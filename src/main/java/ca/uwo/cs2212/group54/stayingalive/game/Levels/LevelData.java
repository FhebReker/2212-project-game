package ca.uwo.cs2212.group54.stayingalive.game.Levels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * LevelData class
 * 
 * @author Malik Alghneimin
 */

public class LevelData {
    @JsonProperty("number")
    private final int number;
    @JsonIgnore
    private final Enemy enemies[];
    @JsonIgnore
    private final Sprite background;


    /**
     * Constructor for LevelData
     * 
     * @param number The level number
     * @param enemies The enemies in the level
     * @param background The background of the level
     */
    public LevelData(int number, Enemy enemies[], Sprite background) {
        this.number = number;
        this.enemies = enemies;
        this.background = background;
    }

    public LevelData(@JsonProperty("number") int number) {
        this.number = number;
        this.enemies = null;
        this.background = null;
    }

    /**
     * Get the level number
     * 
     * @return The level number
     */
    @JsonProperty("number")
    public int getNumber() {
        return this.number;
    }

    /**
     * Get the enemies in the level
     * 
     * @return The enemies in the level
     */
    public Enemy[] getEnemies() {
        return this.enemies;
    }

    public Sprite getBackground() {
        return this.background;
    }
}
