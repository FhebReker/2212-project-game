import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class LoginScreen implements Screen{
    // Login Frame
    static JFrame loginFrame = new JFrame("Staying Alive - Login");

    // UI Colours
    final static Color backgroundDeepPurple = new Color(80, 52, 117);
    static Color textColor = Color.white;
    static Color textFieldColor = new Color(224, 224, 224);
    static Color buttonBackground = new Color(102, 187, 255);
    static Color buttonTextColor = Color.black;

    // Navigation Buttons (forgot password? sign up?)
    private JButton loginButton = new JButton("Login");    

    // Labels and Fields
    private JLabel usernameLabel = new JLabel();
	private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel();
	private JPasswordField passwordField = new JPasswordField();
	private JLabel messageLabel = new JLabel();

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
        // Set up labels and fields
        usernameLabel.setText("User Name:");
        usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15)); // adjust as needed
        usernameLabel.setForeground(textColor);
        usernameLabel.setBounds(50, 100, 100, 25);
        passwordLabel.setText("Password:");
        passwordLabel.setFont(new Font("Helvetica", Font.PLAIN, 15)); // adjust as needed
        passwordLabel.setForeground(textColor);
		passwordLabel.setBounds(50, 150, 100, 25);

        // TODO: Adjust error message label once user login is fully implemented
		messageLabel.setBounds(125, 260, 250, 35);
		messageLabel.setFont(new Font(null, Font.BOLD, 15));

        // Set up text fields
        // TODO: Adjust texxt field properties (i.e. boldness) as needed
        usernameField.setBackground(textFieldColor);
        usernameField.setForeground(buttonTextColor);
        usernameField.setFont(new Font("Helvetica", Font.BOLD, 15)); // adjust as needed
		usernameField.setBounds(175, 100, 200, 25);
        passwordField.setBackground(textFieldColor);
        passwordField.setForeground(buttonTextColor);
        passwordField.setFont(new Font("Helvetica", Font.BOLD, 15)); // adjust as needed
		passwordField.setBounds(175, 150, 200, 25);

		// Set up login button
        // TODO: adjust button properties (i.e. size, position) as needed
		loginButton.setFont(new Font("Helvetica", Font.PLAIN, 16));
		loginButton.setForeground(buttonTextColor);
		loginButton.setBackground(buttonBackground);
		loginButton.setBounds(125, 200, 100, 25);
        loginButton.addActionListener(this);

        // Add to frame
		loginFrame.getContentPane().add(usernameLabel);
		loginFrame.getContentPane().add(passwordLabel);
		loginFrame.getContentPane().add(messageLabel);
		loginFrame.getContentPane().add(usernameField);
		loginFrame.getContentPane().add(passwordField);
		loginFrame.getContentPane().add(loginButton);

		
		// Set frame
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
            NavigationControl.setCurrentScreen(3);
        }
    }

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public JFrame getFrame() {return loginFrame;}
}