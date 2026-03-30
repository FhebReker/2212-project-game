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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainMenuScreen implements Screen {
    // Main Menu Frame
    static JFrame mainMenuFrame = new JFrame("Staying Alive - Main Menu");

    // UI Colours
    final static Color backgroundPurple = new Color(106, 69, 156);
    static Color titleTextColour = new Color(255, 73, 164);
    static Color buttonBackground = new Color(0, 140, 255);
    static Color buttonTextColor = Color.black;

    // Navigation Buttons
    private JButton loginButton = new JButton("Login");
    private JButton tutorialButton = new JButton("Tutorial");
    private JButton parentalControlButton = new JButton("Parental Controls");

    // Labels
    private JLabel title = new JLabel();
    private JLabel subLabel = new JLabel();
    private JLabel credits = new JLabel();
    

    /** ADD DESCRIPTION HERE
     * Handle button clicks on main menu
     * <p>
     * Exit button exits application, login button moves to login screen, 
     * tutorial button moves to tutorial screen, and 
     * parental control button moves to parental control screen.
     * <p>
     * 
     * @param e
     */
    // 
	@Override
	public void actionPerformed(ActionEvent e) {
        // Switch to login screen if login button is pressed
        if (e.getSource() == loginButton) {this.moveToNextScreen("login");}
        // Switch to tutorial screen if tutorial button is pressed
        else if (e.getSource() == tutorialButton) {this.moveToNextScreen("tutorial");}
        // Switch to parental control screen if parental control button is pressed
        else if (e.getSource() == parentalControlButton) {this.moveToNextScreen("parental control");}
    }

    /** ADD DESCRIPTION HERE
     * 
     */
    @Override
    public void showScreen() {
        // Title
		title.setText("Staying Alive");
		title.setFont(new Font("Helvetica", Font.PLAIN, 60)); // adjust as needed
		title.setForeground(titleTextColour);
		title.setBounds(225, 20, 400, 80); // adjust as needed
        subLabel.setText("A Shebab Kebab Creation");
        subLabel.setFont(new Font("Helvetica", Font.PLAIN, 20)); // adjust as needed
        subLabel.setForeground(titleTextColour);
        subLabel.setBounds(285, 100, 300, 20); // adjust as needed

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
		
		// Add to frame
		mainMenuFrame.getContentPane().add(title);
		mainMenuFrame.getContentPane().add(subLabel);
		mainMenuFrame.getContentPane().add(loginButton);
		mainMenuFrame.getContentPane().add(tutorialButton);
		mainMenuFrame.getContentPane().add(parentalControlButton);
		mainMenuFrame.getContentPane().add(credits);
		
		// Set frame
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.getContentPane().setLayout(null);
		mainMenuFrame.getContentPane().setBackground(backgroundPurple);
		//mainMenuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/shebab_icon.png"))); // Shebab Kebab logo without text, adjust as needed
		mainMenuFrame.setBackground(backgroundPurple); // adjust as needed
        mainMenuFrame.setForeground(backgroundPurple);
		mainMenuFrame.setVisible(true);
        mainMenuFrame.setLocationRelativeTo(null);

    }

    /** ADD DESCRIPTION HERE
     * @param screenToMoveTo
     * @return Screen
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        // Open login screen as a pop-up
        if (screenToMoveTo.equals("login")) {NavigationControl.setCurrentScreen(1);}
        // Open tutorial screen
        else if (screenToMoveTo.equals("tutorial")) {NavigationControl.setCurrentScreen(2);}
        // Open parental control screen
        else if (screenToMoveTo.equals("parental control")) {NavigationControl.setCurrentScreen(3);}
    }

    /** ADD DESCRIPTION HERE
     * 
     */
    @Override
    public JFrame getFrame() {return mainMenuFrame;}
}