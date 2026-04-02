package game;

import accounts.ac;
import game.Levels.LevelData;
import game.Enemies.Enemy;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.util.Random;

enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}

/**
 * Gameplay class
 * 
 * @author Malik Alghneimin
 */
public class Gameplay {
    private int lives;
    private int mistakes;
    private int corrects;
    private int time;
    private int score;
    private int maxWeight;
    private int currWeight;
    private Difficulty difficulty;
    private float spawnDelay;
    
    private Queue<Enemy> pendingEnemies;
    private List<Enemy> activeEnemies;
    private float timeSinceLastSpawn;
    private float inputLockTimer;
    private static final Point[] SPAWN_POINTS = new Point[16];
    private Random random;

    
    public Gameplay(ac player, LevelData levelData, Difficulty difficulty) {
        this.lives = ac.getLives(); // Either 3 as a default or based on the account's lives. (Need to confirm)
        this.mistakes = 0;
        this.corrects = 0;
        this.time = 0;
        this.difficulty = difficulty;

        // This is for how much enemies are present in the game.
        // Maybe with the difficulty, the speed of the enemies should increase too.
        switch (difficulty) {
            case EASY: {
                this.maxWeight = 10; 
                this.spawnDelay = 3.0f;
                break;
            }
            case MEDIUM: {
                this.maxWeight = 15; 
                this.spawnDelay = 2.5f;
                break;
            }
            case HARD: {
                this.maxWeight = 20;
                this.spawnDelay = 1.0f;
                break;
            }
        }
        
        this.pendingEnemies = new LinkedList<>();
        if (levelData != null && levelData.getEnemies() != null) {
            for (Enemy e : levelData.getEnemies()) {
                this.pendingEnemies.add(e);
            }
        }
        this.activeEnemies = new ArrayList<>();
        this.random = new Random();
        this.timeSinceLastSpawn = 0;
        this.inputLockTimer = 0;
        this.currWeight = 0;
        
        // Setup 16 target corners for spawn
        int[][] corners = {
            {50, 50}, {100, 50}, {50, 100}, {100, 100}, // Top Left
            {700, 50}, {750, 50}, {700, 100}, {750, 100}, // Top Right
            {50, 500}, {100, 500}, {50, 550}, {100, 550}, // Bottom Left
            {700, 500}, {750, 500}, {700, 550}, {750, 550} // Bottom Right
        };
        for (int i = 0; i < 16; i++) {
            SPAWN_POINTS[i] = new Point(corners[i][0], corners[i][1]);
        }
    }

    private static final int PLAYER_X = 400;
    private static final int PLAYER_Y = 300;
    private static final int SAFE_RADIUS = 50;

    public void update(float deltaTime) {
        if (inputLockTimer > 0) {
            inputLockTimer -= deltaTime;
        }

        // Handle enemy spawning
        timeSinceLastSpawn += deltaTime;
        if (timeSinceLastSpawn >= spawnDelay) {
            Enemy nextEnemy = pendingEnemies.peek();
            if (nextEnemy != null) {
                if (currWeight + nextEnemy.getWeight() <= maxWeight) {
                    Enemy spawned = pendingEnemies.poll();
                    
                    // Assign random spawn point representation
                    Point spawnPt = SPAWN_POINTS[random.nextInt(16)];
                    spawned.setPosition(spawnPt.x, spawnPt.y);
                    
                    activeEnemies.add(spawned);
                    currWeight += spawned.getWeight();
                    timeSinceLastSpawn = 0;
                }
            }
        }

        // Update active enemy positions and collision
        java.util.Iterator<Enemy> iterator = activeEnemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.move(deltaTime, PLAYER_X, PLAYER_Y);
            
            if (enemy.contact(PLAYER_X, PLAYER_Y, SAFE_RADIUS)) {
                this.changeLives(-enemy.getDamage());
                this.currWeight -= enemy.getWeight();
                // Remove the Sprite from UI parent 
                if (enemy.getSprite() != null && enemy.getSprite().getImage() != null) {
                    java.awt.Container parent = enemy.getSprite().getImage().getParent();
                    if (parent != null) {
                        parent.remove(enemy.getSprite().getImage());
                        parent.repaint();
                    }
                }
                iterator.remove();
            }
        }

        // TODO: Implement other update logic
        // Update score
        // Check if level is completed
        // Check if game is over
    }

    public void processInput(String input) {
        if (inputLockTimer > 0) {
            return; // Player is stunned
        }

        for (Enemy enemy : activeEnemies) {
            String currentWord = enemy.getCurrentWord();
            if (currentWord != null && currentWord.equals(input)) {
                // Match found
                enemy.updateWords();
                updateScore(enemy.getScore(), difficulty);
                
                if (enemy.isDefeated()) {
                    currWeight -= enemy.getWeight();
                    activeEnemies.remove(enemy);
                }
                return; // hit only the first matching enemy!
            }
        }

        // No match found
        mistakes++;
        inputLockTimer = 1.0f;
    }
    
    public void updateScore(int amount, Difficulty difficulty) {
        // Add score based on difficulty
        switch (difficulty) {
            case EASY: this.score += amount; break;
            case MEDIUM: this.score += amount * 1.5; break;
            case HARD: this.score += amount * 2; break;
        }
    }

    public void endLevel() {
        // TODO: Implement end level function
        // Send results to ac's levelStats
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }

    public void pauseGame() {
        // TODO: Implement pause game function
    }

    public void resumeGame() {
        // TODO: Implement resume game function
    }

    public void changeLives(int change) {
        this.lives += change;
    }
}