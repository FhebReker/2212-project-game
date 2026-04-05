package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ca.uwo.cs2212.group54.stayingalive.accounts.AccountManagement;
import ca.uwo.cs2212.group54.stayingalive.audio.AudioManager;

/**
 * Login screen for the application.
 * 
 * <p>
 * Takes a username and password entered in its fields and checks using the {@link AccountManagement.java} class if it's in the {@link players.json} file.
 * Supports button clicking and pressing the enter key from any field to login.
 * @author Fardin Abbassi
 */

public class LoginScreen implements Screen{
    // Login Frame
    private JFrame loginFrame = new JFrame("Staying Alive - Login");

    // UI Colours
    private final static Color backgroundDeepPurple = new Color(80, 52, 117);
    private final static Color textColor = Color.white;
    private final static Color textFieldColor = new Color(224, 224, 224);
    private final static Color buttonBackground = new Color(102, 187, 255);
    private final static Color buttonTextColor = Color.black;

    // Navigation Buttons (forgot password? sign up?)
    private JButton loginButton;

    // Labels and Fields
    private JLabel usernameLabel;
	private JTextField usernameField;
    private JLabel passwordLabel;
	private JPasswordField passwordField;
	private JLabel messageLabel;

    /**
     * Check if the user is in the system.
     * 
     * <p>
     * Either move to player screen or display error message depending on 
     * whether or not the user is in the system.
     * 
     * @author Fardin Abbassi
     */
    private void checkUserLogin() {
        boolean correctLogin = NavigationControl.getAccountManager().checkUserLogin(usernameField.getText(), new String(passwordField.getPassword()));
        if (correctLogin) { 
            AccountManagement.setCurrentAccount(NavigationControl.getAccountManager().getParental().getAccount(usernameField.getText()));
            NavigationControl.startTimer();
            this.moveToNextScreen("Player"); // test with player screen later 
        }
        else messageLabel.setVisible(true);
    }
    
    /**
     * Helper function to reset the message when either the username or password fields change.
     * @author Fardin Abbassi
     */
    private void addMessageResetOnType() {
        DocumentListener resetListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { messageLabel.setVisible(false); }
            @Override
            public void removeUpdate(DocumentEvent e) { messageLabel.setVisible(false); }
            @Override
            public void changedUpdate(DocumentEvent e) { messageLabel.setVisible(false); }
        };
        usernameField.getDocument().addDocumentListener(resetListener);
        passwordField.getDocument().addDocumentListener(resetListener);
    }
    /**
     * Helper function to make adding shortcuts easier to read by setting key binds to both fields.
     * @author Fardin Abbassi
     */
    private void addKeyShortcuts() {
        addKeyShortcut(usernameField, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); checkUserLogin(); }
        });
        addKeyShortcut(passwordField, KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); checkUserLogin(); }
        });
        addKeyShortcut((JPanel)loginFrame.getContentPane(),KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); moveToNextScreen("Back"); }
        });
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
     * Add listener to the login button to handle logins
     * 
     * @param e The ActionEvent of the button being clicked
     * @author Fardin Abbassi
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        AudioManager.playButtonClick();
        if(e.getSource() == loginButton) { checkUserLogin(); }
    }

    /**
     * Initializes and displays the components of the frame.
     * @author Fardin Abbassi
     */
    @Override
    public void showScreen() {
        // Set up labels and fields
        usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        usernameLabel.setForeground(textColor);
        usernameLabel.setBounds(50, 75, 100, 25);
        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        passwordLabel.setForeground(textColor);
		passwordLabel.setBounds(50, 125, 100, 25);

        messageLabel = new JLabel("ERROR: Incorrect login");
		messageLabel.setBounds(125, 235, 250, 35);
        messageLabel.setForeground(Color.red);
		messageLabel.setFont(new Font(null, Font.BOLD, 15));
        messageLabel.setVisible(false);

        // Set up text fields
        usernameField = new JTextField();
        usernameField.setBackground(textFieldColor);
        usernameField.setForeground(buttonTextColor);
        usernameField.setFont(new Font("Helvetica", Font.BOLD, 15));
		usernameField.setBounds(175, 75, 200, 25);
        
        passwordField = new JPasswordField();
        passwordField.setBackground(textFieldColor);
        passwordField.setForeground(buttonTextColor);
        passwordField.setFont(new Font("Helvetica", Font.BOLD, 15));
		passwordField.setBounds(175, 125, 200, 25);

        addMessageResetOnType();

		// Set up login
        loginButton = new JButton("Login");
		loginButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
		loginButton.setForeground(buttonTextColor);
		loginButton.setBackground(buttonBackground);
		loginButton.setBounds(150, 175, 100, 25);
        loginButton.addActionListener(this);
        addKeyShortcuts();
        
        // Clear and add to frame
        loginFrame.getContentPane().removeAll();
        loginFrame.getContentPane().setLayout(null);
		loginFrame.getContentPane().add(usernameLabel);
		loginFrame.getContentPane().add(passwordLabel);
		loginFrame.getContentPane().add(messageLabel);
		loginFrame.getContentPane().add(usernameField);
		loginFrame.getContentPane().add(passwordField);
		loginFrame.getContentPane().add(loginButton);

		
		// Set up frame
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // only close login screen, not whole app
		loginFrame.setSize(NavigationControl.screenW / 2, NavigationControl.screenH / 2 + 100);
		loginFrame.getContentPane().setLayout(null);
		loginFrame.getContentPane().setBackground(backgroundDeepPurple);
		//loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/logo.png"))); // TODO: Try to figure out why logo isn't coming
		loginFrame.setVisible(true);
        WindowUtils.setAppIcon(loginFrame);
    }

    /**
     * Moves to the next screen.
     * 
     * <p>
     * Disposes of the main menu, then moves to the player screen on successful login.
     * Moves back to the main menu if the escape key is pressed.
     * 
     * @param screenToMoveTo The screen to move to
     * @author Fardin Abbassi
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo){
        // Move to next screen on successful login
        if (screenToMoveTo.equals("Player")) {
            MainMenuScreen mainMenu = (MainMenuScreen) NavigationControl.getScreen(0);
            if (mainMenu != null && mainMenu.getFrame() != null) mainMenu.getFrame().dispose(); // close main menu screen if open
            NavigationControl.setCurrentScreen(3);
        }
        // Dispose of this frame if exited
        else if (screenToMoveTo.equals("Back")) { NavigationControl.setCurrentScreen(0); }
    }

    /**
     * Gets the frame of the screen.
     * @return The login screen's frame
     * @author Fardin Abbassi
     */
    @Override
    public JFrame getFrame() { return loginFrame; }
}
