package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.game.Gameplay;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.Difficulty;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.LevelSelector;

/**
 * 
 */
public class GameplayScreen implements Screen {

    public static void main(String[] args) {
        GameplayScreen gs = new GameplayScreen();
        gs.showScreen();
    }

    // Gameplay Frame
    private JFrame GameplayFrame = new JFrame("Staying Alive - Gameplay");
    public final static int screenW = NavigationControl.screenW * 2;
    public final static int screenH = NavigationControl.screenH * 2;

    // UI Colours
    private static final Color backgroundClr1 = new Color(0, 170, 0); // GREEN
    private static final Color backgroundClr2 = new Color(165, 84, 84); // BROWN
        private static final Color backgroundPurple = new Color(106, 69, 156);
    private static final Color heartsClr = new Color(0, 0, 170);

    // UI Button
    private JButton backButton;

    // Other
    private GamePanel gamePanel;

    public GameplayScreen() {
        Account player = new Account("random1","test");
        int currentPlayerLevel = player.playerProgress.getCurrentLevel();
        //int currentPlayerLevel = 1;
        Difficulty difficulty = Difficulty.values()[currentPlayerLevel];
        Gameplay gameplay = new Gameplay(player,LevelSelector.getLevel(1, currentPlayerLevel, difficulty),difficulty);
        gamePanel = new GamePanel(gameplay);
    }
    
    /**
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // move from this class to player menu when back button is clicked
        if (e.getActionCommand() != null) {
            switch (e.getActionCommand()) {
                case "Back":
                    System.out.println("→ Back");
                    gamePanel.getGameplay().endLevel();
                    this.moveToNextScreen("Player");
                    break;
                case "Next Level":
                    System.out.println("→ Next level");
                    // TODO: add here
                    break;
                case "Restart Level":
                    System.out.println("→ Restart level");
                    // TODO: add here
                    break;
            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void showScreen() {
        GameplayFrame.getContentPane().removeAll();
        buildUI();
        GameplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameplayFrame.setSize(NavigationControl.screenW * 2, NavigationControl.screenH * 2);
        GameplayFrame.setBackground(backgroundPurple);
        GameplayFrame.setVisible(true);
        GameplayFrame.setLocationRelativeTo(null);
    }

    /**
     * Construct the UI for the gameplay screen
     */
    private void buildUI() {
        backButton = buildBackButton();
        gamePanel.add(backButton);
        GameplayFrame.getContentPane().add(gamePanel);

    }

    /** Back button using the back.png image from the global folder. */
    private JButton buildBackButton() {                                          // TODO: Add javadoc comments to method
        ImageIcon icon = null;
        File imgFile = new File("global/back.png");
        if (imgFile.exists()) {
            Image img = new ImageIcon(imgFile.getAbsolutePath()).getImage()
                            .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }
        JButton btn = new JButton(icon);
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Back");
        btn.setActionCommand("Back");
        btn.addActionListener(this); // handle back button click in actionPerformed
        btn.setBounds(screenW - 150, 20, 50, 50);
        return btn;
    }

    /**
     * 
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        if (screenToMoveTo.equals("Player")) {
            System.out.println("to player menu");
            NavigationControl.setCurrentScreen(3);
        }
    }

    /**
     * 
     */
    @Override
    public JFrame getFrame() {
        // TODO Auto-generated method stub
        return null;
    }

    public static Color getBackgroundClr1() { return backgroundClr1; }

    public static Color getBackgroundClr2() { return backgroundClr2; }

    @Override
    public void addKeyShortcut(JComponent target, int keyCode, Action action) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
