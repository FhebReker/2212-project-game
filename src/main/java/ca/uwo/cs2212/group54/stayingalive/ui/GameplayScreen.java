package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.*;

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
 * Displays the result dialog when the player completes or fails a level.
 *
 * @param win true if the player wins the level, false if the player loses
 * @author Mohamed Ahmed
 */
public void showLevelResult(boolean win) {

    int currentLevel = 3; 

    String message;
    Object[] options;

    if (win) {
        if (currentLevel == 3) {
            message = "congratulations you have completed the game";
            options = new Object[]{"Restart Game", "Menu"};
        } else {
            message = "Congratulations you may proceed to the next level!";
            options = new Object[]{"Next Level", "Restart", "Menu"};
        }
    } else {
        message = "Game Over! Don't give up, try again!";
        options = new Object[]{"Restart", "Menu"};
    }

    int choice = JOptionPane.showOptionDialog(
            GameplayFrame,
            message,
            win ? "Level Complete" : "Game Over",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
    );

    if (win) {
        if (currentLevel == 3) {
            if (choice == 0) {
                System.out.println("Restart whole game");
            } else {
                moveToNextScreen("Player");
            }
        } else {
            if (choice == 0) {
                System.out.println("Go to next level");
            } else if (choice == 1) {
                System.out.println("Restart level");
            } else {
                moveToNextScreen("Player");
            }
        }
    } else {
        if (choice == 0) {
            System.out.println("Please restart level");
        } else {
            moveToNextScreen("Player");
        }
    }
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
    @Override
    public void addKeyShortcut(JComponent target, int keyCode, Action action) {
        // TODO Auto-generated method stub
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
