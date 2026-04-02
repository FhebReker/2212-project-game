package ca.uwo.cs2212.group54.stayingalive.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelStatistic {
    @JsonIgnore
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
    @JsonProperty("status")
    protected Level_status status;

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
        mistakes = wordsPerMinute = highscore = attempts = 0;
        status = Level_status.UNLOCKED;
    }

    public LevelStatistic() {
        mistakes = wordsPerMinute = highscore = attempts = 0;
        status = Level_status.UNLOCKED;
    }

    public void updateStats(int wordsPerMinute, int mistakes, int highscore, int attempts, Level_status status) {
        this.wordsPerMinute = wordsPerMinute;
        this.mistakes = mistakes;
        this.highscore = highscore;
        this.attempts = attempts;
        this.status = status;
    }

    public void clearStatistics() {
        mistakes = wordsPerMinute = highscore = attempts = 0;
        status = Level_status.LOCKED;
    }

}