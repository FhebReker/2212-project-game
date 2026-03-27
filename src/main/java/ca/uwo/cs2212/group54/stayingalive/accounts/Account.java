package ca.uwo.cs2212.group54.stayingalive.accounts;

public class Account {
    private String username;
    private char[] pass;
    private LevelStatistic levelStats;
    public PlayerProgress playerProgress;
    public int coins;
    public Sprite playerSprite;
    public Powerup[] powerups;
    public Cosmetic[] cosmetics;

    public Account (String username, char[] pass) {
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    protected char[] getPassword() {
        return pass;
    }

    protected void setPassword(char[] newPass) {
        this.pass = newPass;
    }

    public LevelStatistic getStats() {
        return levelStats;
    }
}
