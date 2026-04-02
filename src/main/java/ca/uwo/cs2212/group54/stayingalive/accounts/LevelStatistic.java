package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelStatistic {
    @JsonProperty("level_data")
    private LevelData levelData;
    @JsonProperty("avg_wpm")
    protected int avgWPM;
    @JsonProperty("peak_wpm")
    protected int peakWPM;
    @JsonProperty("mistakes")
    protected int mistakes;
    @JsonProperty("highscore")
    protected int highscore;
    @JsonProperty("attempts")
    protected int attempts;
    @JsonProperty("accuracy")
    protected double accuracy;
    @JsonProperty("status")
    private Level_status status;

    /*@JsonCreator
    public LevelStatistic(@JsonProperty int wordsPerMinute,
    @JsonProperty int mistakes, @JsonProperty int highscore,
    @JsonProperty int attempts, @JsonProperty String status) {
        this.wordsPerMinute = wordsPerMinute;
        this.mistakes = mistakes;
        this.highscore = highscore;
        this.attempts = attempts;
        this.status = Level_status.valueOf(status);
    }*/

    public LevelStatistic(LevelData levelData) {
        this.levelData = levelData;
        mistakes = avgWPM = peakWPM = highscore = attempts = 0;
        accuracy = 0.0;
        // only for first level
        if (levelData.getLevelNum() == 1) status = Level_status.UNLOCKED;
        else status = Level_status.LOCKED;
    }

    // default constructor will always make level data equal to 1
    public LevelStatistic() {
        this.levelData = new LevelData(1,1);
        mistakes = avgWPM = peakWPM = highscore = attempts = 0;
        accuracy = 0.0;
        status = Level_status.LOCKED;
    }

    public void updateStats(int avgWPM, int peakWPM, int mistakes, int highscore, int attempts, double accuracy, Level_status status) {
        this.avgWPM = avgWPM;
        this.peakWPM = peakWPM;
        this.mistakes = mistakes;
        this.highscore = highscore;
        this.attempts = attempts;
        this.accuracy = accuracy;
        this.status = status;
    }

    @JsonProperty("level_data")
    public LevelData getLevelData() {
        return this.levelData;
    }

    public void clearStatistics() {
        mistakes = avgWPM = peakWPM = highscore = attempts = 0;
        accuracy = 0.0;
        status = Level_status.LOCKED;
    }
    
    protected void completeLevel() {status = Level_status.COMPLETED;}

}