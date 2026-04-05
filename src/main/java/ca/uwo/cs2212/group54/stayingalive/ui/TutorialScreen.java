package ca.uwo.cs2212.group54.stayingalive.ui;

/**
 * TutorialScreen class represents the tutorial screen of Staying Alive.
 * <p>
 * TutorialScreen implements the Screen interface,
 * so it has methods to show the screen, move to the next screen,
 * and get the class' frame.
 * Since Screen implements ActionListener, it also has a method to handle button clicks.
 * <p>
 * The tutorial cycles through multiple text-based pages explaining how to play the game.
 * Navigation arrows let the user move forward/backward, dots show the current page,
 * and a back button returns to the previous menu.
 *
 * @author Fardin Abbassi
 * @author Omar Soliman
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import ca.uwo.cs2212.group54.stayingalive.audio.AudioManager;

public class TutorialScreen implements Screen {
    // Tutorial Frame
    private JFrame tutorialFrame = new JFrame("Tutorial Screen");

    // UI Colours
    private final static Color BACKGROUND_PURPLE = new Color(106, 69, 156);
    private final static Color BOX_BACKGROUND     = new Color(80, 50, 120);
    private final static Color TEXT_WHITE          = Color.WHITE;
    private final static Color DOT_ACTIVE          = Color.WHITE;
    private final static Color DOT_INACTIVE        = new Color(120, 100, 150);

    // Tutorial pages: each entry is { title, body }
    private final static String[][] PAGES = {
        {
            "Welcome to Staying Alive!",
            "<html><div style='text-align:center;'>"
            + "Staying Alive is a <b>typing game</b> where words hit the screen fast<br>"
            + "and you have to type them before time runs out.<br><br>"
            + "Type fast. Type accurate. Stay alive.<br><br>"
            + "Hit the arrow to get started!"
            + "</div></html>"
        },
        {
            "Type to Survive",
            "<html><div style='text-align:center;'>"
            + "Words drop onto the screen. <b>Type them exactly</b> to destroy them.<br><br>"
            + "Every round is on a <b>timer</b>, so hesitation will cost you.<br><br>"
            + "Miss a word or type it wrong and you take <b>damage</b>.<br>"
            + "Let your health hit zero and it is <b>game over</b>."
            + "</div></html>"
        },
        {
            "Level Up or Die Trying",
            "<html><div style='text-align:center;'>"
            + "Each level you clear ramps up the challenge.<br><br>"
            + "Words get <b>longer</b>, more <b>complex</b>, and the clock gets<br>"
            + "tighter with every stage.<br><br>"
            + "Chain enough clears to push through to the next level.<br>"
            + "Keep up or get left behind!"
            + "</div></html>"
        },
        {
            "Health, Points & Power-Ups",
            "<html><div style='text-align:center;'>"
            + "You start each run with a fixed amount of <b>health</b>.<br>"
            + "Every mistake chips away at it, so play smart.<br><br>"
            + "Nailing words earns you <b>points</b> and builds your score.<br><br>"
            + "<b>Power-Ups</b> show up mid-game. Type them fast to grab<br>"
            + "bonuses like extra time or health restored."
            + "</div></html>"
        },
        {
            "Keyboard Shortcuts",
            "<html><div style='text-align:center;'>"
            + "Navigate the game quickly with these shortcuts:<br><br>"
            + "<b>T</b> &nbsp;&mdash;&nbsp; Open the <b>Tutorial</b><br><br>"
            + "<b>P</b> &nbsp;&mdash;&nbsp; Open <b>Parental Controls</b><br><br>"
            + "<b>L</b> &nbsp;&mdash;&nbsp; Go to the <b>Login</b> screen<br><br>"
            + "Use these from the main menu to jump straight where you need to go.<br><br>"
            + "Usually, these shortcuts will be available through the menus using the first letter of the button."
            + "</div></html>"
        }
    };

    // State
    private int currentPage = 0;

    // UI Components (recreated on each showScreen call)
    private JPanel  displayBox;
    private JLabel  pageTitle;
    private JLabel  pageBody;
    private JPanel  dotsPanel;
    private JButton leftArrowButton;
    private JButton rightArrowButton;
    private JButton backToPreviousButton;

    /**
     * General navigation for both keyboard and button presses.
     * 
     * <p>
     * Left arrow moves to the previous page, right arrow moves to the next page,
     * and the back button returns to the previous menu.
     *
     * @param command The name of the command used
     * @author Omar Soliman
     * @author Fardin Abbassi
     */
    private void navigateTo(String command) {
        switch (command) {
            case "Move Left":
                if (currentPage > 0) {
                    currentPage--;
                    refreshPage();
                }
                break;
            case "Move Right":
                if (currentPage < PAGES.length - 1) {
                    currentPage++;
                    refreshPage();
                }
                break;
            case "Back":
                this.moveToNextScreen("Back");
                break;
        }
    }
    /**
     * Handle button clicks on the tutorial screen.
     *
     * @param e the ActionEvent triggered by a button click
     * @author Fardin Abbassi
     * @author Omar Soliman
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        AudioManager.playButtonClick();
        if(e.getActionCommand() != null) navigateTo(e.getActionCommand());
    }

    /**
     * Updates the display box content and dot indicators to reflect the current page
     * without rebuilding the entire frame.
     * 
     * @author Omar Soliman
     */
    private void refreshPage() {
        pageTitle.setText(PAGES[currentPage][0]);
        pageBody.setText(PAGES[currentPage][1]);

        // Update dot colours
        for (int i = 0; i < dotsPanel.getComponentCount(); i++) {
            JLabel dot = (JLabel) dotsPanel.getComponent(i);
            dot.setForeground(i == currentPage ? DOT_ACTIVE : DOT_INACTIVE);
        }

        // Show/hide arrows based on position
        leftArrowButton.setVisible(currentPage > 0);
        rightArrowButton.setVisible(currentPage < PAGES.length - 1);

        tutorialFrame.repaint();
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
     * Builds and displays the tutorial screen with all components.
     * 
     * @author Fardin Abbassi
     * @author Omar Soliman
     */
    @Override
    public void showScreen() {
        tutorialFrame.getContentPane().removeAll();
        currentPage = 0;

        // --- Root panel ---
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND_PURPLE);

        // --- NORTH: back button pinned to top-right ---
        Image rawBack = new ImageIcon("global/back.png").getImage()
                            .getScaledInstance(34, 34, Image.SCALE_SMOOTH);
        backToPreviousButton = new JButton(new ImageIcon(rawBack));
        backToPreviousButton.setContentAreaFilled(false);
        backToPreviousButton.setBorderPainted(false);
        backToPreviousButton.setFocusPainted(false);
        backToPreviousButton.setActionCommand("Back");
        backToPreviousButton.addActionListener(this);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        topBar.setOpaque(false);
        topBar.add(backToPreviousButton);
        root.add(topBar, BorderLayout.NORTH);

        // --- CENTER: left arrow | display box | right arrow ---
        leftArrowButton  = makeArrowButton("\u2039", "Move Left");
        rightArrowButton = makeArrowButton("\u203a", "Move Right");
        leftArrowButton.setVisible(false); // hidden on first page

        // Arrow wrapper panels give consistent padding on each side
        JPanel leftWrap = new JPanel(new GridBagLayout());
        leftWrap.setOpaque(false);
        leftWrap.setPreferredSize(new Dimension(80, 0));
        leftWrap.add(leftArrowButton);

        JPanel rightWrap = new JPanel(new GridBagLayout());
        rightWrap.setOpaque(false);
        rightWrap.setPreferredSize(new Dimension(80, 0));
        rightWrap.add(rightArrowButton);

        // Display box
        displayBox = new JPanel(new BorderLayout(0, 10));
        displayBox.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        displayBox.setBackground(BOX_BACKGROUND);

        pageTitle = new JLabel(PAGES[0][0], SwingConstants.CENTER);
        pageTitle.setFont(new Font("Helvetica", Font.BOLD, 18));
        pageTitle.setForeground(TEXT_WHITE);
        pageTitle.setBorder(BorderFactory.createEmptyBorder(18, 10, 0, 10));

        pageBody = new JLabel(PAGES[0][1], SwingConstants.CENTER);
        pageBody.setFont(new Font("Helvetica", Font.PLAIN, 13));
        pageBody.setForeground(TEXT_WHITE);
        pageBody.setVerticalAlignment(SwingConstants.TOP);
        pageBody.setBorder(BorderFactory.createEmptyBorder(0, 18, 18, 18));

        displayBox.add(pageTitle, BorderLayout.NORTH);
        displayBox.add(pageBody,  BorderLayout.CENTER);

        displayBox.setPreferredSize(new Dimension(470, 300));

        JPanel centerRow = new JPanel(new BorderLayout());
        centerRow.setOpaque(false);
        centerRow.add(leftWrap,   BorderLayout.WEST);
        centerRow.add(displayBox, BorderLayout.CENTER);
        centerRow.add(rightWrap,  BorderLayout.EAST);

        // Wrap in GridBagLayout so centerRow keeps its preferred size and stays centred
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(centerRow);
        root.add(centerWrapper, BorderLayout.CENTER);

        // --- SOUTH: progress dots ---
        dotsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        dotsPanel.setOpaque(false);
        for (int i = 0; i < PAGES.length; i++) {
            JLabel dot = new JLabel("\u25CF"); // ●
            dot.setFont(new Font("Helvetica", Font.PLAIN, 16));
            dot.setForeground(i == 0 ? DOT_ACTIVE : DOT_INACTIVE);
            dotsPanel.add(dot);
        }
        root.add(dotsPanel, BorderLayout.SOUTH);

        // --- Frame settings ---
        tutorialFrame.setContentPane(root);
        tutorialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tutorialFrame.setBackground(BACKGROUND_PURPLE);
        WindowUtils.setAppIcon(tutorialFrame);
        tutorialFrame.setVisible(true);
        tutorialFrame.setLocationRelativeTo(null);
        addKeyShortcut((JPanel)tutorialFrame.getContentPane(),KeyEvent.VK_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Move Left"); }
        });
        addKeyShortcut((JPanel)tutorialFrame.getContentPane(),KeyEvent.VK_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Move Right"); }
        });
        addKeyShortcut((JPanel)tutorialFrame.getContentPane(),KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Back"); }
        });
    }

    /**
     * Creates a styled arrow button with a Unicode chevron character.
     *
     * @param symbol the chevron character to display
     * @param command the action command string
     * @return the configured JButton
     * @author Omar Soliman
     */
    private JButton makeArrowButton(String symbol, String command) {
        JButton btn = new JButton(symbol);
        btn.setFont(new Font("Helvetica", Font.PLAIN, 48));
        btn.setForeground(TEXT_WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setActionCommand(command);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * Moves to another screen.
     * "Back" navigates to the main menu.
     *
     * @param screenToMoveTo the target screen identifier
     * @author Omar Soliman
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        if (screenToMoveTo.equals("Back")) {
            NavigationControl.goBack();
        }
    }

    /**
     * Returns the JFrame for this screen.
     *
     * @return the tutorial JFrame
     * @author Fardin Abbassi
     */
    @Override
    public JFrame getFrame() { return tutorialFrame; }
}