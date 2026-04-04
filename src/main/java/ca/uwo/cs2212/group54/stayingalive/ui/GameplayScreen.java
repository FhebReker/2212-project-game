package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.Action;
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
        if (e.getActionCommand() != null && e.getActionCommand().equals("Back")) {
            System.out.println("→ Back");
            this.moveToNextScreen("Player");
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
        GameplayFrame.getContentPane().add(gamePanel);
    }

    /**
     * 
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        // TODO Auto-generated method stub
        
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
