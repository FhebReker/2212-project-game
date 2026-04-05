package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.accounts.LevelStatistic;

/**
 * Implements the Parental Controls screen of the application.
 * 
 * <p>
 * The parental user, upon login, will be able to  cycle between two panels: one for the
 * database and another to sign up a new user.
 * <p>
 * In the first, the parent can view the total high scores of all users, reset passwords and 
 * stats of selected users, or delete a selected user.
 * In the second, the parent can sign up a new user, provided the username does not already exist.
 * 
 * @author Osman Idris
 * @author Fardin Abbassi
 */
public class ParentalControls implements Screen {
    // ──── Instance Variables ─────────────────────────────────────────────────
    // Main Frame
    private JFrame parentalControlsFrame = new JFrame("Staying Alive - Parental Controls");

    // UI Colours
    private static final Color BG_COLOR = new Color(106, 69, 156);
    private static final Color BUTTON_BG = new Color(0, 140, 255);
    private static final Color PICKED_TAB = new Color(100, 180, 255);
    private static final Color TABLE_COLOR = new Color(60, 40, 100);
    private static final Color TABLE_HEADER_BG = new Color(80, 60, 120);


    // UI Buttons
    private JButton backButton;
    private JButton signUpButton;
    private JButton resetPasswordButton;
    private JButton resetStatsButton;
    private JButton deleteAccountButton;
    private JButton showAllPlayersTab; // Tab to show all player accounts and their data
    private JButton createAccountTab; // Tab to create a new player account

    // Player Account Management Fields
    private JTable playerTable;
    private DefaultTableModel playerTableModel;

    // Account Creation Fields
    private JTextField usernameField;
    private JPasswordField passwordField;

    // Panels for each tab
    private JPanel allPlayersPanel;
    private JPanel createAccountPanel;


    // ──── Account Management Helpers ─────────────────────────────────────────
    /**
     * Signs up a new user by getting test from the username and password fields.
     * Storage of the user is automatically done in the JSON when calling the createAccount(user,pass)
     * method from the parental class.
     * 
     * @author Fardin Abbassi
     * @author Osman Idris
     */
    private void signUpNewUser() {
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword());
        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            showStyledMessage("Username and password cannot be empty.");
            return;
        }
        boolean created = NavigationControl.getAccountManager().getParental().createAccount(newUsername, newPassword);
        if (!created) {
            showStyledMessage("Username already exists.");
            return;
        }
        // Add new row to the table
        playerTableModel.addRow(new Object[]{newUsername, 0, 0.0});
        // Clear the input fields
        usernameField.setText("");
        passwordField.setText("");
        showStyledMessage("Account created successfully!");
    }
    /** 
     * Resets stats for the selected player only. 
     * 
     * @author Osman Idris
     * */
    private void resetStats() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Please select a player first.");
            return;
        }
        String username = (String) playerTableModel.getValueAt(selectedRow, 0);
        if (!showStyledConfirm("Reset stats for \"" + username + "\"?")) return;
        NavigationControl.getAccountManager().getParental().resetPlayerStats(username);
        playerTableModel.setValueAt(0, selectedRow, 1);
        playerTableModel.setValueAt(0.0, selectedRow, 2);
    }
    /** 
     * Deletes the selected player account. 
     * 
     * @author Osman Idris
     */
    private void deleteAccount() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Please select a player first.");
            return;
        }
        String username = (String) playerTableModel.getValueAt(selectedRow, 0);
        if (!showStyledConfirm("Delete account \"" + username + "\"? This cannot be undone.")) return;
        NavigationControl.getAccountManager().getParental().deleteAccount(username);
        playerTableModel.removeRow(selectedRow);
    }
    /**
     * Checks which row is selected and resets the password for that account (to a new password).
     * Makes use of playerTable to check selected row, JOptionPane's input dialog to set a new password,
     * and parental (from account manager in nav control) to reset password.
     * 
     * @author Osman Idris
     * @author Fardin Abbassi
     */
    private void resetPassword() {
        int selectedRow = playerTable.getSelectedRow();
        if (selectedRow == -1) {
            showStyledMessage("Please select a player first.");
            return;
        }
        String username = (String)playerTableModel.getValueAt(selectedRow,0);
        String newPassword = showStyledPasswordInput("Enter new password:");
        if (newPassword.isBlank()) {
            showStyledMessage("New password cannot be empty.");
            System.out.println(newPassword);
            return;
        }
        NavigationControl.getAccountManager().getParental().resetPassword(username, newPassword);
    }
    /**
     * Displays a styled dialog with detailed statistics for the given player.
     * 
     * @param username The username of the player whose stats to show
     * @author Fardin Abbassi
     */
    private void showPlayerStatsDialog(String username) {
        Account player = NavigationControl.getAccountManager().getParental().getAccount(username);
        if (player == null) return;
        
        LevelStatistic[] allStats = player.getAllLevelStats();
        int currentLevel = player.getProgress().getCurrentLevel();
        LevelStatistic currentStats = allStats[currentLevel - 1];
        
        // Create a panel with GridLayout for labels and values
        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        statsPanel.setBackground(new Color(80, 52, 117));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Font labelFont = new Font("Helvetica", Font.PLAIN, 13);
        Font valueFont = new Font("Helvetica", Font.BOLD, 13);
        Color gold = new Color(255, 215, 60);
        
        // TODO: Debug this display of data when actually playing the game
        String[][] data = {
            {"Current Level:", String.valueOf(currentLevel)},
            {"High Score:", String.valueOf(currentStats.getHighscore())},
            {"Average WPM:", String.valueOf(currentStats.getAvgWPM())},
            {"Peak WPM:", String.valueOf(currentStats.getPeakWPM())},
            {"Accuracy (%):", String.format("%.2f", currentStats.getAccuracy())},
            {"Error Count:", String.valueOf(currentStats.getMistakes())},
            {"Attempts:", String.valueOf(currentStats.getAttempts())},
            {"Status:", currentStats.getStatus().toString()}
        };
        
        for (String[] row : data) {
            JLabel label = new JLabel(row[0]);
            label.setFont(labelFont);
            label.setForeground(Color.WHITE);
            JLabel value = new JLabel(row[1]);
            value.setFont(valueFont);
            value.setForeground(gold);
            statsPanel.add(label);
            statsPanel.add(value);
        }
        
        // Optional overall stats (simple)
        int totalHighScore = 0;
        double totalAccuracy = 0;
        for (LevelStatistic stat : allStats) {
            totalHighScore += stat.getHighscore();
            totalAccuracy += stat.getAccuracy();
        }
        double avgAccuracy = allStats.length > 0 ? totalAccuracy / allStats.length : 0;
        
        statsPanel.add(new JLabel("--- Overall ---"));
        statsPanel.add(new JLabel(""));
        statsPanel.add(new JLabel("Total High Score:"));
        statsPanel.add(new JLabel(String.valueOf(totalHighScore)));
        statsPanel.add(new JLabel("Avg Accuracy (all):"));
        statsPanel.add(new JLabel(String.format("%.2f", avgAccuracy)));
        
        // Put panel in a scroll pane (optional, in case many levels)
        JScrollPane scrollPane = new JScrollPane(statsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(80, 52, 117));
        
        // Create dialog
        JDialog dialog = new JDialog(parentalControlsFrame, username + "'s Statistics", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setBackground(new Color(102, 187, 255));
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dialog.dispose());
        addKeyShortcut(closeBtn, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { dialog.dispose(); }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(80, 52, 117));
        buttonPanel.add(closeBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setSize(400, 450);
        dialog.setLocationRelativeTo(parentalControlsFrame);
        dialog.setVisible(true);
    }

    // ──── Styled dialog helpers (match login screen colours) ─────────────────
    /** 
     * Shows a styled message dialog (OK button). 
     * 
     * @author Osman Idris
     * @author Fardin Abbassi
     */
    private void showStyledMessage(String message) {
        JDialog dialog = new JDialog(parentalControlsFrame, true);
        dialog.setSize(350, 180);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(new Color(80, 52, 117));

        JLabel msgLabel = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>", JLabel.CENTER);
        msgLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setBounds(20, 25, 310, 70);

        JButton okBtn = new JButton("OK");
        okBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        okBtn.setForeground(Color.BLACK);
        okBtn.setBackground(new Color(102, 187, 255));
        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setBounds(125, 100, 100, 30);
        okBtn.addActionListener(e -> dialog.dispose());
        addKeyShortcut(okBtn, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { dialog.dispose(); }
        });

        dialog.add(msgLabel);
        dialog.add(okBtn);
        dialog.setLocationRelativeTo(parentalControlsFrame);
        dialog.setVisible(true);

    }
    /**
     *  Shows a styled Yes/No confirm dialog.
     * 
     * @return True if 'Yes' was clicked, false if 'No' was clicked
     * @author Osman Idris
     */
    private boolean showStyledConfirm(String message) {
        boolean[] result = {false};
        JDialog dialog = new JDialog(parentalControlsFrame, true);
        dialog.setSize(360, 200);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(new Color(80, 52, 117));

        JLabel msgLabel = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>", JLabel.CENTER);
        msgLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setBounds(20, 25, 320, 70);

        JButton yesBtn = new JButton("Yes");
        yesBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        yesBtn.setForeground(Color.BLACK);
        yesBtn.setBackground(new Color(102, 187, 255));
        yesBtn.setBorderPainted(false);
        yesBtn.setFocusPainted(false);
        yesBtn.setBounds(70, 130, 90, 28);
        yesBtn.addActionListener(e -> { result[0] = true; dialog.dispose(); });

        JButton noBtn = new JButton("No");
        noBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        noBtn.setForeground(Color.BLACK);
        noBtn.setBackground(new Color(200, 80, 80));
        noBtn.setBorderPainted(false);
        noBtn.setFocusPainted(false);
        noBtn.setBounds(200, 130, 90, 28);
        noBtn.addActionListener(e -> dialog.dispose());

        dialog.add(msgLabel);
        dialog.add(yesBtn);
        dialog.add(noBtn);
        dialog.setLocationRelativeTo(parentalControlsFrame);
        dialog.setVisible(true);
        return result[0];
    }
    /** 
     * Shows a styled password input dialog. 
     * 
     * @return Entered text, or null if cancelled
     * @author Osman Idris
     */
    private String showStyledPasswordInput(String prompt) {
        String[] result = {null};
        JDialog dialog = new JDialog(parentalControlsFrame, true);
        dialog.setSize(380, 220);
        dialog.setLayout(null);
        dialog.getContentPane().setBackground(new Color(80, 52, 117));

        JLabel promptLabel = new JLabel(prompt);
        promptLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        promptLabel.setForeground(Color.WHITE);
        promptLabel.setBounds(55, 45, 270, 25);

        JPasswordField passField = new JPasswordField();
        passField.setBackground(new Color(224, 224, 224));
        passField.setForeground(Color.BLACK);
        passField.setFont(new Font("Helvetica", Font.BOLD, 14));
        passField.setBounds(55, 80, 265, 25);

        JButton okBtn = new JButton("OK");
        okBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        okBtn.setForeground(Color.BLACK);
        okBtn.setBackground(new Color(102, 187, 255));
        okBtn.setBorderPainted(false);
        okBtn.setFocusPainted(false);
        okBtn.setBounds(90, 135, 85, 28);
        okBtn.addActionListener(e -> { result[0] = new String(passField.getPassword()); dialog.dispose(); });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Helvetica", Font.PLAIN, 14));
        cancelBtn.setForeground(Color.BLACK);
        cancelBtn.setBackground(new Color(180, 180, 180));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBounds(200, 135, 85, 28);
        cancelBtn.addActionListener(e -> dialog.dispose());

        dialog.add(promptLabel);
        dialog.add(passField);
        dialog.add(okBtn);
        dialog.add(cancelBtn);
        dialog.setLocationRelativeTo(parentalControlsFrame);
        dialog.setVisible(true);
        return result[0];
    }

    // ──── Tab Switching ──────────────────────────────────────────────────────
    /**
     * Helper function to switch to the "Show All Players" panel and update tab button colours.
     * 
     * @author Fardin Abbassi
     */
    private void showAllPlayers() {
        allPlayersPanel.setVisible(true);
        createAccountPanel.setVisible(false);

        if (showAllPlayersTab != null) {
            showAllPlayersTab.setBackground(PICKED_TAB);
        }
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

    // ──── UI Building ────────────────────────────────────────────────────────
    /**
     * Builds top bar of screen that contains the tab buttons and back button.
     * @author Fardin Abbassi
     * @return The top bar of the screen's UI
     */
    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_COLOR);
        topBar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // optional padding

        // Tab Buttons
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        tabsPanel.setOpaque(false);

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

        tabsPanel.add(showAllPlayersTab);
        tabsPanel.add(createAccountTab);

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

        // Add buttons to top bar and return
        topBar.add(tabsPanel, BorderLayout.CENTER);
        topBar.add(backButton, BorderLayout.EAST);
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
     * @author Osman Idris
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

        // Add data to table
        ArrayList<Account> players = NavigationControl.getAccountManager().getParental().getAccounts();
        for (Account player: players) {
            Object[] row = new Object[3];
            row[0] = player.getUsername();
            LevelStatistic[] stats = player.getAllLevelStats();
            row[1] = stats[player.getProgress().getCurrentLevel()].getHighscore();
            row[2] = stats[player.getProgress().getCurrentLevel()].getAccuracy();
            playerTableModel.addRow(row);
        }

        playerTable = new JTable(playerTableModel);
        playerTable.setOpaque(true);
        playerTable.setBackground(Color.WHITE);
        playerTable.setForeground(Color.WHITE);
        playerTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        playerTable.setRowHeight(30);
        playerTable.setShowGrid(false);
        playerTable.setIntercellSpacing(new Dimension(2, 2));
        playerTable.setSelectionBackground(new Color(100, 100, 200));

        // Stylize Table Header
        JTableHeader header = playerTable.getTableHeader();
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
        ((javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);

        // Set column widths
        playerTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        playerTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        playerTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Center-align all cell data to match headers
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(Color.WHITE);
        centerRenderer.setBackground(TABLE_COLOR);
        for (int i = 0; i < playerTable.getColumnCount(); i++) {
            playerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Clicking on a player account shows their stats in a popup
        playerTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int column = playerTable.getSelectedColumn();
                int row = playerTable.getSelectedRow();
                if (row >= 0 && column == 2) {
                    String playerName = (String) playerTableModel.getValueAt(row, 0); // get username from table
                    showPlayerStatsDialog(playerName);
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

        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setBackground(new Color(200, 120, 60));
        resetPasswordButton.setBorderPainted(false);
        resetPasswordButton.setFocusPainted(false);
        resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetPasswordButton.setActionCommand("Reset Password");
        resetPasswordButton.addActionListener(this);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setBackground(new Color(140, 30, 30));
        deleteAccountButton.setBorderPainted(false);
        deleteAccountButton.setFocusPainted(false);
        deleteAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteAccountButton.setActionCommand("Delete Account");
        deleteAccountButton.addActionListener(this);

        buttonPanel.add(resetStatsButton);
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(deleteAccountButton);
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
     * @author Fardin Abbassi
     */
    private JPanel buildCreateAccountPanel() {
        // Styled to match the student login screen
        JPanel panel = new JPanel(null); // absolute layout like LoginScreen
        panel.setBackground(new Color(80, 52, 117));

        JLabel usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(80, 80, 120, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBackground(new Color(224, 224, 224));
        usernameField.setForeground(Color.BLACK);
        usernameField.setFont(new Font("Helvetica", Font.BOLD, 15));
        usernameField.setBounds(210, 80, 200, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(80, 130, 120, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(224, 224, 224));
        passwordField.setForeground(Color.BLACK);
        passwordField.setFont(new Font("Helvetica", Font.BOLD, 15));
        passwordField.setBounds(210, 130, 200, 25);
        panel.add(passwordField);

        signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setBackground(new Color(102, 187, 255));
        signUpButton.setBounds(210, 185, 100, 28);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setActionCommand("Sign Up");
        signUpButton.addActionListener(this);
        panel.add(signUpButton);

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

    // ──── Keyboard Helper ────────────────────────────────────────────────────
    /**
     * Helper function to add navigation to key inputs to the target components.
     * @author Fardin Abbassi
     */
    private void addKeyShortcuts() {
        addKeyShortcut((JPanel)parentalControlsFrame.getContentPane(),KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { moveToNextScreen("Back"); }
        });
        addKeyShortcut((JPanel) parentalControlsFrame.getContentPane(), KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createAccountPanel != null && createAccountPanel.isVisible()) {
                    signUpNewUser();
                }
            }
        });
    }

    // ──── Screen Methods ─────────────────────────────────────────────────────
    /**
     * Handles button clicks on the screen
     * 
     * <p>
     * Some buttons switch between panels whereas others perform functions like
     * resetting player stats or creating a new account. 
     * 
     * @param e The ActionEvent triggered by a button click
     * @author Fardin Abbassi
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Back":
                moveToNextScreen(command);
                break;
            case "Sign Up":
                signUpNewUser();
                break;
            case "Reset Password":
                resetPassword();
                break;
            case "Reset Stats":
                resetStats();
                break;
            case "Delete Account":
                deleteAccount();
                break;
            case "Show All Players":
                showAllPlayers();
                break;
            case "Create Account":
                showCreateAccount();
                break;
        }
    }
    /**
     * Add key press functionality to the given key to handle logic
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
        WindowUtils.setAppIcon(parentalControlsFrame);
        parentalControlsFrame.setLocationRelativeTo(null);
        parentalControlsFrame.setVisible(true);
        addKeyShortcuts();
    }
    /**
     * Move back to the main menu when called back.
     * 
     * @param screenToMoveTo The name of the screen to switch to, currently just the main menu.
     * @author Fardin Abbassi
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