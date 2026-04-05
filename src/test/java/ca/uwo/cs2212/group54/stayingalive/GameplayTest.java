package ca.uwo.cs2212.group54.stayingalive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uwo.cs2212.group54.stayingalive.accounts.Account;
import ca.uwo.cs2212.group54.stayingalive.game.Gameplay;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.*;
import ca.uwo.cs2212.group54.stayingalive.game.Levels.*;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

public class GameplayTest {
    // Integration Tests 4-6
    @Test
    void testEmptyLevelClearsOnFirstUpdate() {
        // Step 0: Set up gameplay
        Account account = new Account("testPlayer", "pass");
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Call update
        game.update(0.1f);

        // Step 2: Call level clear check
        boolean cleared = game.isLevelCleared();

        // Step 2: Assert level cleared
        assertTrue(cleared);
    }

    @Test
    void testInitialNotLevelClearedWithEnemies() {
        // Step 0: Add a single enemy into a level
        String[] words = {"cat"};
        Sprite sprite = new Sprite(null, 0, 0);
        Enemy enemy = new Enemy(words, Enemy_Attribute.NORMAL, sprite);
        Enemy[] enemies = {enemy};
        LevelData levelData = new LevelData(1, enemies, null);

        Account account = new Account("testUser", "password123");
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Call level clear check
        boolean cleared = game.isLevelCleared();
        
        // Step 2: Assert level is not cleared
        assertFalse(cleared);
    }

    @Test
    void testScoreAccessibleOnLevelCompletion() {
        // Step 0: Create an empty level
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Account account = new Account("testUser", "password123");
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Call update to trigger level-cleared
        game.update(0.1f);

        // Step 2: Assert level cleared
        assertTrue(game.isLevelCleared());

        // Step 3: Assert score is non-negative
        assertTrue(game.getScore() >= 0);
    }


    // Validation Tests 1-7
    @Test
    void testInitialLives() {
        // Step 0: Set up game session
        Account account = new Account("testUser", "password123");
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Call getLives()
        int lives = game.getLives();

        // Step 2: Assert that the game starts with exactly 3 lives
        assertEquals(3, lives);
    }

    @Test
    void testInitialScore() {
        // Step 0: Create game session with at least 1 enemy
        String[] words = {"cat"};
        Sprite sprite = new Sprite(null, 0, 0);
        Enemy enemy = new Enemy(words, Enemy_Attribute.NORMAL, sprite);
        Enemy[] enemies = {enemy};
        Account account = new Account("testUser", "password123");
        LevelData levelData = new LevelData(1, enemies, null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Assert that the score starts at 0
        assertEquals(0, game.getScore());
    }

    @Test
    void testUpdateScoreUsingDifficulty() {
        // Step 0: Launch easy game session
        Account account = new Account("testUser", "password123");
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Update score as if an enemy were defeated
        game.updateScore(100, Difficulty.EASY);

        // Step 2: Assert that the score multiplier is 1x
        assertEquals(100, game.getScore());

        // Step 3: Launch medium game session
        game = new Gameplay(account, levelData, Difficulty.MEDIUM);

        // Step 4: Update score as if an enemy were defeated
        game.updateScore(100, Difficulty.MEDIUM);

        // Step 5: Assert that the score multiplier is 1.5x
        assertEquals(150, game.getScore(), "MEDIUM multiplier should be 1.5x (100 → 150)");

        // Step 6: Launch hard game session
        game = new Gameplay(account, levelData, Difficulty.HARD);

        // Step 7: Update score as if an enemy were defeated
        game.updateScore(100, Difficulty.HARD);

        // Step 8: Assert that the score multiplier is 2x
        assertEquals(200, game.getScore(), "HARD multiplier should be 2x (100 → 200)");
    }

    @Test
    void testGameOverWhenLivesReachZero() {
        // Step 0: Create a game session
        Account account = new Account("testUser", "password123");
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Assert that game state initially is not over
        boolean over = game.isGameOver();
        assertFalse(over);

        // Step 2: Reduce lives from 3 to 0
        game.changeLives(-3);
        over = game.isGameOver();

        // Step 3: Assert game over state
        assertTrue(over);
    }

    @Test
    void testWrongInputAppliesStun() {
        // Step 0: Make a game session with 1 enemy with word "cat" and let it come to the player
        String[] words = {"cat"};
        Sprite sprite = new Sprite(null, 0, 0);
        Enemy enemy = new Enemy(words, Enemy_Attribute.NORMAL, sprite);
        Enemy[] enemies = {enemy};
        LevelData levelData = new LevelData(1, enemies, null);
        Account account = new Account("testUser", "password123");
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);
        game.update(3.1f);
        assertEquals(1, game.getActiveEnemies().size());

        // Step 1: Send a wrong input not in the highlighted enemy
        game.processInput('x');
        int scoreBefore = game.getScore();
        
        // Step 2: Try to send correct first letter while stunned
        game.processInput('c');

        // Step 3: Assert that no change occured and that enemy should still have no characters unlocked
        assertEquals(scoreBefore, game.getScore());
        
        // Step 4: Assert that the enemy's first character remains the same
        Enemy activeEnemy = game.getActiveEnemies().get(0);
        assertEquals(0, activeEnemy.getFirstUnlockedChar());
    }

    // TODO: Change documentation to reflect automatic testing.
    @Test
    void testWpmZeroAtStart() {
        // Step 0: Initialize game session
        Account account = new Account("testPlayer", "pass");
        LevelData levelData = new LevelData(1, new Enemy[0], null);
        Gameplay game = new Gameplay(account, levelData, Difficulty.EASY);

        // Step 1: Assert that WPM at start is 0
        assertEquals(0, game.getWPM());
    }
}