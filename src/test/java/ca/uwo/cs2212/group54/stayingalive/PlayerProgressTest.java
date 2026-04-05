package ca.uwo.cs2212.group54.stayingalive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.uwo.cs2212.group54.stayingalive.accounts.PlayerProgress;

public class PlayerProgressTest {
    // Validation Test 12
    @Test
    void testSettingCurrentLevel() {
        // Step 0: Make PlayerProgress instance
        PlayerProgress progress = new PlayerProgress();

        // Step 1: Set and assert that current level is at 2
        progress.setCurrentLevel(2);
        assertEquals(2, progress.getCurrentLevel());

        // Step 2: Set and assert that current level is at max level
        progress.setCurrentLevel(PlayerProgress.MAX_LEVELS);
        assertEquals(PlayerProgress.MAX_LEVELS, progress.getCurrentLevel());
    }
}
