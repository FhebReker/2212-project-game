import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ParentalControls implements Screen {
    // Main Frame
    private JFrame parentalControlsFrame = new JFrame("Staying Alive - Parental Controls");

    // UI Colours
    private static final Color backgroundPurple = new Color(106, 69, 156);
    private static final Color titleTextColour = new Color(255, 73, 164);
    private static final Color buttonBackground = new Color(0, 140, 255);
    private static final Color buttonTextColor = Color.black;
    private static final Color BTN_COLOR   = new Color(0x00, 0xBF, 0xFF);
    private static final Color BTN_HOVER   = new Color(0x00, 0x9A, 0xCD);

    // UI Buttons
    private JButton backButton;
    private JButton signUpButton;
    private JButton resetPasswordButton;
    private JButton resetStatsButton;
    private JButton showAllPlayersTab; // Tab to show all player accounts and their data
    private JButton createAccountTab; // Tab to create a new player account

    // Player Account Management Fields
    private JTable playerTable;
    //private ArrayList<Account> playerAccounts; // TODO: Refactor ac.java into Account.java

    // Account Creation Fields
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Panels for each tab
    private JPanel allPlayersPanel;
    private JPanel createAccountPanel;





    // Screen Methods
    /**
     * Handles button clicks on the screen
     * 
     * <p>
     * Some buttons switch between panels whereas others perform functions like
     * resetting player stats or creating a new account. 
     * 
     * @param e The ActionEvent triggered by a button click
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // TODO: Add functions for each button
        switch (command) {
            case "Back":
                break;
            case "Sign Up":
                break;
            case "Reset Password":
                break;
            case "Reset Stats":
                break;

            case "Show All Players":
                break;
            case "Create Account":
                break;
        }
    }
    
    /**
     * 
     */
    @Override
    public void showScreen() {
        // Show all players panel by default
        if (parentalControlsFrame == null) {
            parentalControlsFrame = new JFrame("Staying Alive - Parental Controls");
            parentalControlsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        parentalControlsFrame.setSize(NavigationControl.screenW, NavigationControl.screenH);
        parentalControlsFrame.getContentPane().removeAll();
        parentalControlsFrame.getContentPane().setLayout(new BorderLayout());
        parentalControlsFrame.getContentPane().setBackground(backgroundPurple);

        // Top bar (tab buttons + back button)
        // TODO: make function to create top bar

        // Main Panel (both panels, only one visible at a time)
        // TODO: make function to create main panel with both sub-panels

        parentalControlsFrame.setLocationRelativeTo(null);
        parentalControlsFrame.setVisible(true);
    }

    /**
     * Move back to the main menu when the back button is clicked.
     * 
     * @param screenToMoveTo The name of the screen to switch to, currently just the main menu.
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        if (screenToMoveTo.equals("Back")) {
            NavigationControl.setCurrentScreen(0);
        }
    }

    /**
     * 
     */
    @Override
    public JFrame getFrame() {return parentalControlsFrame;}
}