package ca.uwo.cs2212.group54.stayingalive.accounts;

import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.uwo.cs2212.group54.stayingalive.game.Gameplay;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

public class Account {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String pass;
    @JsonProperty("stats")
    private LevelStatistic[] levelStatistics;
    @JsonProperty("progress")
    public PlayerProgress playerProgress;
    @JsonProperty("coins")
    public int coins;
    @JsonIgnore
    private Sprite sprite;
    //public Powerup[] powerups;
    //public Cosmetic[] cosmetics;

    /*@JsonCreator
    public Account(@JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("coins") int coins) {
        this.username = username;
        this.pass = password;
        this.coins = coins;
    }*/

    @JsonCreator
    public Account(@JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("coins") int coins,
        @JsonProperty("stats") LevelStatistic[] levelStatistics,
        @JsonProperty("progress") PlayerProgress playerProgress) {
        this.username = username;
        this.pass = password;
        this.coins = coins;
        this.levelStatistics = levelStatistics;
        this.playerProgress = playerProgress;
        loadAvatar("global/download.png");
    }

    public Account (String username, String pass) {
        this.username = username;
        this.pass = pass;
        this.levelStatistics = new LevelStatistic[PlayerProgress.MAX_LEVELS];
        for (int i =0; i < levelStatistics.length;i++) {
            levelStatistics[i] = new LevelStatistic(new LevelData(1, i + 1));
        }
        this.playerProgress = new PlayerProgress();
        coins = 0;
        loadAvatar("global/download.png");
    }

    /** Loads the avatar image from a file path; leaves {@code avatarImage} null if not found. */
    private void loadAvatar(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                Image image = new ImageIcon(filePath).getImage();
                Sprite sprite = new Sprite(image,Gameplay.getPlayerX(),Gameplay.getPlayerY());
                return;
            }
        }
        // avatarImage stays null → buildAvatarPanel draws the placeholder box
    }

    public String getUsername() {
        return username;
    }

    protected String getPassword() {
        return pass;
    }

    protected void setPassword(String newPass) {
        this.pass = newPass;
        Parental.saveAccountData();
    }

    @JsonProperty("stats")
    public LevelStatistic[] getAllLevelStats() {
        return levelStatistics;
    }

    @JsonIgnore
    public LevelStatistic getLevelStat(int levelNum) {
        return levelStatistics[levelNum-1];
    }

    @JsonIgnore
    public void setStats(LevelStatistic levelStats) {
        int levelNum = levelStats.getLevelData().getLevelNum();
        levelStatistics[levelNum-1] = levelStats;
    }

    protected void clearStats() {
        for (int i = 0; i < levelStatistics.length; i++) {
            levelStatistics[i] = new LevelStatistic();
        }
    }

    public PlayerProgress getProgress() {
        return playerProgress;
    }

    @Override
    public boolean equals(Object o) {
        if ( (o != null) && (o instanceof Account) ) {
            Account otherAccount = (Account) o;
            if (this.username.equals(otherAccount.getUsername())) {
                if (this.pass.equals(otherAccount.getPassword())) {return true;}
            }
        }
        return false;
    }
    
    public Sprite getSprite() { return this.sprite; }
}
