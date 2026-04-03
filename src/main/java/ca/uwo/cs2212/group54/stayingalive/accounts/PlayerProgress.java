package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerProgress {
    @JsonIgnore
    public static final int MAX_LEVELS = 3;
    @JsonProperty("current_level")
    private int currentLevel;
    @JsonProperty("completed_levels")
    private boolean[] completedLevels;
    @JsonProperty("playtime")
    private double playtime;

    /*@JsonCreator
    public PlayerProgress(@JsonProperty int currentLevel, 
    @JsonProperty boolean[] completedLevels, @JsonProperty LocalDate lastSaveDate) {
        this.currentLevel = currentLevel;
        this.completedLevels = completedLevels;
        this.lastSaveDate = lastSaveDate;
    }*/

    public void setPlaytime(double playtime) {
        this.playtime = playtime;
    }

    /**
     * Updates the playtime for the parent player object of this PlayerProgress object.
     * 
     * @param startTime Log in time in milliseconds
     * @param endTime   Log out time in milliseconds
     */
    public void updatePlaytime(long startTime, long endTime) {
        long duration = endTime - startTime; // IN MILLISECONDS
        double durationMins = duration/60000.0;
        durationMins = Math.round(durationMins * 100.0) / 100.0; // rounding
        playtime = playtime + durationMins;

    }


    public PlayerProgress() {
        currentLevel = 1;
        completedLevels = new boolean[MAX_LEVELS];
    }

    
    public void completeLevel(int level) {
        completedLevels[level-1] = true;
        //Parental.saveAccountData();
    }

    @JsonProperty("current_level")
    public int getCurrentLevel() {
        return currentLevel;
    }

    @JsonProperty("current_level")
    public void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public void clearProgress() {
        int currentLevel = 1;
        completedLevels = new boolean[MAX_LEVELS];
    }

    public double getPlaytime() {
        return playtime;
    }

}