package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.accounts.AccountManagement;
import ca.uwo.cs2212.group54.stayingalive.game.Gameplay;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.Difficulty;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.LevelData;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.LevelSelector;
import ca.uwo.cs2212.group54.stayingalive.audio.AudioManager;

/**
 * 
 */
public class GameplayScreen implements Screen {

    public static void main(String[] args) {
        GameplayScreen gs = new GameplayScreen();
        gs.showScreen();
    }

    // Gameplay Frame
    private JFrame gameplayFrame = new JFrame("Staying Alive - Gameplay");
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
    private int level;

    private void buildPanel(Account player) {
        if (player == null || player.playerProgress == null) {
            System.err.println("Player or progress is null");
            return;
        }
        int currentPlayerLevel = player.playerProgress.getCurrentLevel();
        // Ensure level is between 1 and 3
        if (currentPlayerLevel < 1) currentPlayerLevel = 1;
        if (currentPlayerLevel > 3) currentPlayerLevel = 3;

        Difficulty difficulty = Difficulty.values()[currentPlayerLevel - 1];
        LevelData ld = LevelSelector.getLevel(1, currentPlayerLevel, difficulty);
        
        if (ld == null) {
            System.err.println("Failed to load LevelData for level " + currentPlayerLevel);
            return;
        }
        
        level = ld.getNumber();
        Gameplay gameplay = new Gameplay(player, ld, difficulty);
        gamePanel = new GamePanel(gameplay);
        buildUI();
    }

   /**
     * Displays the result dialog when the player completes or fails a level.
     *
     * @param win true if the player wins the level, false if the player loses
     * @author Mohamed Ahmed
     */
    public void showLevelResult(boolean win, int currentLevel) {

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
                gameplayFrame,
                message,
                win ? "Level Complete" : "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (win) {
            // level 3
            if (currentLevel == 3) {
                if (choice == 0) {
                    buildPanel(AccountManagement.getCurrentAccount());
                } else {
                    moveToNextScreen("Player");
                }
            } else {
                switch (choice) {
                    case 0:
                        buildPanel(AccountManagement.getCurrentAccount());
                        System.out.println("completed");
                        break;
                    case 1:
                        buildPanel(AccountManagement.getCurrentAccount());
                        break;
                    default:
                        moveToNextScreen("Player");
                        break;
                }
            }
        } else {
            if (choice == 0) {
                buildPanel(AccountManagement.getCurrentAccount());
            } else {
                moveToNextScreen("Player");
            }
        }
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
            gameplayFrame,
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
        AudioManager.playButtonClick();
        // move from this class to player menu when back button is clicked
        if (e.getActionCommand() != null) {
            switch (e.getActionCommand()) {
                case "Back":
                    System.out.println("→ Back");
                    gamePanel.getGameplay().endLevel();
                    showLevelResult(false);
                    break;
                case "Next Level":
                    System.out.println("→ Next level");
                    break;
                case "Restart Level":
                    System.out.println("→ Restart level");
                    break;
            }
        }
    }
    
    /**
     * 
     */
    @Override
    public void showScreen() {
        if (gameplayFrame == null) {
            gameplayFrame = new JFrame("Staying Alive - Game");
            WindowUtils.addSaveOnClose(gameplayFrame); // data is saved when window is closed
        }
        buildPanel(AccountManagement.getCurrentAccount());
        gameplayFrame.getContentPane().removeAll();
        buildUI();
//        gameplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameplayFrame.setSize(NavigationControl.screenW * 2, NavigationControl.screenH * 2);
        gameplayFrame.setBackground(backgroundPurple);
        gameplayFrame.setVisible(true);
        gameplayFrame.setLocationRelativeTo(null);
    }

    /**
     * Construct the UI for the gameplay screen
     */
    private void buildUI() {
        gameplayFrame.getContentPane().removeAll();
        backButton = buildBackButton();
        gamePanel.setGameListener(() -> {
            Gameplay g = gamePanel.getGameplay();
            gamePanel.getGameplay().endLevel();
            showLevelResult(g.isLevelCleared(), level);
        });
        gamePanel.add(backButton);
        gameplayFrame.getContentPane().add(gamePanel);
        gameplayFrame.revalidate();
        gameplayFrame.repaint();
    }

    /** Back button using the back.png image from the global folder. */
    private JButton buildBackButton() {
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
        addKeyShortcut((JPanel)gameplayFrame.getContentPane(), KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                gamePanel.getGameplay().endLevel();
                showLevelResult(false);
                moveToNextScreen("Back");
             }
        });
        btn.setBounds(screenW - 150, 20, 50, 50);
        return btn;
    }


    /**
     * Add key press functionality to a given key to handle logic
     * 
     * @param target The component to give the navigation logic to
     * @param keyCode The key to give logic to
     * @param action The logic to give
     * @author Fardin Abbassi
     */
    @Override
    public void addKeyShortcut(JComponent target, int keyCode, Action action) {
        InputMap im = target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = target.getActionMap();
        String key = "shortcut_" + keyCode;
        im.put(KeyStroke.getKeyStroke(keyCode, 0), key);
        am.put(key, action);
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
        return gameplayFrame;
    }

    public static Color getBackgroundClr1() { return backgroundClr1; }

    public static Color getBackgroundClr2() { return backgroundClr2; }
}
