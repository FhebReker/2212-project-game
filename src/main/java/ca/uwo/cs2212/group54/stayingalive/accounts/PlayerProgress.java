package ca.uwo.cs2212.group54.stayingalive.accounts;

import java.time.LocalDate;

public class PlayerProgress {
    int currentLevel;
    boolean[] completedLevels;
    LocalDate lastSaveDate;

    public PlayerProgress() {
        currentLevel = 1;
        completedLevels = new boolean[8];
        lastSaveDate = LocalDate.now();
    }

    public void completeLevel(int level) {
        completedLevels[level] = true;
    }

    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public void updateSaveTime() {
        lastSaveDate = LocalDate.now();
    }

}