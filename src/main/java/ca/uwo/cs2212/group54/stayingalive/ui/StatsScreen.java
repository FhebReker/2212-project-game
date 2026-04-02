package ca.uwo.cs2212.group54.stayingalive.ui;

// package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * StatsScreen – displays per-user statistics with the player's avatar.
 *
 * Layout:
 *   - Purple background matching the app theme
 *   - Left half: labelled stat rows (label + rounded grey value box)
 *   - Right half: player name heading + avatar image
 *   - Back button (curved arrow) in the top-right corner
 */
//public class StatsScreen extends JPanel implements Screen {
public class StatsScreen implements Screen {

    // Colours 
    private static final Color BG_COLOR  = new Color(106, 69, 156);
    private static final Color FIELD_BG  = new Color(215, 215, 215);
    // Data
    private final String   playerName;
    private Image avatarImage;
    private JFrame statsFrame;

    // Default / placeholder values — replace with real data from the stats system
    private int    highScore    = 0;
    private int    avgWpm       = 0;
    private int    peakWpm      = 0;
    private double accuracy     = 0.0;
    private int    errorCount   = 0;
    private String timePlayed   = "0 minutes";
    private int    currentLevel = 1;
    private int    wordsTyped   = 0;

    // Constructor
    public StatsScreen(String playerName) {
        this.playerName = playerName;
        loadAvatar("global/download.png");
//        buildUI();
    }

    // Setters for live data 
    public void setHighScore(int v)    { highScore    = v; }
    public void setAvgWpm(int v)       { avgWpm       = v; }
    public void setPeakWpm(int v)      { peakWpm      = v; }
    public void setAccuracy(double v)  { accuracy     = v; }
    public void setErrorCount(int v)   { errorCount   = v; }
    public void setTimePlayed(String v){ timePlayed   = v; }
    public void setCurrentLevel(int v) { currentLevel = v; }
    public void setWordsTyped(int v)   { wordsTyped   = v; }

    // UI construction
    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        mainPanel.add(buildTopBar(), BorderLayout.NORTH);
        mainPanel.add(buildBody(),   BorderLayout.CENTER);
        statsFrame.setContentPane(mainPanel);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);
        bar.setBorder(BorderFactory.createEmptyBorder(4, 10, 0, 14));
        bar.add(buildBackButton(), BorderLayout.EAST);
        return bar;
    }

    /** Back button using the back.png image from the global folder. */
    private JButton buildBackButton() {
        ImageIcon icon = null;
        File imgFile = new File("global/back.png");
        if (imgFile.exists()) {
            Image img = new ImageIcon(imgFile.getAbsolutePath()).getImage()
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
        btn.setActionCommand("Back");
        btn.addActionListener(this); // handle back button click in actionPerformed
        return btn;
    }

    /** Splits the screen into left (stats) and right (avatar) halves. */
    private JPanel buildBody() {
        JPanel body = new JPanel(new GridLayout(1, 2));
        body.setOpaque(false);
        body.add(buildStatsPanel());
        body.add(buildAvatarPanel());
        return body;
    }

    // Left panel: stat rows 
    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        String[][] rows = {
            { "High Score:",                   String.valueOf(highScore)       },
            { "Average WPM:",                  avgWpm + " wpm"                 },
            { "Peak WPM:",                     peakWpm + " wpm"                },
            { "Overall Accuracy%:",            accuracy + "%"                  },
            { "Error Count:",                  String.valueOf(errorCount)      },
            { "Total Time Played:",            timePlayed                      },
            { "Current Level:",                "Level " + currentLevel         },
            { "Words Correctly Typed:",  String.valueOf(wordsTyped)      },
        };

        int startY = 8;
        int rowH   = 42;
        int lblW   = 155;
        int boxX   = lblW + 14;
        int boxW   = 160;
        int rowHt  = 28;

        for (int i = 0; i < rows.length; i++) {
            int y = startY + i * rowH;

            // Label
            JLabel lbl = new JLabel(rows[i][0]);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            lbl.setHorizontalAlignment(SwingConstants.RIGHT);
            lbl.setVerticalAlignment(SwingConstants.CENTER);
            lbl.setBounds(4, y, lblW, rowHt);
            panel.add(lbl);

            // Value box (rounded grey rectangle)
            JLabel val = makeValueBox(rows[i][1]);
            val.setBounds(boxX, y, boxW, rowHt);
            panel.add(val);
        }

        return panel;
    }

    /** A label that paints itself as a rounded grey box. */
    private JLabel makeValueBox(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(FIELD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(Color.BLACK);
        lbl.setOpaque(false);
        return lbl;
    }

    // Right panel: avatar
    private JPanel buildAvatarPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (avatarImage != null) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    int targetH = (int) (getHeight() * 0.52);
                    int targetW = avatarImage.getWidth(null) * targetH
                                  / Math.max(avatarImage.getHeight(null), 1);
                    int x = (getWidth()  - targetW) / 2;
                    int y = (getHeight() - targetH) / 2 + 18;
                    g2.drawImage(avatarImage, x, y, targetW, targetH, null);
                    g2.dispose();
                }
            }
        };
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(playerName + "'s Statistics", SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(28, 0, 0, 0));
        panel.add(nameLabel, BorderLayout.NORTH);

        return panel;
    }

    // Avatar loading
    private void loadAvatar(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            avatarImage = new ImageIcon(file.getAbsolutePath()).getImage();
        }
    }

// SCREEN INTERGACE METHODS -----------------------------------------------
    // TODO: Action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() != null) {
            // move from this class to the next screen based on the button clicked
            System.out.println("→ " + e.getActionCommand());
            this.moveToNextScreen(e.getActionCommand());
        }
    }
    //TODO: public showScreen
    @Override
    public void showScreen() {
        if (statsFrame == null) {
            statsFrame = new JFrame("Staying Alive - Player Menu");
            statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        statsFrame.setSize(NavigationControl.screenW, NavigationControl.screenH);
        statsFrame.getContentPane().removeAll();
        
        loadAvatar("global/download.png");
        buildUI();
        //gameStoreFrame.setContentPane(screen);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
    }
    // TODO: public moveToNextScreen
    @Override
    public void moveToNextScreen(String screenToMoveTo) {
        if (screenToMoveTo.equals("Back")) {
            NavigationControl.goBack();
        }
    }
    // TODO: public getFrame
    @Override
    public JFrame getFrame() {return this.statsFrame;}
}
