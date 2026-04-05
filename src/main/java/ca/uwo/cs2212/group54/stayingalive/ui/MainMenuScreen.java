package ca.uwo.cs2212.group54.stayingalive.ui;

/**
 * MainMenuScreen class represents the main menu screen of Staying Alive.
 * <p>
 * MainMenuScreen implements the Screen interface, 
 * so it has methods to show the screen, move to the next screen, 
 * and get the class' frame.
 * Since Screen implements ActionListener, it also has a method to handle button clicks.
 * 
 * @author Fardin Abbassi
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ca.uwo.cs2212.group54.stayingalive.audio.AudioManager;


public class MainMenuScreen implements Screen {
    // Main Menu Frame
    private JFrame mainMenuFrame = new JFrame("Staying Alive - Main Menu");

    // UI Colours
    private static final Color backgroundPurple = new Color(106, 69, 156);
    private static final Color titleTextColour = new Color(255, 73, 164);
    private static final Color buttonBackground = new Color(0, 140, 255);
    private static final Color buttonTextColor = Color.black;

    // Navigation Buttons
    private JButton loginButton;
    private JButton tutorialButton;
    private JButton parentalControlButton;

    // Labels
    private JLabel title;
    private JLabel subLabel;
    private JLabel credits;
    

    // ── Navigation Control ────
    /**
     * General navigation for both keyboard and button presses.
     * @param command The name of the command used
     * @author Fardin Abbassi
     */
    private void navigateTo(String command) {
        // move from this class to the next screen based on the button clicked
            System.out.println("to " + command);
            this.moveToNextScreen(command);
    }
    /**
     * Helper function to reset the message when either the username or password fields change.
     * @author Fardin Abbassi
     */
    private void addMessageResetOnType(JLabel messageLabel, JPasswordField masterPassField) {
        DocumentListener resetListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {messageLabel.setVisible(false); }
            @Override
            public void removeUpdate(DocumentEvent e) { messageLabel.setVisible(false); }
            @Override
            public void changedUpdate(DocumentEvent e) { messageLabel.setVisible(false); }
        };
        masterPassField.getDocument().addDocumentListener(resetListener);
    }
    /**
     * Helper function to make adding shortcuts easier to read by setting key binds to both fields.
     * @author Fardin Abbassi
     */
    private void keyShortcutOnParentalPass(JPasswordField masterPassField, JDialog dialog, JLabel messageLabel) {
        addKeyShortcut(masterPassField, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); 
                String entered = new String(masterPassField.getPassword());
                if (NavigationControl.getAccountManager().checkMasterPass(entered)) {
                    dialog.dispose();
                    NavigationControl.setCurrentScreen(6);
                } else {
                    messageLabel.setVisible(true);
                }
             }
        });
        addKeyShortcut((JPanel)dialog.getContentPane(),KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); dialog.dispose(); }
        });
    }


    /** 
     * Handle button clicks on main menu
     * <p>
     * Exit button exits application, login button moves to login screen, 
     * tutorial button moves to tutorial screen, and 
     * parental control button moves to parental control screen.
     * <p>
     * 
     * @param e ActionEvent of the button pressed
     * @author Fardin Abbassi
     */
	@Override
	public void actionPerformed(ActionEvent e) {
        AudioManager.playButtonClick();
        // Switch to login screen if login button is pressed
        if (e.getActionCommand() != null) { navigateTo(e.getActionCommand()); }
    }
    /**
     * Helper function to add navigation to key inputs to the given target component.
     * @param target Target JComponent to add key inputs to
     * @author Fardin Abbassi
     */
    private void addKeyShortcuts(JComponent target) {
        // Tutorial: T
        addKeyShortcut(target, KeyEvent.VK_T, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Tutorial"); }
        });
        
        // Login: L
        addKeyShortcut(target, KeyEvent.VK_L, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Login"); }
        });

        // Parental Controls: P
        addKeyShortcut(target, KeyEvent.VK_P, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Parental Controls"); }
        });

        // Exit App: Esc
        addKeyShortcut(target, KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Exit"); }
        });
    }
    /**
     * Add key press functionality to a given key.
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
     * Helper function for building UI by initializing all the components and putting them all together.
     * @author Omar Soliman
     */
    private void buildUI() {
        // BorderLayout: title NORTH, logo CENTER (scales), subtitle+buttons+credits SOUTH
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundPurple);

        // --- NORTH: title ---
        title = new JLabel("Staying Alive", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.PLAIN, 60));
        title.setForeground(titleTextColour);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // --- CENTER: logo at fixed size, centred — extra window space becomes padding ---
        Image logoRaw = new ImageIcon("global/logo.png").getImage()
                            .getScaledInstance(260, 195, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoRaw));
        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setOpaque(false);
        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel, BorderLayout.CENTER);

        // --- SOUTH: subtitle + buttons + credits ---
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        subLabel = new JLabel("A Shebab Kebab Creation", SwingConstants.CENTER);
        subLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
        subLabel.setForeground(titleTextColour);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        buttons.setOpaque(false);
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);

        tutorialButton        = new JButton("Tutorial");
        loginButton           = new JButton("Login");
        parentalControlButton = new JButton("Parental Controls");

        for (JButton btn : new JButton[]{tutorialButton, loginButton, parentalControlButton}) {
            btn.setFont(new Font("Helvetica", Font.PLAIN, 18));
            btn.setForeground(buttonTextColor);
            btn.setBackground(buttonBackground);
            btn.setPreferredSize(new Dimension(170, 38));
            btn.addActionListener(this);
            buttons.add(btn);
        }
        tutorialButton.setActionCommand("Tutorial");
        loginButton.setActionCommand("Login");
        parentalControlButton.setActionCommand("Parental Controls");

        credits = new JLabel(
            "<html><center>Created by Omar Soliman, Osman Idris, Mohamed Ahmed, Malik Alghneimin, and Fardin Abbassi"
            + "<br>as Group 54 for the CS 2212 course in the Winter 2026 term.</center></html>",
            SwingConstants.CENTER);
        credits.setFont(new Font("Helvetica", Font.PLAIN, 11));
        credits.setForeground(new Color(220, 200, 240));
        credits.setAlignmentX(Component.CENTER_ALIGNMENT);

        south.add(subLabel);
        south.add(Box.createVerticalStrut(16));
        south.add(buttons);
        south.add(Box.createVerticalStrut(12));
        south.add(credits);

        mainPanel.add(south, BorderLayout.SOUTH);
        mainMenuFrame.setContentPane(mainPanel);
    }

    /**
     * Call on the buildUI helper function and set up the frame.
     * @author Omar Soliman
     */
    @Override
    public void showScreen() {
        mainMenuFrame.getContentPane().removeAll();
        buildUI();
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setBackground(backgroundPurple);
        mainMenuFrame.setVisible(true);
        mainMenuFrame.setLocationRelativeTo(null);
        WindowUtils.setAppIcon(mainMenuFrame);
        addKeyShortcuts((JPanel) mainMenuFrame.getContentPane());
    }

    /** 
     * Moves to the specified screen.
     * <p>
     * For each of the buttons, the screen shifts to that specified screen. 
     * In the case of the escape button being pressed, the user is given an option pane to choose to exit the app.
     * @param screenToMoveTo
     * @author Fardin Abbassi
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        // Open login screen as a pop-up
        if (screenToMoveTo.equals("Login")) {
            System.out.println("logging in");
            NavigationControl.setCurrentScreen(1);
        }
        // Open tutorial screen
        else if (screenToMoveTo.equals("Tutorial")) {
            System.out.println("reading tutorial");
            NavigationControl.setCurrentScreen(2);
        }
        // Check for the master password, then open parental controls screen on correct login
        else if (screenToMoveTo.equals("Parental Controls")) {
            // Styled master-password dialog matching the student login screen
            JDialog dialog = new JDialog(mainMenuFrame, "Parental Controls", true);
            dialog.setSize(400, 250);
            dialog.setLayout(null);
            dialog.getContentPane().setBackground(new Color(80, 52, 117));

            JLabel passLabel = new JLabel("Master Password:");
            passLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
            passLabel.setForeground(Color.WHITE);
            passLabel.setBounds(50, 80, 150, 25);

            JPasswordField passField = new JPasswordField();
            passField.setBackground(new Color(224, 224, 224));
            passField.setForeground(Color.BLACK);
            passField.setFont(new Font("Helvetica", Font.BOLD, 15));
            passField.setBounds(210, 80, 150, 25);

            JLabel errorLabel = new JLabel("Incorrect password.");
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font(null, Font.BOLD, 14));
            errorLabel.setBounds(120, 160, 200, 25);
            errorLabel.setVisible(false);

            JButton enterButton = new JButton("Enter");
            enterButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
            enterButton.setForeground(Color.BLACK);
            enterButton.setBackground(new Color(102, 187, 255));
            enterButton.setBounds(150, 120, 100, 28);
            enterButton.addActionListener(ev -> {
                AudioManager.playButtonClick();
                String entered = new String(passField.getPassword());
                if (NavigationControl.getAccountManager().checkMasterPass(entered)) {
                    dialog.dispose();
                    NavigationControl.setCurrentScreen(6);
                } else {
                    errorLabel.setVisible(true);
                }
            });
            addMessageResetOnType(errorLabel, passField);
            keyShortcutOnParentalPass(passField, dialog, errorLabel);

            dialog.add(passLabel);
            dialog.add(passField);
            dialog.add(enterButton);
            dialog.add(errorLabel);
            dialog.setLocationRelativeTo(mainMenuFrame);
            dialog.setVisible(true);
        }
        // Give the user the option to leave the app
        else if (screenToMoveTo.equals("Exit")) {
            int response = JOptionPane.showConfirmDialog(
                mainMenuFrame,
                "Are you sure you want to exit Staying Alive?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
            
    }

    /** 
     * Gets the JFrame of the screen,
     * @return The frame of the main menu screen
     * @author Fardin Abbassi
     */
    @Override
    public JFrame getFrame() {return mainMenuFrame;}
}