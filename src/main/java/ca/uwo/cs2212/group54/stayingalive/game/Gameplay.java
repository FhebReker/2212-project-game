package game;

import game.levels.LevelData;
import accounts.ac;

enum Difficulty {
    EASY,
    MEDIUM,
    HARD
}

/**
 * Gameplay class
 * 
 * @author Malik Alghneimin
 */
public class Gameplay {
    private int lives;
    private int mistakes;
    private int corrects;
    private int time;
    private int score;
    private int maxWeight;
    private 

    
    public Gameplay(ac player, LevelData levelData, Difficulty difficulty) {
        this.lives = ac.getLives(); // Either 3 as a default or based on the account's lives. (Need to confirm)
        this.mistakes = 0;
        this.corrects = 0;
        this.time = 0;

        // This is for how much enemies are present in the game.
        switch (difficulty) {
            case EASY: this.maxWeight = 10; break;
            case MEDIUM: this.maxWeight = 20; break;
            case HARD: this.maxWeight = 30; break;
        }


    }

    public void update() {
        // TODO: Implement update function
        // Update enemy positions
        // Check for collisions
        // Update score
        // Update lives
        // Check if level is completed
        // Check if game is over
    }

    public void processInput(String input) {
        // Check if input matches any of the enemies' words.
        // If match, remove word from enemy and increase score.
        // If no match, increase mistakes and stun player for 1 second (no input allowed).

    }
    
    public void updateScore(int amount, Difficulty difficulty) {
        // Add score based on difficulty
        switch (difficulty) {
            case EASY: this.score += amount; break;
            case MEDIUM: this.score += amount * 1.5; break;
            case HARD: this.score += amount * 2; break;
        }
    }

    public void endLevel() {
        // TODO: Implement end level function
        // Send results to ac's levelStats
    }

    public boolean isGameOver() {
        return this.lives <= 0;
    }

    public void pauseGame() {
        // TODO: Implement pause game function
    }

    public void resumeGame() {
        // TODO: Implement resume game function
    }

    public void changeLives(int change) {
        this.lives += change;
    }
}