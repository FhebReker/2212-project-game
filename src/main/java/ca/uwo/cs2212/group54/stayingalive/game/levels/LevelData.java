package game.levels;

import game.Enemy;
import sprites.Sprite;

/**
 * LevelData class
 * 
 * @author Malik Alghneimin
 */

public class LevelData {
    private int section;
    private int number;
    private Enemy enemies[];
    private Sprite background;

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
