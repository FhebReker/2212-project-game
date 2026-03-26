

public enum Level_status {LOCKED, UNLOCKED, COMPLETED}

public class LevelStatistic {

    private levelData level;
    int wordsPerMinute;
    int mistakes;
    int highscore;
    int attempts;
    Level_status status;

    public LevelStatistic(LevelData levelData) {
        this.levelData = levelData;
    }

    public void updateStats(int wordsPerMinute, int mistakes, int highscore, int attempts, Level_status status) {
        this.wordsPerMinute = wordsPerMinute;
        this.mistakes = mistakes;
        this.highscore = highscore;
        this.attempts = attempts;
        this.status = status;
    }

}