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
import ca.uwo.cs2212.group54.stayingalive.accounts.Parental;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.Difficulty;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.LevelData;
import ca.uwo.cs2212.group54.stayingalive.ui.NavigationControl;

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
    private Random random;
    private static final Point[] SPAWN_POINTS = new Point[16];
    private static final int MIN_DISTANCE = 100;
    private static final int MAX_SPAWN_ATTEMPTS = 50; // attempts to spread apart enemy spawn locations
    private static final int SPAWN_BORDER_RADIUS = 99; // amnt of pixels that form a spawnable border
    private boolean levelCleared;
    private Account player;
    private LevelData levelData;
    private Enemy inFocus;

    private static final int PLAYER_X = NavigationControl.screenW;
    private static final int PLAYER_Y = NavigationControl.screenH;
    private static final int SAFE_RADIUS = 10;
    
    /**
     * Constructor for Gameplay
     * 
     * @param player The player account
     * @param levelData The level data
     * @param difficulty The difficulty of the game
     */
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
                //this.spawnDelay = 3.0f; old spawn delay
                this.spawnDelay = 100.0f;
            }
            case MEDIUM:  {
                this.maxWeight = 15; 
                //this.spawnDelay = 2.5f; old
                this.spawnDelay = 50.0f;
            }
            case HARD:  {
                this.maxWeight = 20;
                //this.spawnDelay = 1.0f; old
                this.spawnDelay = 20.0f;
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
        
        int screenWidthBounds = NavigationControl.screenW * 2;
        int screenHeightBounds = NavigationControl.screenH * 2;

        // Setup 16 target corners for spawn
        int[][] corners = {
            {50, 50}, {400, 50}, {50, 400}, {400, 400}, // Top Left
            {screenWidthBounds - 400, 50}, {screenWidthBounds - 50, 50}, {screenWidthBounds - 400, 400}, {screenWidthBounds - 50, 400}, // Top Right
            {50, screenHeightBounds - 400}, {100, screenHeightBounds - 400}, {200, screenHeightBounds - 200}, {400, screenHeightBounds - 200}, // Bottom Left
            {screenWidthBounds - 400, screenHeightBounds - 400}, {screenWidthBounds - 50, screenHeightBounds - 400}, {screenWidthBounds - 400, screenHeightBounds - 200}, {screenWidthBounds - 200, screenHeightBounds - 200} // Bottom Right
        };
        for (int i = 0; i < 16; i++) {
            SPAWN_POINTS[i] = new Point(corners[i][0], corners[i][1]);
        }
    }

    // Getters
    /**
     * Gets the number of lives the player has.
     * 
     * @return The number of lives the player has.
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Gets the score of the player.
     * 
     * @return The score of the player.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the words per minute of the player.
     * 
     * @return The words per minute of the player.
     */
    public int getWPM() {
        return this.calculateWPM();
    }

    /**
     * Gets the time elapsed since the start of the game.
     * 
     * @return The time elapsed since the start of the game.
     */
    public float getTime() {
        return this.time;
    }

    /**
     * Gets the list of active enemies.
     * 
     * @return The list of active enemies.
     */
    public List<Enemy> getActiveEnemies() {
        return this.activeEnemies;
    }

    /**
     * Updates the game state.
     * 
     * @param deltaTime The time elapsed since last frame.
     */
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
                    /*int rand = random.nextInt(16);
                    Point spawnPt = SPAWN_POINTS[rand];*/
                    Point spawnPt = getValidSpawn();

                    spawned.setPosition(spawnPt.x, spawnPt.y);
                    
                    activeEnemies.add(spawned);
                    currWeight += spawned.getWeight();
                    timeSinceLastSpawn = 0;
                    if (inFocus == null) setInFocus(); // sets an enemy into focus
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
                /* 
                if (enemy.getSprite() != null && enemy.getSprite().getImage() != null) {
                    java.awt.Container parent = enemy.getSprite().getImage().getParent();
                    if (parent != null) {
                        parent.remove(enemy.getSprite().getImage());
                        parent.repaint();
                    }
                }*/
                iterator.remove();
            }
        }

        // Check if level is completed
        if (pendingEnemies.isEmpty() && activeEnemies.isEmpty()) {
            this.levelCleared = true;
        }
    }

    /**
     * Processes the input from the player.
     * 
     * @param input The input from the player which will be a letter.
     */
    public void processInput(char input) {
        if (isGameOver() || isLevelCleared()) {
            return;
        }
        
        if (inputLockTimer > 0) {
            return; // Player is stunned
        }

        if (inFocus.wordContainsChar(input)) {
            inFocus.unlockNextCharacter();
            inFocus.updateWords();
            updateScore(inFocus.getScore(), difficulty);

            if (inFocus.isDefeated()) {
                updateScore(inFocus.getScore() * 5, difficulty);
                currWeight -= inFocus.getWeight();
                activeEnemies.remove(inFocus);
                setInFocus(); // sets a new enemy into focus
            }
        }

        /*for (Enemy enemy : activeEnemies) {
            String currentWord = enemy.getCurrentWord();
            if (enemy.wordContainsChar(input)) {
                enemy.unlockNextCharacter();
                enemy.updateWords();
                updateScore(enemy.getScore(), difficulty);

                if (enemy.isDefeated()) {
                    updateScore(enemy.getScore() * 5, difficulty);
                    currWeight -= enemy.getWeight();
                    activeEnemies.remove(enemy);
                }
            }
        }*/

        // No match found
        mistakes++;
        inputLockTimer = 1.0f;
    }
    
    /**
     * Updates the score of the player.
     * 
     * @param amount The amount to add to the score.
     * @param difficulty The difficulty of the game.
     */
    public void updateScore(int amount, Difficulty difficulty) {
        switch (difficulty) {
            case EASY: this.score += amount;
            case MEDIUM: this.score += amount * 1.5;
            case HARD: this.score += amount * 2;
        }
    }

    /**
     * Ends the current level and updates the player's statistics.
     * Updates the player's level status and score.
     */
    public void endLevel() {
        if (player != null && levelData != null) {
            LevelStatistic stats = this.player.getLevelStat(levelData.getNumber());
            if (stats == null) {
                stats = new LevelStatistic(levelData);
            }
            int totalWords = corrects + mistakes;
            double accuracy = totalWords == 0 ? 0.0 : ((double) corrects / totalWords) * 100.0;
            
            if (isLevelCleared()) {
                int currentLevel = this.player.getProgress().getCurrentLevel();
                this.player.getProgress().completeLevel(currentLevel);
                if (currentLevel < 3) {
                    this.player.getProgress().setCurrentLevel(currentLevel + 1);
                }
            }
            
            Level_status status = isLevelCleared() ? Level_status.COMPLETED : Level_status.UNLOCKED;
            
            int newHighscore = Math.max(score, stats.getHighscore());
            int newAttempts = stats.getAttempts() + 1;
            stats.updateStats(calculateWPM(), calculateWPM(), mistakes, newHighscore, newAttempts, accuracy, status);
            this.player.setStats(stats);
            Parental.saveAccountData();
        }
    }

    /**
     * Checks if the game is over.
     * 
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return this.lives <= 0;
    }
    
    /**
     * Checks if the level is cleared.
     * 
     * @return True if the level is cleared, false otherwise.
     */
    public boolean isLevelCleared() {
        return this.levelCleared;
    }

    /**
     * Calculates the words per minute of the player.
     * 
     * @return The words per minute of the player.
     */
    public int calculateWPM() {
        if (time == 0) {
            return 0;
        }
        return (int) (corrects / (time / 60.0f));
    }

    /**
     * Changes the number of lives the player has.
     * 
     * @param change The amount to change the number of lives by.
     */
    public void changeLives(int change) {
        this.lives += change;
    }

    /**
     * gets a random X value to spawn the enemy
     * @return
     */
    public int getRandomSpawnX() {
        int result;

        if (Math.random() < 0.5) {
            result = (int)(Math.random() * SPAWN_BORDER_RADIUS) + 1;
        } else {
            result = (int)(Math.random() * SPAWN_BORDER_RADIUS) + (NavigationControl.screenW * 2 - SPAWN_BORDER_RADIUS);
        }

        return result;
    }

    public int getRandomSpawnY() {
        int result;

        if (Math.random() < 0.5) {
            result = (int)(Math.random() * SPAWN_BORDER_RADIUS) + 1;
        } else {
            result = (int)(Math.random() * SPAWN_BORDER_RADIUS) + (NavigationControl.screenH * 2 - SPAWN_BORDER_RADIUS);
        }

        return result;
    }


    private Point getValidSpawn() {
        Point p;
        boolean valid;
        int attempts = 0;

        do {
            attempts++;
            int x = getRandomSpawnX();
            int y = getRandomSpawnY();
            p = new Point(x, y);

            valid = true;

            for (Enemy enemy : activeEnemies) {
                double dx = enemy.getPositionX() - x;
                double dy = enemy.getPositionY() - y;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < MIN_DISTANCE) {
                    valid = false;
                    break;
                }
            }
        } while (!valid && attempts < MAX_SPAWN_ATTEMPTS);

        return p;
    }

    /**
     * Sets into focus the enemy that's closest to the player.
     * This method is used to focus on an enemy so that its word can be typed
     * before typing the characters to words that belong to other enemies.
     */
    public void setInFocus() {
        Enemy closest = null;
        float closestDistance = Float.MAX_VALUE;

        // Find the closest enemy
        for (Enemy enemy : activeEnemies) {
            float dist = enemy.getDistanceFrom(new Point(PLAYER_X, PLAYER_Y)); // or however you do it
            if (dist < closestDistance) {
                closestDistance = dist;
                closest = enemy;
            }
        }

        inFocus = closest;
    }

    /**
     * Getter to return the enemy that's currently being focused on
     * @return The enemy that the player is focused on.
     */
    public Enemy getInFocus() { return inFocus; }

    public static int getPlayerX() { return PLAYER_X; }
    public static int getPlayerY() { return PLAYER_Y; }
}