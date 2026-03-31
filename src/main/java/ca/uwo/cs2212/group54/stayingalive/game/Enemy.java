package game;

import sprites.Sprite;

enum Enemy_Attribute {
    Normal,
    hasHeart,
    Big,
    Boss
}

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

    protected boolean timeStopped; // How do we implement the time stop???

    /**
     * Constructor for Enemy class
     * @param words Array of words for the enemy
     * @param attribute Attribute of the enemy
     * @param sprite Sprite of the enemy
     */
    public Enemy(String[] words, Enemy_Attribute attribute, Sprite sprite) {
        this.words = words;
        this.attribute = attribute;
        this.sprite = sprite;

        switch (attribute) {
            case Normal -> {
                this.damage = 1;
                this.speed = 1.0f;
                this.weight = 1;
            }
            case hasHeart -> {
                this.damage = 1;
                this.speed = 1.0f;
                this.weight = 1;
            }
            case Big -> {
                this.damage = 2;
                this.speed = 0.75f;
                this.weight = 2;
            }
            case Boss -> {
                this.damage = 2;
                this.speed = 0.5f;
                this.weight = 5;
            }
        }
    }

    public int getDamage() {
        return this.damage;
    }

    public void move() {
        // TODO: Implement move function
    }

    public boolean contact(Gameplay player) {
        // Move contact logic to Gameplay?
        return false;
    }
    
    public void updateWords() {
        // Either eliminate current word or 
    }
}
