package accounts;

public class ac {
    private char[] pass;
    private String username;
    private LevelStatistic levelStats;
    public PlayerProgress playerProgress;
    public int coins;
    public Sprite playerSprite;
    public Powerup[] powerups;
    public Cosmetic[] cosmetics;

    public ac (String username, char[] pass) {
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
