package ca.uwo.cs2212.group54.stayingalive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.uwo.cs2212.group54.stayingalive.accounts.*;

public class AccountTest {
    private Parental parental;
    private Account account;

    // Integration Tests 1-3, 7
    @BeforeEach
    void setUp() {
        parental = new Parental();

        boolean created = parental.createAccount("testUser", "password123");
        System.out.println("before");
        assertTrue(created);
        System.out.println("after");
        account = parental.getAccount("testUser");
    }

    @Test
    void testSetStats() {
        // Step 0: Construct the level statistic and update it
        LevelData levelData = new LevelData(1, 2);
        LevelStatistic newStat = new LevelStatistic(levelData);
        newStat.updateStats(50, 70, 3, 200, 1, 0.90, Level_status.UNLOCKED);

        // Step 1: Set account stats using the level statistic
        account.setStats(newStat);

        // Step 2: Call to get the statistic for level 2
        LevelStatistic retrievedStats = account.getLevelStat(2);

        // Step 3: Assert that all fields match
        assertNotNull(retrievedStats);
        assertEquals(50, retrievedStats.getAvgWPM());
        assertEquals(70, retrievedStats.getPeakWPM());
        assertEquals(3, retrievedStats.getMistakes());
        assertEquals(200, retrievedStats.getHighScore());
        assertEquals(1, retrievedStats.getAttempts());
        assertEquals(0.90, retrievedStats.getAccuracy(), 0.001);
        assertEquals(Level_status.UNLOCKED, retrievedStats.getStatus());
    }

    @Test
    void testClearStats() {
        // Step 0: Level 1 stat is updated with WPM = 40
        LevelData levelData = new LevelData(1, 1);
        LevelStatistic originalStat = new LevelStatistic(levelData);
        originalStat.updateStats(40, 60, 2, 150, 1, 0.85, Level_status.UNLOCKED);
        account.setStats(originalStat);
        LevelStatistic beforeClear = account.getLevelStat(1);
        assertEquals(40, beforeClear.getAvgWPM(), "Precondition: stats should be set");

        // Step 1: Clear player stats using Parental method
        parental.resetPlayerStats(account.getUsername());

        // Step 2: Retrieve the stats for level 1 after clearing
        LevelStatistic afterClear = account.getLevelStat(1);

        // Step 3: Assert all fields are reset to default (zero / LOCKED)
        assertEquals(0, afterClear.getAvgWPM(), "avgWPM should be reset to 0");
        assertEquals(0, afterClear.getPeakWPM(), "peakWPM should be reset to 0");
        assertEquals(0, afterClear.getMistakes(), "mistakes should be reset to 0");
        assertEquals(0, afterClear.getHighScore(), "highscore should be reset to 0");
        assertEquals(0, afterClear.getAttempts(), "attempts should be reset to 0");
        assertEquals(0.0, afterClear.getAccuracy(), 0.001, "accuracy should be reset to 0.0");
        assertEquals(Level_status.LOCKED, afterClear.getStatus(), "status should be LOCKED after clear");
    }

    @Test
    void testLevelStatisticsArrayLength() {
        // Step 1: Call the function
        LevelStatistic[] stats = account.getAllLevelStats();

        // Step 2: Assert that the length matches max levels
        assertEquals(stats.length, PlayerProgress.MAX_LEVELS);
    }

    @Test
    void testPlayerProgressNotNull() {
        // Step 1: Assert that progress is non-null
        assertNotNull(account.getProgress());

        // Step 2: Assert that progress is at level 1 for a new account
        assertEquals(1, account.getProgress().getCurrentLevel());
    }

    
    // Validation Test 10
    @Test
    void testUsernameAndPasswordAreSet() {
        // Step 0: Get explicit username and password
        String username = "testUser";
        String password = "password123";

        // Step 1: Call on getUsername()
        String storedUser = account.getUsername();

        // Step 2: Call on getPassword()
        String storedPass = account.getPassword();

        // Step 3: Assert that both username and password match the constructor
        assertEquals(username, storedUser);
        assertEquals(password, storedPass);
    }

    @AfterEach
    void deleteAccount() {
        if (parental.getAccount("testUser") != null) parental.deleteAccount("testUser");
    }
}