package ca.uwo.cs2212.group54.stayingalive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy_Attribute;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

public class EnemyTest {
    // Validation Tests 8-9
    @Test
    void testEnemyAttributes() {
        // Step 0: Initialize enemies for each attribute type
        String[] normalWords = {"cat"};
        Sprite sprite = new Sprite(null, 0, 0);
        Enemy normal = new Enemy(normalWords, Enemy_Attribute.NORMAL, sprite);

        String[] heartWords = {"cat", "dog"};
        Enemy heart = new Enemy(heartWords, Enemy_Attribute.HAS_HEART, sprite);
        
        String[] bigWords = new String[10];
        for (int i = 0; i < 10; i++) bigWords[i] = "word";
        Enemy big = new Enemy(bigWords, Enemy_Attribute.BIG, sprite);

        String[] bossWords = new String[15];
        for (int i = 0; i < 15; i++) bossWords[i] = "word";
        Enemy boss = new Enemy(bossWords, Enemy_Attribute.BOSS, sprite);

        // Step 1: Assert that normal enemies have their attributes correct
        assertEquals(1, normal.getDamage());
        assertEquals(1, normal.getWeight());
        assertEquals(10, normal.getScore());

        // Step 2: Assert that heart enemies have their attributes correct
        assertEquals(1, heart.getDamage());
        assertEquals(1, heart.getWeight());
        assertEquals(25, heart.getScore());

        // Step 3: Assert that big enemies have their attributes correct
        assertEquals(2, big.getDamage());
        assertEquals(5, big.getWeight());
        assertEquals(50, big.getScore());

        // Step 4: Assert that boss enemies have their attributes correct
        assertEquals(2, boss.getDamage());
        assertEquals(10, boss.getWeight());
        assertEquals(100, boss.getScore());
    }

    @Test
    void testDefeatedAfterAllWords() {
        // Step 0: Enemy with 2+ word list
        String[] words = {"cat", "dog"};
        Sprite sprite = new Sprite(null, 0, 0);
        Enemy enemy = new Enemy(words, Enemy_Attribute.NORMAL, sprite);

        // Step 1: Update words twice
        enemy.updateWords(); 
        enemy.updateWords(); 

        // Step 2: Call isDefeated()
        boolean defeated = enemy.isDefeated();

        // Step 3: Assert that enemy is defeated
        assertTrue(defeated);
    }
}