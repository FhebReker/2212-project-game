package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String pass;
    @JsonProperty("stats")
    private LevelStatistic levelStats;
    @JsonProperty("progress")
    public PlayerProgress playerProgress;
    @JsonProperty("coins")
    public int coins;
    //public Sprite playerSprite;
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
        @JsonProperty("stats") LevelStatistic levelStats,
        @JsonProperty("progress") PlayerProgress playerProgress) {
        this.username = username;
        this.pass = password;
        this.coins = coins;
        this.levelStats = levelStats;
        this.playerProgress = playerProgress;
    }

    public Account (String username, String pass) {
        this.username = username;
        this.pass = pass;
        this.levelStats = new LevelStatistic();
        this.playerProgress = new PlayerProgress();
        coins = 0;
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

    public LevelStatistic getStats() {
        return levelStats;
    }

    public void setStats(LevelStatistic levelStats) {
        this.levelStats = levelStats;
    }

    public void setStats(int wordsPerMinute, int mistakes, int highscore, int attempts, Level_status status) {
        this.levelStats.updateStats(wordsPerMinute, mistakes, highscore, attempts, status);
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
}
