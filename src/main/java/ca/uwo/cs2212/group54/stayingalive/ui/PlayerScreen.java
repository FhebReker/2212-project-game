package ca.uwo.cs2212.group54.stayingalive.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

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

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.accounts.AccountManagement;
import ca.uwo.cs2212.group54.stayingalive.audio.AudioManager;

/**
 * PlayerScreen – the hub screen shown after a player logs in.
 *
 * Layout (matches the design mockup):
 *   - Purple background
 *   - "Player Screen" title at top-left
 *   - Logout (back-arrow) button at top-right
 *   - Current avatar image (placeholder kitten) centred in the screen
 *   - Four navigation buttons along the bottom:
 *       New Game | Continue Game | Game Store | Stats
 * 
 * @author Omar Soliman
 * @author Fardin Abbassi
 */
public class PlayerScreen implements Screen {

    // ── Colour palette ────────────────────────────────────────────────────
    private static final Color BG_COLOR      = new Color(0x6A, 0x5A, 0xCD);
    private static final Color BUTTON_COLOR  = new Color(0x00, 0xBF, 0xFF);
    private static final Color BUTTON_HOVER  = new Color(0x00, 0x9A, 0xCD);
    private static final Color BUTTON_TEXT   = Color.WHITE;

    // ── Fields ────────────────────────────────────────────────────────────
    private Image avatarImage;
    private JFrame playerFrame;
    private String avatarPath;
    private Account user;

    // ── Constructor ───────────────────────────────────────────────────────
    /**
     * Constructor for the class.
     * 
     * <p>
     * Sets the avatar image path to whatever is specified, or defaults to the placeholder image.
     * @param avatarPath resource path to the avatar image (e.g. "/assets/kitten.png"),
     *                   or {@code null} to use the built-in placeholder
     */
    public PlayerScreen(String avatarPath) {
        this.avatarPath = avatarPath;
    }
    /** Convenience constructor that uses the default kitten placeholder. */
    public PlayerScreen() {
        this.avatarPath = "global/download.png";
    }

    // ──── User Setter and Getter ─────────────────────────────────────────────
    public void setUser(Account user) {this.user = user;}
    public Account getUser() {return user;}

    // ── UI construction ───────────────────────────────────────────────────
    /**
     * 
     */
    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        mainPanel.add(buildTopBar(),    BorderLayout.NORTH);
        mainPanel.add(buildAvatarPanel(), BorderLayout.CENTER);
        mainPanel.add(buildButtonBar(), BorderLayout.SOUTH);
        playerFrame.setContentPane(mainPanel);
    }

    /** 
     * Top bar: title on the left, logout arrow on the right. 
     * 
     */
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(6, 10, 0, 10));

        JLabel title = new JLabel("Player Screen");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bar.add(title, BorderLayout.WEST);

        bar.add(buildLogoutButton(), BorderLayout.EAST);
        return bar;
    }

    /** 
     * Round X button that exits / logs out and returns to the main menu. 
     * 
     */
    private JButton buildLogoutButton() {
        JButton btn = new JButton() {
            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int r = Math.min(w, h) - 1;
                int ox = (w - r) / 2, oy = (h - r) / 2;

                // Circle fill
                g2.setColor(hovered ? new Color(0xE0, 0x30, 0x30) : new Color(0xCC, 0x22, 0x22));
                g2.fillOval(ox, oy, r, r);

                // X mark
                int pad = r / 4;
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.drawLine(ox + pad, oy + pad, ox + r - pad, oy + r - pad);
                g2.drawLine(ox + r - pad, oy + pad, ox + pad, oy + r - pad);

                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(28, 28));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Logout");
        btn.setActionCommand("Logout");
        btn.addActionListener(this);                        // call actionPerformed to logout
        return btn;
    }

    /**
     * Centre panel showing the player's avatar.
     * If a real image was loaded it is drawn scaled; otherwise a dashed
     * placeholder box is shown to mark where the character art will go.
     */
    private JPanel buildAvatarPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (avatarImage != null) {
                    // Draw the real avatar image, centred and scaled
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    int targetH = (int) (getHeight() * 0.55);
                    int targetW = avatarImage.getWidth(null) * targetH
                                  / Math.max(avatarImage.getHeight(null), 1);
                    int x = (getWidth()  - targetW) / 2;
                    int y = (getHeight() - targetH) / 2;
                    g2.drawImage(avatarImage, x, y, targetW, targetH, null);
                } else {
                    // Placeholder: dashed rounded rectangle with label
                    int boxW = 140, boxH = 180;
                    int bx = (getWidth()  - boxW) / 2;
                    int by = (getHeight() - boxH) / 2;

                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillRoundRect(bx, by, boxW, boxH, 16, 16);

                    float[] dash = {8f, 6f};
                    g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND,
                                                 BasicStroke.JOIN_ROUND, 1f, dash, 0f));
                    g2.setColor(new Color(255, 255, 255, 160));
                    g2.drawRoundRect(bx, by, boxW, boxH, 16, 16);

                    g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                    g2.setColor(new Color(255, 255, 255, 200));
                    String line1 = "Character";
                    String line2 = "goes here";
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(line1, bx + (boxW - fm.stringWidth(line1)) / 2,
                                         by + boxH / 2 - 4);
                    g2.drawString(line2, bx + (boxW - fm.stringWidth(line2)) / 2,
                                         by + boxH / 2 + fm.getHeight() - 2);
                }

                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /** 
     * Bottom bar with the four navigation buttons. 
     * 
     */
    private JPanel buildButtonBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 14));
        bar.setOpaque(false);

        bar.add(makeNavButton("New Game")); // actionPerformed will make new game
        bar.add(makeNavButton("Continue Game")); // actionPerformed will continue game
        bar.add(makeNavButton("Game Store")); // actionPerformed will move to game store
        bar.add(makeNavButton("Stats")); // actionPerformed will show stats
        bar.add(makeNavButton("Tutorial")); // actionPerformed will show tutorial

        return bar;
    }

    /** 
     * Factory for a styled navigation button. 
     * 
     */
    private JButton makeNavButton(String label) {
        JButton btn = new JButton(label) {
            private boolean hovered = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? BUTTON_HOVER : BUTTON_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(BUTTON_TEXT);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };

        btn.setPreferredSize(new Dimension(148, 44));
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setActionCommand(label);
        btn.addActionListener(this); // use actionPerformed to handle navigation
        return btn;
    }

    // ── Avatar loading ────────────────────────────────────────────────────
    /** 
     * Loads the avatar image from a file path; leaves {@code avatarImage} null if not found. 
     * 
     */
    private void loadAvatar(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                avatarImage = new ImageIcon(file.getAbsolutePath()).getImage();
                return;
            }
            // fallback: try classpath resource
            URL url = getClass().getResource(filePath);
            if (url != null) {
                avatarImage = new ImageIcon(url).getImage();
            }
        }
        // avatarImage stays null → buildAvatarPanel draws the placeholder box
    }


    // ── General Navigation ────────────────────────────────────────────────
    /**
     * General navigation for both keyboard and button presses.
     * @param command The name of the command used
     * @author Fardin Abbassi
     */
    private void navigateTo(String command) {
        // move from this class to the next screen based on the button clicked
            System.out.println("→ " + command);
            this.moveToNextScreen(command);
    }
    /**
     * Helper function to make adding shortcuts easier to read.
     * 
     * @param target The component to add key shortcuts to
     */
    private void addKeyShortcuts(JComponent target) {
        // New Game: N
        addKeyShortcut(target, KeyEvent.VK_N, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("New Game"); }
        });
        
        // Continue Game: C
        addKeyShortcut(target, KeyEvent.VK_C, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Continue Game"); }
        });

        // Game Store: G
        addKeyShortcut(target, KeyEvent.VK_G, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Game Store"); }
        });

        // Stats: S
        addKeyShortcut(target, KeyEvent.VK_S, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Stats"); }
        });

        // Logout: ESC
        addKeyShortcut(target, KeyEvent.VK_ESCAPE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AudioManager.playButtonClick(); navigateTo("Logout"); }
        });
    }
    
    
// SCREEN INTERFACE METHODS -----------------------------------------------
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
     * Handle button press logic by rerouting to general navigation handling.
     * @author Omar Soliman
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        AudioManager.playButtonClick();
        if (e.getActionCommand() != null) navigateTo(e.getActionCommand());
    }
    //TODO: public showScreen
    /**
     * Builds the UI of the screen.
     * 
     * <p>
     * Uses helper functions to build panels, then sets keyboard maps for each button in the larger panel.
     */
    @Override
    public void showScreen() {
        // Build Player Frame
        if (playerFrame == null) {
            playerFrame = new JFrame("Staying Alive - Player Menu");
            WindowUtils.addSaveOnClose(playerFrame); // data is saved when window is closed
        }
        setUser(AccountManagement.getCurrentAccount());
        playerFrame.setSize(NavigationControl.screenW, NavigationControl.screenH);
        playerFrame.getContentPane().removeAll();
        
        loadAvatar(avatarPath); // Uses either default image or whatever file the user specified when constructing the file.
        buildUI();
        playerFrame.setLocationRelativeTo(null);
        playerFrame.setVisible(true);
        WindowUtils.setAppIcon(playerFrame);

        // Add key press navigation
        addKeyShortcuts((JPanel) playerFrame.getContentPane());

    }
    // TODO: add Gameplay function when integrated
    /**
     * Move from player screen to the specified screen.
     * 
     * @param screenToMoveTo The specified screen to switch to
     */
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        // TODO: when integrating, move from this class to player menu when the back button is clicked; ensure that player data is kept when continuing a game
        if (screenToMoveTo.equals("New Game")) {
            System.out.println("to new game");
            String username = AccountManagement.getCurrentAccount().getUsername();
            NavigationControl.getAccountManager().getParental().resetPlayerStats(username);
            NavigationControl.setCurrentScreen(7); // Make sure to reset data when starting a new game
        }
        if (screenToMoveTo.equals("Continue Game")) {
            System.out.println("to continue game");
            NavigationControl.setCurrentScreen(7); // Make sure to load saved data when continuing the game
        }
        if (screenToMoveTo.equals("Game Store")) {
            System.out.println("to game store");
            NavigationControl.setCurrentScreen(5);
        }
        if (screenToMoveTo.equals("Stats")) {
            System.out.println("to stats");
            NavigationControl.setCurrentScreen(4); // TODO: implement stats screen
        }
        if (screenToMoveTo.equals("Tutorial")) {
            System.out.println("to tutorial");
            NavigationControl.setCurrentScreen(2);
        }
        if (screenToMoveTo.equals("Logout")) {
            WindowUtils.activateSaveSequence(); // save data before logging out.
            NavigationControl.setCurrentScreen(0);;
        }
    }
    // TODO: public getFrame
    /**
     * Return the player screen's frame.
     * @return The player screen's frame
     */
    @Override
    public JFrame getFrame() { return this.playerFrame; }

}