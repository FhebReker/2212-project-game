package ca.uwo.cs2212.group54.stayingalive.game.Enemies;

import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * Enemy class
 * 
 * @author Malik Alghneimin
 * 
 */
public class Enemy {
    private String[] words;
    private Enemy_Attribute attribute;
    private Sprite sprite;
    private int damage;
    private float speed;
    private int weight;
    private int score;
    private int currentWordIndex;

    private float exactX;
    private float exactY;

    protected boolean timeStopped; // How do we implement the time stop???

    /**
     * Constructor for Enemy class. 
     * 
     * @param words Array of words for the enemy
     * @param attribute Attribute of the enemy
     * @param sprite Sprite of the enemy
     */
    public Enemy(String[] words, Enemy_Attribute attribute, Sprite sprite) {
        this.words = words; // The first word is the one that needs to be typed.
        this.attribute = attribute;
        this.sprite = sprite;

        switch (attribute) {
            case NORMAL: {
                this.damage = 1;
                this.speed = 1.0f;
                this.weight = 1;
                this.score = 10;
                break;
            }
            case HAS_HEART: {
                this.damage = 1;
                this.speed = 1.0f;
                this.weight = 1;
                this.score = 25;
                break;
            }
            case BIG: {
                this.damage = 2;
                this.speed = 0.75f;
                this.weight = 5;
                this.score = 50;
                break;
            }
            case BOSS: {
                this.damage = 2;
                this.speed = 0.5f;
                this.weight = 10;
                this.score = 100;
                break;
            }
        }
        
        this.currentWordIndex = 0;
        this.exactX = sprite != null ? sprite.getX() : 0;
        this.exactY = sprite != null ? sprite.getY() : 0;
    }

    /**
     * Gets the damage of the enemy.
     * 
     * @return The damage of the enemy.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Moves the enemy directly towards the target coordinates.
     * 
     * @param deltaTime The time elapsed since last frame.
     * @param targetX The target X coordinate (player).
     * @param targetY The target Y coordinate (player).
     */
    public void move(float deltaTime, int targetX, int targetY) {
        if (sprite == null) return;
        
        float dx = targetX - this.exactX;
        float dy = targetY - this.exactY;
        float angle = (float) Math.atan2(dy, dx);
        
        this.exactX += Math.cos(angle) * this.speed * deltaTime;
        this.exactY += Math.sin(angle) * this.speed * deltaTime;
        
        this.sprite.setX(Math.round(this.exactX));
        this.sprite.setY(Math.round(this.exactY));
    }

    /**
     * Checks if the enemy has contacted the player within the safe radius.
     * 
     * @param playerX The X coordinate of the player.
     * @param playerY The Y coordinate of the player.
     * @param safeRadius The boundary radius.
     * @return True if the enemy has breached the safe zone, false otherwise
     */
    public boolean contact(int playerX, int playerY, int safeRadius) {
        double distance = Math.hypot(playerX - this.exactX, playerY - this.exactY);
        return distance < safeRadius;
    }
    public int getWeight() {
        return this.weight;
    }

    /**
     * Gets the sprite associated with the enemy.
     * 
     * @return The sprite object.
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**
     * Sets the position of the enemy.
     * 
     * @param x The exact X coordinate.
     * @param y The exact Y coordinate.
     */
    public void setPosition(int x, int y) {
        this.exactX = x;
        this.exactY = y;
        if (this.sprite != null) {
            this.sprite.setX(x);
            this.sprite.setY(y);
        }
    }

    /**
     * Gets the score given by the enemy when defeated.
     * 
     * @return The score of the enemy.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the current word to be typed.
     * 
     * @return The current word string, or null if defeated.
     */
    public String getCurrentWord() {
        if (isDefeated()) {
            return null;
        }
        return this.words[this.currentWordIndex];
    }

    /**
     * Checks if the enemy has no more words left.
     * 
     * @return true if default, false otherwise.
     */
    public boolean isDefeated() {
        return this.currentWordIndex >= this.words.length;
    }
    
    /**
     * Updates the words of the enemy.
     */
    public void updateWords() {
        if (!isDefeated()) {
            this.currentWordIndex++;
        }
    }
}
