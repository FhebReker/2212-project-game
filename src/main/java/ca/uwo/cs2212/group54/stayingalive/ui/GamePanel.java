package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import ca.uwo.cs2212.group54.stayingalive.accounts.AccountManagement;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Gameplay;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private Timer gameLoop;
    private long lastTime = System.nanoTime();

    // Player position
    private int playerX = Gameplay.getPlayerX();
    private int playerY = Gameplay.getPlayerY();

    // Color
    private static final Color backgroundPurple = new Color(106, 69, 156);
    private static final Color textColor = new Color(255, 165, 0);

    // Other
    private static final int textOffset = 20;
    private static final int borderSize = 20;
    private static final int heartImageSize = 80;
    private static final int padding = 10;

    // Gameplay Related
    private static boolean running;
    private Gameplay gameplay;
    private GameOverListener listener;

    // This is the logic for showing the result overlay when a level is cleared or failed
    private boolean showResultOverlay = false;
    private boolean levelWon = false;

    public GamePanel(Gameplay gameplay) {
        setFocusable(true);
        addKeyListener(this);
        this.setLayout(null);

        this.gameplay = gameplay;

        // Game loop (will run ~60 FPS)
        gameLoop = new Timer(16, this);
        gameLoop.start();
        running = true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Background
        g.setColor(backgroundPurple);
        g.fillRect(0, 0, getWidth(), getHeight());
        GradientPaint gp = new GradientPaint(0, 0, GameplayScreen.getBackgroundClr1(), 
        0, getHeight(), GameplayScreen.getBackgroundClr2());
        g.fillRect(20, 20, getWidth() - 20, getHeight() - 20);
        

        g2d.setPaint(gp);
        g2d.fillRect(borderSize, borderSize, getWidth() - borderSize * 2, getHeight() - borderSize * 2);

        // Player
        g.setColor(Color.GREEN);
        Image playerImage = new ImageIcon("global/download.png").getImage();
        g.drawImage(playerImage,playerX - 37,playerY - 50,75,100,null);

        // Example enemy
        g.setColor(Color.RED);
        List<Enemy> active = gameplay.getActiveEnemies();
        for (Enemy enemy: active) {
            if (! enemy.isDefeated()) {
                // Draw enemy sprites
                Image image = enemy.getSprite().getImage();
                g.drawImage(image, enemy.getSprite().getX()-50, enemy.getSprite().getY()-50, 100, 100, null);
                g.drawRect(enemy.getSprite().getX()-50, enemy.getSprite().getY()-50, 100, 100); // only testing for hitboxes

                // Text title for each enemy
                int textX = enemy.getSprite().getX() - textOffset;
                int textY = enemy.getSprite().getY() - textOffset;
                String currentWord = enemy.getCurrentWord();
                g.setColor(textColor);
                Font enemyFont = new Font("Helvetica", Font.PLAIN, 16);
                g.setFont(enemyFont);
                
                // FOR HIGHLIGHTING BEHIND IT
                if (enemy.equals(gameplay.getInFocus())) {
                    /*
                    g.setColor(Color.CYAN);
                    g.drawRect(enemy.getPositionX(), enemy.getPositionY(),
                    100, 100);*/
                    
                    FontMetrics fontMetrics = g.getFontMetrics(); // Calculate font size
                    String typedLetters = currentWord.substring(0,enemy.getFirstUnlockedChar());

                    int textHeight = fontMetrics.getHeight();

                    int typedTextWidth = fontMetrics.stringWidth(typedLetters.toUpperCase());
                    int textWidth = fontMetrics.stringWidth(currentWord.toUpperCase());

                    g.setColor(Color.BLUE);
                    g.fillRect(textX, textY - fontMetrics.getAscent(),
                        textWidth, textHeight); // Highlight behind enemy word for focused enemy
                    g.setColor(Color.GREEN);
                    g.fillRect(textX, textY - fontMetrics.getAscent(),
                        typedTextWidth, textHeight); // Highlight behind typed characters
                    
                }
                
                // Drawing enemy current word
                g.setColor(textColor);
                g.drawString(currentWord.toUpperCase(), textX, textY);    
            }
            
        }

        // Player hearts
        Image heartImage = new ImageIcon("global/blueheart.png").getImage();
        int lives = gameplay.getLives();
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, borderSize + 10 + i * heartImageSize, borderSize + 10, 
                heartImageSize, heartImageSize, null);
        }

        // Current score
        g.setColor(Color.WHITE);
        Font scoreFont = new Font("Helvetica", Font.PLAIN, 40);
        g.setFont(scoreFont);
        g.drawString("Current Score: " + gameplay.getScore(), 
            borderSize + 10, NavigationControl.screenH * 2 - borderSize - 50);
        
        // Current Level
        int level = AccountManagement.getCurrentAccount().getProgress().getCurrentLevel();
        g.drawString("Level: " + level, 
            borderSize + 10, NavigationControl.screenH * 2 - borderSize - 100);
        

        // this is for to  draw result overlay if level is cleared or failed
        if (showResultOverlay) {
            drawOverlay(g2d);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.nanoTime();
        float deltaTime = (now - lastTime) / 30_000_000.0f;
        lastTime = now;
        updateGame(deltaTime);
        repaint();
        updatePanel();
    }

    /**
     * All the updates that take place per frame.
     */
    private void updateGame(float deltaTime) {
        // this will help us stop updating once result screen is shown
        if (showResultOverlay) return;

        gameplay.update(deltaTime);

        // to see and detect win or loss
        if (gameplay.isLevelCleared()) {
            showResultOverlay = true;
            levelWon = true;
            gameLoop.stop();
        }

        if (gameplay.isGameOver()) {
            showResultOverlay = true;
            levelWon = false;
            gameLoop.stop();
        }
    }

    /**
     * Updates the panel to check if the game is over.f
     */
    private void updatePanel() {
        if (gameplay.isGameOver() || gameplay.isLevelCleared()) {
            gameLoop.stop();
            listener.onGameOver();
        }
    }

    // Key controls
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {}
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        gameplay.processInput(c);
    }

    public static boolean getRunState() { return running; }

    public Gameplay getGameplay() { return gameplay; }

    public void setGameListener(GameOverListener listener) {
        this.listener = listener;
    }

    /**
     * Draws the result overlay when the player completes or fails a level.
     * 
     * @param g2d the graphics object used to draw the overlay
     * @author Mohamed Ahmed
     */
    private void drawOverlay(Graphics2D g2d) {
        int w = 500;
        int h = 250;
        int x = (getWidth() - w) / 2;
        int y = (getHeight() - h) / 2;

        g2d.setColor(new Color(60, 0, 100));
        g2d.fillRect(x, y, w, h);

        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, w, h);

        g2d.setFont(new Font("Helvetica", Font.BOLD, 28));
        String text = levelWon ? "Level Complete!" : "Game Over!";
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        g2d.drawString(text, x + (w - textWidth) / 2, y + 60);

        g2d.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g2d.drawString("Score: " + gameplay.getScore(), x + 180, y + 120);
        g2d.drawString("WPM: " + gameplay.getWPM(), x + 190, y + 150);
    }

}
