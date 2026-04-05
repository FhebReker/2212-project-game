package ca.uwo.cs2212.group54.stayingalive.game.Enemies;

import java.awt.Point;

import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * Enemy class
 * 
 * @author Malik Alghneimin
 * 
 */
public class Enemy {
    private String[] words;
    private boolean[] charsPressed; // To see which indexes of characters for the word were pressed
    private Enemy_Attribute attribute;
    private Sprite sprite;
    private int damage;
    private float speed;
    private int weight;
    private int score;
    private int currentWordIndex;
    // To adjust amount of words needed to defeat each type of enemy
    private final static int normalWords = 1;
    private final static int hasHeartWords = 2;
    private final static int bigWords = 3;
    private final static int bossWords = 5;

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
                // initialize amount of words
                this.words = new String[normalWords];
                this.words[0] = words[0];
                break;
            }
            case HAS_HEART: {
                this.damage = 1;
                this.speed = 1.0f;
                this.weight = 1;
                this.score = 25;
                this.words = new String[hasHeartWords];
                for (int i = 0; i < this.words.length; i++) { this.words[i] = words[i]; }
                break;
            }
            case BIG: {
                this.damage = 2;
                this.speed = 0.75f;
                this.weight = 5;
                this.score = 50;
                this.words = new String[bigWords];
                for (int i = 0; i < this.words.length; i++) { this.words[i] = words[i]; }
                break;
            }
            case BOSS: {
                this.damage = 2;
                this.speed = 0.5f;
                this.weight = 10;
                this.score = 100;
                this.words = new String[bossWords];
                for (int i = 0; i < this.words.length; i++) { this.words[i] = words[i]; }
                break;
            }
        }
        
        this.currentWordIndex = 0;
        charsPressed = new boolean[words[currentWordIndex].length()];
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
        /*if (sprite == null) return;
        
        float dx = targetX - this.exactX;
        float dy = targetY - this.exactY;
        float angle = (float) Math.atan2(dy, dx);
        
        this.exactX += Math.cos(angle) * this.speed * deltaTime;
        this.exactY += Math.sin(angle) * this.speed * deltaTime;
        
        this.sprite.setX(Math.round(this.exactX));
        this.sprite.setY(Math.round(this.exactY));*/
        if (sprite == null) return;

        float dx = targetX - this.exactX;
        float dy = targetY - this.exactY;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // If already at target (or very close), snap to it
        if (distance < 0.01f) {
            this.exactX = targetX;
            this.exactY = targetY;
        } else {
            float maxStep = this.speed * deltaTime;

            // Clamp movement to not overshoot
            float step = Math.min(maxStep, distance);

            float nx = dx / distance; // normalized direction
            float ny = dy / distance;

            this.exactX += nx * step;
            this.exactY += ny * step;
        }

        //this.sprite.setX(Math.round(this.exactX));
        //this.sprite.setY(Math.round(this.exactY));
        this.sprite.setX((int)this.exactX);
        this.sprite.setY((int)this.exactY);
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
     * 
     * @return x position of this enemy
     */
    public int getPositionX() {
        return (int)this.exactX;
    }

    /**
     * 
     * @return y position of this enemy
     */
    public int getPositionY() {
        return (int)this.exactY;
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
            // all characters for that word were unlocked
            if (getFirstUnlockedChar() == -1) {
                // last index
                if (currentWordIndex == words.length - 1) {
                    this.currentWordIndex++;
                } else {
                    this.currentWordIndex++;
                    charsPressed = new boolean[words[currentWordIndex].length()];
                }
            }
        }
    }

    /**
     * Helper method to unlock the next character in the current word.
     */
    public void unlockNextCharacter() {
        // if this is the first character being pressed
        for (int i = 0; i < charsPressed.length; i++) {
            if (charsPressed[i] == false) {
                charsPressed[i] = true;
                return;
            }
        }
    }

    /**
     * Helper method to check which index is unlocked.
     * @return the index of the first unlocked character
     * 
     */
    public int getFirstUnlockedChar() {
        for (int i = 0; i < charsPressed.length; i++) {
            if (charsPressed[i] == false) {
                return i;
            }
        }
        return -1; // if all characters are unlocked
    }

    /**
     * Check if the current word contain the character.
     * @param c The character that has been input.
     * @return True if 'c' is in the word and false otherwise.
     */
    public boolean wordContainsChar(char c) {
        if (getFirstUnlockedChar() >= 0) {
            int unlockedIndex = getFirstUnlockedChar();
            if (getCurrentWord().charAt(unlockedIndex) == c) return true;
        }
        return false;
    }

    /**
     * Gets the distance between point and this enemy. Can be used to
     * find closest enemy in an enemy list.
     * @param point The point from which the distance has to be calculated
     * @return The amount of distance.
     */
    public float getDistanceFrom(Point point) {
        float dx = point.x - exactX;
        float dy = point.y - exactY;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
