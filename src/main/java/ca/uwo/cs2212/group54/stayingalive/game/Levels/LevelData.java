package ca.uwo.cs2212.group54.stayingalive.game.Levels;

import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * LevelData class
 * 
 * @author Malik Alghneimin
 */

public class LevelData {
    private final int section;
    private final int number;
    private final Enemy enemies[];
    private final Sprite background;

    public LevelData(int section, int number, Enemy enemies[], Sprite background) {
        this.section = section;
        this.number = number;
        this.enemies = enemies;
        this.background = background;
    }

    public int getSection() {
        return this.section;
    }

    public int getNumber() {
        return this.number;
    }

    public Enemy[] getEnemies() {
        return this.enemies;
    }

    public Sprite getBackground() {
        return this.background;
    }
}
