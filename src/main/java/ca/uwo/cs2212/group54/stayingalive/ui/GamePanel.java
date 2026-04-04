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

    // Gameplay Related
    private static boolean running;
    private Gameplay gameplay;

    public GamePanel(Gameplay gameplay) {
        setFocusable(true);
        addKeyListener(this);

        this.gameplay = gameplay;

        // Game loop (runs ~60 FPS)
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
                FontMetrics fontMetrics = g.getFontMetrics(); // Calculate font size
                String highlight = currentWord.substring(0,enemy.getFirstUnlockedChar());
                int textWidth = fontMetrics.stringWidth(highlight);
                int textHeight = fontMetrics.getHeight();
                g.setColor(Color.GREEN);
                g.fillRect(textX, textY - fontMetrics.getAscent(),
                    textWidth, textHeight); // Highlight behind characters
                
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long now = System.nanoTime();
        float deltaTime = (now - lastTime) / 30_000_000.0f;
        lastTime = now;
        updateGame(deltaTime);
        repaint();
    }

    /**
     * All the updates that take place per frame.
     */
    private void updateGame(float deltaTime) {
        gameplay.update(deltaTime);
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
}