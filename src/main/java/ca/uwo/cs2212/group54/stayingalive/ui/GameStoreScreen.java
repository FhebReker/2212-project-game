//package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * GameStoreScreen – lets the player spend accumulated score on power-ups and cosmetics.
 *
 * Layout:
 *   - Purple background matching the app theme
 *   - Top bar: "Game Store" title left | score pill + back button right
 *   - Left half: player avatar centred, owned power-up badges underneath
 *   - Right half: scrollable card list of purchasable items with Buy buttons
 */
public class GameStoreScreen extends JPanel implements Screen{

    // ── Colours ───────────────────────────────────────────────────────────
    private static final Color BG_COLOR    = new Color(0x6A, 0x5A, 0xCD);
    private static final Color BTN_COLOR   = new Color(0x00, 0xBF, 0xFF);
    private static final Color BTN_HOVER   = new Color(0x00, 0x9A, 0xCD);
    private static final Color GOLD        = new Color(255, 215, 60);

    // ── Store item model ──────────────────────────────────────────────────
    public static class StoreItem {
        public final String name;
        public final String description;
        public final int    cost;
        public final String category;   // "powerup" or "cosmetic"
        public int quantity = 0;

        public StoreItem(String name, String description, int cost, String category) {
            this.name        = name;
            this.description = description;
            this.cost        = cost;
            this.category    = category;
        }
    }

    // ── Navigation ────────────────────────────────────────────────────────
    public interface Navigator { void goBack(); }

    // ── Fields ────────────────────────────────────────────────────────────
    private final Navigator      navigator;
    private       int            playerScore;
    private       Image          avatarImage;
    private final List<StoreItem> items = new ArrayList<>();

    // Score pill label so we can update it after a purchase
    private JLabel scorePill;

    // Owned-powerup badges so we can refresh counts
    private final List<JLabel> badgeLabels = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────
    public GameStoreScreen(Navigator navigator, int initialScore) {
        this.navigator   = navigator;
        this.playerScore = initialScore;
        loadAvatar("global/download.png");
        initItems();
        buildUI();
    }

    public GameStoreScreen(Navigator navigator) {
        this(navigator, 0);
    }

    // ── Public API ────────────────────────────────────────────────────────
    public void setPlayerScore(int score) {
        this.playerScore = score;
        refreshScorePill();
    }

    public int getPlayerScore() { return playerScore; }

    // ── Default catalogue ─────────────────────────────────────────────────
    private void initItems() {
        items.add(new StoreItem("Time Freeze",
                "Freeze enemies in place for 10 seconds",   500,  "powerup"));
        items.add(new StoreItem("Word Clear",
                "Wipe out lower level enemies in one go",   700,  "powerup"));
        items.add(new StoreItem("Focus Shield",
                "Keeps the next 3 typing errors from confusing you", 300, "powerup"));
        items.add(new StoreItem("New Heart",
                "Adds an extra life",                       2000, "powerup"));
        items.add(new StoreItem("Golden Avatar",
                "Give your avatar a golden glow",           1500, "cosmetic"));
        items.add(new StoreItem("Dark Theme",
                "Switch the game to a dark colour scheme",   800, "cosmetic"));
    }

    // ── UI construction ───────────────────────────────────────────────────
    private void buildUI() {
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());
        add(buildTopBar(),  BorderLayout.NORTH);
        add(buildBody(),    BorderLayout.CENTER);
    }

    // Top bar ──────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(6, 12, 4, 10));

        JLabel title = new JLabel("Game Store");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        bar.add(title, BorderLayout.WEST);

        bar.add(buildTopRight(), BorderLayout.EAST);
        return bar;
    }

    private JPanel buildTopRight() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);

        // Score pill
        scorePill = new JLabel("Score: " + playerScore, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        scorePill.setForeground(GOLD);
        scorePill.setFont(new Font("SansSerif", Font.BOLD, 13));
        scorePill.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        scorePill.setOpaque(false);
        panel.add(scorePill);

        panel.add(buildBackButton());
        return panel;
    }

    private JButton buildBackButton() {
        ImageIcon icon = null;
        File f = new File("global/back.png");
        if (f.exists()) {
            Image img = new ImageIcon(f.getAbsolutePath()).getImage()
                            .getScaledInstance(34, 34, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }
        JButton btn = new JButton(icon);
        btn.setPreferredSize(new Dimension(34, 34));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Back");
        btn.addActionListener(e -> { if (navigator != null) navigator.goBack(); });
        return btn;
    }

    // Body ─────────────────────────────────────────────────────────────────
    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2, 12, 0));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));
        body.add(buildLeftPanel());
        body.add(buildShopScrollPane());
        return body;
    }

    // Left panel: avatar + power-up badges ─────────────────────────────────
    private JPanel buildLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Avatar
        JPanel avatarPanel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (avatarImage == null) return;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                int targetH = (int)(getHeight() * 0.80);
                int targetW = avatarImage.getWidth(null) * targetH
                              / Math.max(avatarImage.getHeight(null), 1);
                int x = (getWidth()  - targetW) / 2;
                int y = (getHeight() - targetH) / 2;
                g2.drawImage(avatarImage, x, y, targetW, targetH, null);
                g2.dispose();
            }
        };
        avatarPanel.setOpaque(false);
        panel.add(avatarPanel, BorderLayout.CENTER);

        // Power-up badge row
        panel.add(buildBadgeBar(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildBadgeBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 6));
        bar.setOpaque(false);
        badgeLabels.clear();

        String[] powerupNames = {"Time Freeze", "Word Clear", "Focus Shield"};
        for (String name : powerupNames) {
            StoreItem item = findItem(name);
            int qty = (item != null) ? item.quantity : 0;

            JPanel badge = new JPanel(new BorderLayout(0, 2));
            badge.setOpaque(false);

            // Icon circle
            final String n = name;
            JPanel iconCircle = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.fillOval(2, 2, getWidth()-4, getHeight()-4);
                    g2.setColor(new Color(255, 255, 255, 100));
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawOval(2, 2, getWidth()-4, getHeight()-4);
                    drawItemIcon(g2, n, getWidth()/2, getHeight()/2);
                    g2.dispose();
                }
            };
            iconCircle.setOpaque(false);
            iconCircle.setPreferredSize(new Dimension(38, 38));

            JLabel qtyLabel = new JLabel(qty + "x", SwingConstants.CENTER);
            qtyLabel.setForeground(Color.WHITE);
            qtyLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
            badgeLabels.add(qtyLabel);

            badge.add(iconCircle, BorderLayout.CENTER);
            badge.add(qtyLabel,   BorderLayout.SOUTH);
            bar.add(badge);
        }
        return bar;
    }

    /** Draws a small symbolic icon for a named item. */
    private void drawItemIcon(Graphics2D g2, String name, int cx, int cy) {
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        switch (name) {
            case "Time Freeze":
                g2.drawOval(cx-7, cy-7, 14, 14);
                g2.drawLine(cx, cy-5, cx, cy);
                g2.drawLine(cx, cy, cx+4, cy+2);
                break;
            case "Word Clear":
                // Broom-like sweep
                g2.drawLine(cx-5, cy-6, cx+6, cy+5);
                g2.drawLine(cx+3, cy-6, cx+8, cy-1);
                g2.drawLine(cx-8, cy+1, cx-3, cy+6);
                break;
            case "Focus Shield":
                int[] sx = {cx, cx+7, cx+7, cx, cx-7, cx-7};
                int[] sy = {cy-8, cy-4, cy+2, cy+7, cy+2, cy-4};
                g2.drawPolygon(sx, sy, 6);
                break;
            default:
                g2.fillOval(cx-4, cy-4, 8, 8);
                break;
        }
    }

    // Right panel: scrollable shop ─────────────────────────────────────────
    private JScrollPane buildShopScrollPane() {
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);
        list.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        for (StoreItem item : items) {
            list.add(buildItemCard(item));
            list.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(list);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 70), 1));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel buildItemCard(StoreItem item) {
        JPanel card = new JPanel(new BorderLayout(10, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(255, 255, 255, 55));
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 10));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));

        // Left: icon circle
        JPanel iconCircle = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillOval(2, 2, getWidth()-4, getHeight()-4);
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(2, 2, getWidth()-4, getHeight()-4);
                drawItemIcon(g2, item.name, getWidth()/2, getHeight()/2);
                g2.dispose();
            }
        };
        iconCircle.setOpaque(false);
        iconCircle.setPreferredSize(new Dimension(48, 48));

        // Centre: text
        JPanel info = new JPanel(new GridLayout(3, 1, 0, 1));
        info.setOpaque(false);

        JLabel nameLabel = new JLabel(item.name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel descLabel = new JLabel("<html>" + item.description + "</html>");
        descLabel.setForeground(new Color(215, 215, 215));
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));

        JLabel costLabel = new JLabel("Cost: " + item.cost + " points");
        costLabel.setForeground(GOLD);
        costLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

        info.add(nameLabel);
        info.add(descLabel);
        info.add(costLabel);

        // Right: Buy button
        JButton buyBtn = new JButton("Buy") {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    @Override public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? BTN_HOVER : BTN_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth()  - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        buyBtn.setPreferredSize(new Dimension(68, 34));
        buyBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        buyBtn.setOpaque(false);
        buyBtn.setContentAreaFilled(false);
        buyBtn.setBorderPainted(false);
        buyBtn.setFocusPainted(false);
        buyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buyBtn.addActionListener(e -> handlePurchase(item));

        JPanel btnWrapper = new JPanel(new GridBagLayout());
        btnWrapper.setOpaque(false);
        btnWrapper.add(buyBtn);

        card.add(iconCircle, BorderLayout.WEST);
        card.add(info,       BorderLayout.CENTER);
        card.add(btnWrapper, BorderLayout.EAST);
        return card;
    }

    // ── Purchase logic ────────────────────────────────────────────────────
    private void handlePurchase(StoreItem item) {
        if (playerScore < item.cost) {
            JOptionPane.showMessageDialog(this,
                    "You need " + item.cost + " points but only have " + playerScore + ".",
                    "Not Enough Points", JOptionPane.WARNING_MESSAGE);
            return;
        }
        playerScore -= item.cost;
        item.quantity++;
        refreshScorePill();
        refreshBadges();
        JOptionPane.showMessageDialog(this,
                item.name + " purchased!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void refreshScorePill() {
        if (scorePill != null) scorePill.setText("Score: " + playerScore);
    }

    private void refreshBadges() {
        String[] names = {"Time Freeze", "Word Clear", "Focus Shield"};
        for (int i = 0; i < badgeLabels.size() && i < names.length; i++) {
            StoreItem item = findItem(names[i]);
            if (item != null) badgeLabels.get(i).setText(item.quantity + "x");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────
    private StoreItem findItem(String name) {
        return items.stream().filter(it -> it.name.equals(name)).findFirst().orElse(null);
    }

    private void loadAvatar(String filePath) {
        File file = new File(filePath);
        if (file.exists()) avatarImage = new ImageIcon(file.getAbsolutePath()).getImage();
    }

    // ── Standalone test ───────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Store Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(760, 450);
            frame.setLocationRelativeTo(null);

            GameStoreScreen screen = new GameStoreScreen(
                    () -> System.out.println("→ Back"), 3000);
            frame.setContentPane(screen);
            frame.setVisible(true);
        });
    }
}
