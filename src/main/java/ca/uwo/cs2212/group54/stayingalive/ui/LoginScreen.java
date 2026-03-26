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
    private JButton signInButton = new JButton("Login");    

    // Labels and Fields
    private JLabel usernameLabel = new JLabel();
	private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel();
	private JPasswordField userPasswordField = new JPasswordField();
	private JLabel messageLabel = new JLabel();

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // Check CSV file if in database, either move to player screen or show error message

        
    }

    /** ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void showScreen() {
        /* * Title
		title.setText("Staying Alive");
		title.setFont(new Font("Helvetica", Font.PLAIN, 60)); // adjust as needed
		title.setForeground(titleTextColour);
		title.setBounds(200, 20, 400, 80); // adjust as needed
        subLabel.setText("A Shebab Kebab Creation");
        subLabel.setFont(new Font("Helvetica", Font.PLAIN, 20)); // adjust as needed
        subLabel.setForeground(titleTextColour);
        subLabel.setBounds(275, 100, 300, 20); // adjust as needed

        // TODO: Add Logo in the middle of the frame(?)

		// Set Up Login Button
		loginButton.setFont(new Font("Helvetica", Font.PLAIN, 20)); // adjust as needed
        loginButton.setForeground(buttonTextColor);
		loginButton.setBackground(buttonBackground);
		loginButton.setBounds(300, 250, 200, 30); // adjust as needed
		loginButton.addActionListener(this);
	
		// Set Up Tutorial Button
		tutorialButton.setFont(new Font("Helvetica", Font.PLAIN, 20)); // adjust as needed
		tutorialButton.setForeground(buttonTextColor);
		tutorialButton.setBackground(buttonBackground);
		tutorialButton.setBounds(50, 250, 200, 30); // adjust as needed
		tutorialButton.addActionListener(this);

        // Set Up Parental Control Button
        parentalControlButton.setFont(new Font("Helvetica", Font.PLAIN, 20)); // adjust as needed
		parentalControlButton.setForeground(buttonTextColor);
		parentalControlButton.setBackground(buttonBackground);
		parentalControlButton.setBounds(550, 250, 200, 30); // adjust as needed
		parentalControlButton.addActionListener(this);

        // Set Up Credits
        credits.setText("<html>Created by Omar Soliman, Osman Idris, Mohamed Ahmed, Malik Alghneimin, and Fardin Abbassi as Group 54 for the CS 2212 course in the Winter 2026 term.</html>");
        credits.setFont(new Font("Helvetica", Font.PLAIN, 15)); // adjust as needed
        credits.setForeground(Color.white);
        credits.setBounds(150, 325, 550, 50); // adjust as needed
		
		// add to frame
		mainMenuFrame.getContentPane().add(title);
		mainMenuFrame.getContentPane().add(subLabel);
		mainMenuFrame.getContentPane().add(loginButton);
		mainMenuFrame.getContentPane().add(tutorialButton);
		mainMenuFrame.getContentPane().add(parentalControlButton);
		mainMenuFrame.getContentPane().add(credits);
		
		// set frame
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setSize(420, 420);
		mainMenuFrame.getContentPane().setLayout(null);
		mainMenuFrame.getContentPane().setBackground(backgroundPurple);
		//mainMenuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/shebab_icon.png"))); // Shebab Kebab logo without text, adjust as needed
		mainMenuFrame.setBackground(backgroundPurple); // adjust as needed
        mainMenuFrame.setForeground(backgroundPurple);
		mainMenuFrame.setVisible(true);
        */
    }

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo){
        // ADD DESCRIPTION HERE
    }

    /**ADD DESCRIPTION HERE
     * 
     */
    @Override
    public JFrame getFrame() {return loginFrame;}
}
