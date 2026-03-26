package ca.uwo.cs2212.group54.stayingalive.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

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
 * Navigation callbacks are supplied via a {@link Navigator} interface so this
 * class stays decoupled from whatever navigation controller the project uses.
 */
public class PlayerScreen extends JPanel {

    // ── Colour palette ────────────────────────────────────────────────────
    private static final Color BG_COLOR      = new Color(0x6A, 0x5A, 0xCD); // medium-purple
    private static final Color BUTTON_COLOR  = new Color(0x00, 0xBF, 0xFF); // deep-sky-blue
    private static final Color BUTTON_HOVER  = new Color(0x00, 0x9A, 0xCD);
    private static final Color BUTTON_TEXT   = Color.WHITE;

    // ── Navigation callback interface ─────────────────────────────────────

    /** Implemented by the navigation controller to handle screen transitions. */
    public interface Navigator {
        void goToNewGame();
        void goToContinueGame();
        void goToGameStore();
        void goToStats();
        void goToMainMenu();   // logout / back
    }

    // ── Fields ────────────────────────────────────────────────────────────
    private final Navigator navigator;
    private Image avatarImage;

    // ── Constructor ───────────────────────────────────────────────────────

    /**
     * @param navigator  callback object that handles screen transitions
     * @param avatarPath resource path to the avatar image (e.g. "/assets/kitten.png"),
     *                   or {@code null} to use the built-in placeholder
     */
    public PlayerScreen(Navigator navigator, String avatarPath) {
        this.navigator = navigator;
        loadAvatar(avatarPath);
        buildUI();
    }

    /** Convenience constructor that uses the default kitten placeholder. */
    public PlayerScreen(Navigator navigator) {
        this(navigator, "global/download.png");
    }

    // ── UI construction ───────────────────────────────────────────────────

    private void buildUI() {
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        add(buildTopBar(),    BorderLayout.NORTH);
        add(buildAvatarPanel(), BorderLayout.CENTER);
        add(buildButtonBar(), BorderLayout.SOUTH);
    }

    /** Top bar: title on the left, logout arrow on the right. */
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

    /** Round X button that exits / logs out and returns to the main menu. */
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
        btn.addActionListener(e -> navigator.goToMainMenu());
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

    /** Bottom bar with the four navigation buttons. */
    private JPanel buildButtonBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 14));
        bar.setOpaque(false);

        bar.add(makeNavButton("New Game",      e -> navigator.goToNewGame()));
        bar.add(makeNavButton("Continue Game", e -> navigator.goToContinueGame()));
        bar.add(makeNavButton("Game Store",    e -> navigator.goToGameStore()));
        bar.add(makeNavButton("Stats",         e -> navigator.goToStats()));

        return bar;
    }

    /** Factory for a styled navigation button. */
    private JButton makeNavButton(String label, ActionListener action) {
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
        btn.addActionListener(action);
        return btn;
    }

    // ── Avatar loading ────────────────────────────────────────────────────

    /** Loads the avatar image from a file path; leaves {@code avatarImage} null if not found. */
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

    // ── Standalone test entry point ───────────────────────────────────────

    /** Launches the PlayerScreen in its own JFrame for visual testing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Player Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(760, 480);
            frame.setLocationRelativeTo(null);

            Navigator stub = new Navigator() {
                public void goToNewGame()      { System.out.println("→ New Game");      }
                public void goToContinueGame() { System.out.println("→ Continue Game"); }
                public void goToGameStore()    { System.out.println("→ Game Store");    }
                public void goToStats()        { System.out.println("→ Stats");         }
                public void goToMainMenu()     { System.out.println("→ Main Menu (logout)"); }
            };

            frame.setContentPane(new PlayerScreen(stub));
            frame.setVisible(true);
        });
    }
}
