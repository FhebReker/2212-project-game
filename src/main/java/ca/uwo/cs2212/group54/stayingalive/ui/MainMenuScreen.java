import javax.swing.*;
import java.awt.*;
import java.awt.event.*;;


public class MainMenuScreen implements Screen {
    // Main Menu Frame
    static JFrame mainMenuFrame = new JFrame("Staying Alive - Main Menu");

    // UI Colours
    final static Color backgroundPurple = new Color(106, 69, 156);
    static Color titleTextColour = new Color(255, 73, 164);
    static Color buttonBackground = new Color(0, 140, 255);
    static Color buttonTextColor = Color.black;

    // Navigation Buttons
    private JButton exitButton = new JButton("Exit");
    private JButton loginButton = new JButton("Login");
    private JButton tutorialButton = new JButton("Tutorial");
    private JButton parentalControlButton = new JButton("Parental Controls");

    // Labels
    private JLabel title = new JLabel();
    private JLabel subLabel = new JLabel();
    private JLabel credits = new JLabel();
    

    /** 
     * @param e
     */
    // 
	@Override
	public void actionPerformed(ActionEvent e) {
        // Exit if exit button is pressed
        if (e.getSource() == exitButton) {
            System.exit(0);
        }

        // Switch to login screen if login button is pressed
        else if (e.getSource() == loginButton) {
            this.moveToNextScreen(null);
            
        }

        // Switch to tutorial screen if tutorial button is pressed
        else if (e.getSource() == tutorialButton) {

        }

        // Switch to parental control screen if parental control button is pressed
        else if (e.getSource() == parentalControlButton) {

        }
    }

    
    @Override
    public void showScreen() {
        // Title
		title.setText("Staying Alive");
		title.setFont(new Font("Helvetica", Font.PLAIN, 30)); // adjust as needed
		title.setForeground(titleTextColour);
		title.setBounds(50, 20, 300, 40); // adjust as needed
        subLabel.setText("A Shebab Kebab Creation");
        subLabel.setFont(new Font("Helvetica", Font.PLAIN, 16)); // adjust as needed
        subLabel.setForeground(titleTextColour);
        subLabel.setBounds(100, 60, 300, 20); // adjust as needed

		// Set Up Login Button
		loginButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // adjust as needed
		loginButton.setForeground(buttonTextColor);
		loginButton.setBackground(buttonBackground);
		loginButton.setBounds(125, 200, 100, 25); // adjust as needed
		loginButton.addActionListener(this);
	
		// Set Up Tutorial Button
		tutorialButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // adjust as needed
		tutorialButton.setForeground(buttonTextColor);
		tutorialButton.setBackground(buttonBackground);
		tutorialButton.setBounds(125, 200, 100, 25); // adjust as needed
		tutorialButton.addActionListener(this);

        // Set Up Parental Control Button
        tutorialButton.setFont(new Font("Helvetica", Font.PLAIN, 16)); // adjust as needed
		tutorialButton.setForeground(buttonTextColor);
		tutorialButton.setBackground(buttonBackground);
		tutorialButton.setBounds(125, 200, 100, 25); // adjust as needed
		tutorialButton.addActionListener(this);

        // Set Up Credits
        credits.setText("Created by Omar Soliman, Osman Idris, Mohamed Ahmed, Malik Alghneimin, and Fardin Abbassi as Group 54 for the CS 2212 course in the Winter 2026 term.");
        credits.setFont(new Font("Helvetica", Font.PLAIN, 10)); // adjust as needed
        credits.setForeground(Color.white);
        credits.setBounds(10, 350, 400, 20); // adjust as needed
		
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
		mainMenuFrame.getContentPane().setBackground(new Color(220, 242, 206));
		mainMenuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/shebab_icon.png"))); // Shebab Kebab logo without text, adjust as needed
		mainMenuFrame.setBackground(backgroundPurple); // adjust as needed
		mainMenuFrame.setVisible(true);
    }

    /** 
     * @param screenToMoveTo
     * @return Screen
     */
    @Override
    public Screen moveToNextScreen(String screenToMoveTo) {
        // return next screen here
        return null;
    }
}