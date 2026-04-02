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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


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
        if (e.getActionCommand().equals("Login")) {this.moveToNextScreen("Login");}
        // Switch to tutorial screen if tutorial button is pressed
        else if (e.getActionCommand().equals("Tutorial")) {this.moveToNextScreen("Tutorial");}
        // Switch to parental control screen if parental control button is pressed
        else if (e.getActionCommand().equals("Parental Controls")) {this.moveToNextScreen("Parental Controls");}
    }

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

    /** ADD DESCRIPTION HERE
     *
     */
    @Override
    public void showScreen() {
        mainMenuFrame.getContentPane().removeAll();
        buildUI();
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setBackground(backgroundPurple);
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
        if (screenToMoveTo.equals("Login")) {
            System.out.println("logging in");
            NavigationControl.setCurrentScreen(1);
        }
        // Open tutorial screen
        else if (screenToMoveTo.equals("Tutorial")) {
            System.out.println("reading tutorial");
            NavigationControl.setCurrentScreen(2);
        }
        // Open parental control screen
        else if (screenToMoveTo.equals("Parental Controls")) {
            System.err.println("ERROR: have yet to implement parental controls");
            //NavigationControl.setCurrentScreen(6); // TODO: implement parental controls screen
        }
    }

    /** ADD DESCRIPTION HERE
     * 
     */
    @Override
    public JFrame getFrame() {return mainMenuFrame;}
}