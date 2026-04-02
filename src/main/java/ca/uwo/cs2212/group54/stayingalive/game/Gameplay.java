package ca.uwo.cs2212.group54.stayingalive.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.accounts.LevelStatistic;
import ca.uwo.cs2212.group54.stayingalive.accounts.Level_status;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.Difficulty;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.LevelData;

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
    private boolean levelCleared;
    private Account player;
    private LevelData levelData;

    private static final int PLAYER_X = 400;
    private static final int PLAYER_Y = 300;
    private static final int SAFE_RADIUS = 50;
    
    public Gameplay(Account player, LevelData levelData, Difficulty difficulty) {
        this.player = player;
        this.levelData = levelData;
        this.lives = 3; // Either 3 as a default or based on the account's lives. (Need to confirm)
        // if (player.powerups.get("extraLife")) {
        //     this.lives++;
        // }
        this.mistakes = 0;
        this.corrects = 0;
        this.time = 0;
        this.score = 0;
        this.difficulty = difficulty;
        this.levelCleared = false;

        // This is for how much enemies are present in the game.
        // Maybe with the difficulty, the speed of the enemies should increase too.
        switch (difficulty) {
            case EASY:  {
                this.maxWeight = 10; 
                this.spawnDelay = 3.0f;
            }
            case MEDIUM:  {
                this.maxWeight = 15; 
                this.spawnDelay = 2.5f;
            }
            case HARD:  {
                this.maxWeight = 20;
                this.spawnDelay = 1.0f;
            }
        }
        
        this.pendingEnemies = new LinkedList<>();
        if (levelData != null && levelData.getEnemies() != null) {
            this.pendingEnemies.addAll(Arrays.asList(levelData.getEnemies()));
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

    // Getters

    public int getLives() {
        return this.lives;
    }

    public int getScore() {
        return this.score;
    }

    public int getWPM() {
        return this.calculateWPM();
    }

    public float getTime() {
        return this.time;
    }

    public List<Enemy> getActiveEnemies() {
        return this.activeEnemies;
    }

    public void update(float deltaTime) {
        if (isGameOver() || isLevelCleared()) {
            return;
        }

        this.time += deltaTime; // track elapsed game time

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
                this.updateScore(-100, this.difficulty);
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

        // Check if level is completed
        if (pendingEnemies.isEmpty() && activeEnemies.isEmpty()) {
            this.levelCleared = true;
        }
    }

    public void processInput(String input) {
        if (isGameOver() || isLevelCleared()) {
            return;
        }
        
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
                    updateScore(enemy.getScore() * 5, difficulty);
                    currWeight -= enemy.getWeight();
                    activeEnemies.remove(enemy);
                }
            }
        }

        // No match found
        mistakes++;
        inputLockTimer = 1.0f;
    }
    
    public void updateScore(int amount, Difficulty difficulty) {
        // Add score based on difficulty
        switch (difficulty) {
            case EASY: this.score += amount;
            case MEDIUM: this.score += amount * 1.5;
            case HARD: this.score += amount * 2;
        }
    }

    public void endLevel() {
        if (player != null && levelData != null) {
            LevelStatistic stats = this.player.getLevelStat(levelData.getNumber());
            if (stats == null) {
                LevelData accLevelData = 
                    new LevelData(levelData.getNumber(), null, null);
                stats = new LevelStatistic(accLevelData);
            }
            int totalWords = corrects + mistakes;
            double accuracy = totalWords == 0 ? 0.0 : ((double) corrects / totalWords) * 100.0;
            
            Level_status status = isLevelCleared() ? Level_status.COMPLETED : Level_status.UNLOCKED;
            
            int newHighscore = Math.max(score, stats.getHighScore());
            int newAttempts = stats.getAttempts() + 1;
            stats.updateStats(calculateWPM(), calculateWPM(), mistakes, newHighscore, newAttempts, accuracy, status);
            this.player.setStats(stats);
        }
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }
    
    public boolean isLevelCleared() {
        return this.levelCleared;
    }

    public int calculateWPM() {
        if (time == 0) return 0;
        return (int) (corrects / (time / 60.0f));
    }

    public void changeLives(int change) {
        this.lives += change;
    }
}