import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class ParentalControls implements Screen {
    // Main Frame
    private JFrame parentalControlsFrame = new JFrame("Staying Alive - Parental Controls");

    // UI Colours
    private static final Color BG_COLOR = new Color(106, 69, 156);
    private static final Color BUTTON_BG = new Color(0, 140, 255);
    private static final Color PICKED_TAB = new Color(100, 180, 255);
    private static final Color TABLE_COLOR = new Color(255, 255, 255, 50);
    private static final Color TABLE_HEADER_BG = new Color(80, 60, 120);


    // UI Buttons
    private JButton backButton;
    private JButton signUpButton;
    private JButton resetPasswordButton;
    private JButton resetStatsButton;
    private JButton showAllPlayersTab; // Tab to show all player accounts and their data
    private JButton createAccountTab; // Tab to create a new player account

    // Player Account Management Fields
    private JTable playerTable;
    private DefaultTableModel playerTableModel;
    //private ArrayList<Account> playerAccounts; // TODO: Refactor ac.java into Account.java

    // Account Creation Fields
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Panels for each tab
    private JPanel allPlayersPanel;
    private JPanel createAccountPanel;


    // Helper Functions
    /**
     * 
     */
    private void signUpNewUser() {
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword());
        
        // TODO: Use Parental.java to handle sign ups
        refreshPlayerTable(); // show new account
    }
    /**
     * Refresh table data after changes to accounts.
     * 
     * <p>
     * This will be called after functions that change data, like signing a new user up or reseting stats.
     * 
     * @author Fardin Abbassi
     */
    private void refreshPlayerTable() {
        if (playerTable != null) {
            playerTable.revalidate();
            playerTable.repaint();
        }
    }
    /**
     * Helper function to switch to the "Show All Players" panel and update tab button colours.
     * 
     * @author Fardin Abbassi
     */
    private void showAllPlayers() {
        allPlayersPanel.setVisible(true);
        createAccountPanel.setVisible(false);

        if (showAllPlayersTab != null) {showAllPlayersTab.setBackground(PICKED_TAB);}
        if (createAccountTab != null) {createAccountTab.setBackground(BUTTON_BG);}
    }
    /**
     * Helper function to switch to the "Create Account" panel and update tab button colours.
     * 
     * @author Fardin Abbassi
     */
    private void showCreateAccount () {
        allPlayersPanel.setVisible(false);
        createAccountPanel.setVisible(true);

        if (showAllPlayersTab != null) {showAllPlayersTab.setBackground(BUTTON_BG);}
        if (createAccountTab != null) {createAccountTab.setBackground(PICKED_TAB);}
    }

    /**
     * Builds top bar of screen that contains the tab buttons and back button.
     */
    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(BG_COLOR);
        
        // Back button
        ImageIcon icon = null;
        File f = new File("global/back.png");
        if (f.exists()) {
            Image img = new ImageIcon(f.getAbsolutePath()).getImage()
                            .getScaledInstance(34, 34, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }
        backButton = new JButton(icon);
        backButton.setActionCommand("Back");
        backButton.setPreferredSize(new Dimension(34, 34));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setToolTipText("Back");
        backButton.addActionListener(this);
        

        // Tab Buttons
        showAllPlayersTab = new JButton("Show All Players");
        showAllPlayersTab.setFont(new Font("SansSerif", Font.BOLD, 14));
        showAllPlayersTab.setForeground(Color.WHITE);
        showAllPlayersTab.setBackground(new Color(100, 180, 255));
        showAllPlayersTab.setBorderPainted(false);
        showAllPlayersTab.setFocusPainted(false);
        showAllPlayersTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showAllPlayersTab.setActionCommand("Show All Players");
        showAllPlayersTab.addActionListener(this);

        createAccountTab = new JButton("Create Account");
        createAccountTab.setFont(new Font("SansSerif", Font.BOLD, 14));
        createAccountTab.setForeground(Color.WHITE);
        createAccountTab.setBackground(BUTTON_BG);
        createAccountTab.setBorderPainted(false);
        createAccountTab.setFocusPainted(false);
        createAccountTab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createAccountTab.setActionCommand("Create Account");
        createAccountTab.addActionListener(this);

        // Add buttons to top bar and return
        topBar.add(backButton);
        topBar.add(showAllPlayersTab);
        topBar.add(createAccountTab);
        return topBar;
    }

    /**
     * Helper function to build the "Show All Players" panel.
     * 
     * <p>
     * Contains a table of all player accounts, their stats, and buttons used
     * to reset passwords or stats for all accounts.
     * 
     * @return JPanel containing the "Show All Players" table and buttons
     * @author Fardin Abbassi
     */
    private JPanel buildAllPlayersPanel() {
        // Set up panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(false);
        panel.setBackground(BG_COLOR); 
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Set up table using player account data
        String[] columns = {"Username", "High Score", "Stats"};
        playerTableModel = new DefaultTableModel (columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {return false;} // make table non-editable
        };

        playerTable = new JTable(playerTableModel);
        playerTable.setBackground(TABLE_COLOR);
        playerTable.setForeground(Color.WHITE);
        playerTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        playerTable.setRowHeight(30);
        playerTable.setShowGrid(false);
        playerTable.setIntercellSpacing(new Dimension(0, 0));
        playerTable.setSelectionBackground(new Color(100, 100, 200));

        // Stylize Table Header
        JTableHeader header = playerTable.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));

        // Set column widths
        playerTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        playerTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        playerTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Clicking on a player account shows their stats in a popup
        playerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = playerTable.getSelectedColumn();
                int row = playerTable.getSelectedRow();
                if (row >= 0 && column == 2) {
                    String playerName = (String) playerTableModel.getValueAt(row, 0); // get username from table
                    // TODO: Access player stats from database and show via popup
                }
            }
        });

        // Add table to scroll pane, then to main panel
        JScrollPane scrollPane = new JScrollPane(playerTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(scrollPane, BorderLayout.CENTER);



        // Add reset buttons at the bottom of the panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        resetStatsButton = new JButton("Reset Stats");
        resetStatsButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetStatsButton.setForeground(Color.WHITE);
        resetStatsButton.setBackground(new Color(200, 60, 60));
        resetStatsButton.setBorderPainted(false);
        resetStatsButton.setFocusPainted(false);
        resetStatsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetStatsButton.setActionCommand("Reset Stats");
        resetStatsButton.addActionListener(this);

        resetPasswordButton = new JButton("Reset Passwords");
        resetPasswordButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setBackground(new Color(200, 120, 60));
        resetPasswordButton.setBorderPainted(false);
        resetPasswordButton.setFocusPainted(false);
        resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetPasswordButton.setActionCommand("Reset Passwords");
        resetPasswordButton.addActionListener(this);

        buttonPanel.add(resetStatsButton);
        buttonPanel.add(resetPasswordButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Return complete panel
        return panel;
    }
    /**
     * Helper function to build the "Create Account" panel.
     * 
     * <p>
     * Contains form fields to make a new player account and a button to sign up the new account.
     * @return JPanel containing the "Create Account" panel
     */
    private JPanel buildCreateAccountPanel() {
        // Set up panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Set up form fields and labels
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Set up username label and field
        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(usernameField, gbc);

        // Set up password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordField, gbc);



        // Set up sign up button
        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(BUTTON_BG);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setActionCommand("Sign Up");
        signUpButton.addActionListener(this);
        signUpButton.setPreferredSize(new Dimension(100, 35));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(signUpButton, gbc);

        // Return panel
        return panel;
    }
    /**
     * Helper function to build the main content panel containing both sub-panels.
     * 
     * <p>
     * Both the "Show All Players" and "Create Account" panels are added to this panel, 
     * but only one is visible at a time. They can be switched using the tab buttons 
     * found in the top bar.
     * 
     * @author Fardin Abbassi
     * @return JPanel holding the content for the screen sans top bar.
     */
    private JPanel buildMainContent() {
        // Set up main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Set up content panel with both sub-panels where one is shown at a time
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setOpaque(false);
        
        allPlayersPanel = buildAllPlayersPanel();
        createAccountPanel = buildCreateAccountPanel();
        contentPanel.add(allPlayersPanel, "All Players");
        contentPanel.add(createAccountPanel, "Create Account");
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Show all players panel by default, then return the completed main panel
        showAllPlayers();
        return mainPanel;
    }



    // Constructor
    public ParentalControls() {
        // TODO: this.playerAccounts = AccountManagement.getPlayers();
    }

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
                moveToNextScreen(command);
                break;
            case "Sign Up":
                // Add user to database
                break;
            case "Reset Password":
                // set all user's passwords to CompSci2212
                break;
            case "Reset Stats":
                // set all user stats in the database to 0s
                break;

            case "Show All Players":
                showAllPlayers();
                // show the all players panel
                break;
            case "Create Account":
                showCreateAccount();
                // show the create account panel
                break;
        }
    }
    
    /**
     * Display the screen by building top bar and the main content panels,
     * then adds them to the frame and makes the frame visible.
     * 
     * @author Fardin Abbassi
     */
    @Override
    public void showScreen() {
        // Set up frame
        if (parentalControlsFrame == null) {
            parentalControlsFrame = new JFrame("Staying Alive - Parental Controls");
            parentalControlsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        parentalControlsFrame.setSize(NavigationControl.screenW, NavigationControl.screenH);
        parentalControlsFrame.getContentPane().removeAll();
        parentalControlsFrame.getContentPane().setLayout(new BorderLayout());
        parentalControlsFrame.getContentPane().setBackground(BG_COLOR);

        // Top bar (tab buttons + back button)
        parentalControlsFrame.getContentPane().add(buildTopBar(), BorderLayout.NORTH);

        // Main Panel (both panels, only one visible at a time)
        parentalControlsFrame.getContentPane().add(buildMainContent(), BorderLayout.CENTER);

        // Snap frame to center and show
        parentalControlsFrame.setLocationRelativeTo(null);
        parentalControlsFrame.setVisible(true);
    }

    /**
     * Move back to the main menu when the back button is clicked.
     * 
     * @param screenToMoveTo The name of the screen to switch to, currently just the main menu.
     * 
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        if (screenToMoveTo.equals("Back")) {
            NavigationControl.setCurrentScreen(0);
        }
    }

    /**
     * Get the screen's frame
     * 
     * @author Fardin Abbassi
     * @return JFrame of the screen
     */
    @Override
    public JFrame getFrame() {return parentalControlsFrame;}
}