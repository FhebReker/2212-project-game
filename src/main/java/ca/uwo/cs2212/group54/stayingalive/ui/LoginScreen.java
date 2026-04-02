package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Check CSV file if in database, either move to player screen or show error message (currently just moves to player screen for sake of tests)
        // Only close login screen if exitbutton or proper login
        if(e.getSource() == loginButton) {
            // Until proper implementation of login, just move to player menu screen

            this.moveToNextScreen("Player"); // test with player screen later
        }
    }

    /** ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void showScreen() {
        // Clear frame
        loginFrame.getContentPane().removeAll();
        loginFrame.getContentPane().setLayout(null);

        // Set up labels and fields
        usernameLabel = new JLabel("User Name:");
        usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15)); // adjust as needed
        usernameLabel.setForeground(textColor);
        usernameLabel.setBounds(50, 100, 100, 25);
        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 15)); // adjust as needed
        passwordLabel.setForeground(textColor);
		passwordLabel.setBounds(50, 150, 100, 25);

        // TODO: Adjust error message label once user login is fully implemented
        messageLabel = new JLabel(""); // add text later
		messageLabel.setBounds(125, 260, 250, 35);
		messageLabel.setFont(new Font(null, Font.BOLD, 15));

        // Set up text fields
        // TODO: Adjust text field properties (i.e. boldness) as needed
        usernameField = new JTextField();
        usernameField.setBackground(textFieldColor);
        usernameField.setForeground(buttonTextColor);
        usernameField.setFont(new Font("Helvetica", Font.BOLD, 15)); // adjust as needed
		usernameField.setBounds(175, 100, 200, 25);
        
        passwordField = new JPasswordField();
        passwordField.setBackground(textFieldColor);
        passwordField.setForeground(buttonTextColor);
        passwordField.setFont(new Font("Helvetica", Font.BOLD, 15)); // adjust as needed
		passwordField.setBounds(175, 150, 200, 25);

		// Set up login button
        // TODO: adjust button properties (i.e. size, position) as needed
        loginButton = new JButton("Login");
		loginButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
		loginButton.setForeground(buttonTextColor);
		loginButton.setBackground(buttonBackground);
		loginButton.setBounds(125, 200, 100, 25);
        //for (ActionListener al : loginButton.getActionListeners()) {loginButton.removeActionListener(al);}
        loginButton.addActionListener(this);
        
        // Add to frame
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
		//loginFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/shebab_icon.png"))); // Shebab Kebab logo without text, adjust as needed
		loginFrame.setBackground(backgroundDeepPurple); // adjust as needed
        loginFrame.setForeground(backgroundDeepPurple);
		loginFrame.setVisible(true);
    }

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo){
        // ADD DESCRIPTION HERE
        if (screenToMoveTo.equals("Player")) {
            MainMenuScreen mainMenu = (MainMenuScreen) NavigationControl.getScreen(0);
            if (mainMenu != null && mainMenu.getFrame() != null) mainMenu.getFrame().dispose(); // close main menu screen if open
            NavigationControl.setCurrentScreen(3);
        }
    }

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public JFrame getFrame() {return loginFrame;}
}