package ca.uwo.cs2212.group54.stayingalive;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uwo.cs2212.group54.stayingalive.accounts.*;

public class LevelStatisticTest {
    // Validation Test 11
    // TODO: Change documentation to reflect actual function used.
    @Test
    void testStatisticFields() {
        // Step 0: Make a LevelStatistic for level 1
        LevelData levelData = new LevelData(1, 1);
        LevelStatistic stats = new LevelStatistic(levelData);

        // Step 1: Call updateStats with test values
        stats.updateStats(55, 80, 2, 300, 3, 0.95, Level_status.COMPLETED);

        // Step 2: Assert each field individually
        assertEquals(55, stats.getAvgWPM(), "avgWPM should be 55");
        assertEquals(80, stats.getPeakWPM(), "peakWPM should be 80");
        assertEquals(2, stats.getMistakes(), "mistakes should be 2");
        assertEquals(300, stats.getHighScore(), "highscore should be 300");
        assertEquals(3, stats.getAttempts(), "attempts should be 3");
        assertEquals(0.95, stats.getAccuracy(), 0.001, "accuracy should be 0.95");
        assertEquals(Level_status.COMPLETED, stats.getStatus(), "status should be COMPLETED");
    }
}