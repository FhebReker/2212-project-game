package ca.uwo.cs2212.group54.stayingalive.audio;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * Audio manager class
 * 
 * @author Malik Alghneimin
 */
public class AudioManager {
    public static Sound playerHit;
    public static Sound enemyHit;
    public static Sound buttonClick;
    public static Sound playerHeal;
    public static Sound pouwerup;
    public static Sound gameover;
    public static Sound levelComplete;
    
    private static boolean initialized = false;

    /**
     * Initialize the audio manager
     */
    public static void init() {
        if (initialized) return;
        try {
            TinySound.init();
            
            // Files would be placed in global/sfx

            buttonClick = TinySound.loadSound("global/sfx/press_button.wav");
            playerHit = TinySound.loadSound("global/sfx/player_damaged.wav");
            enemyHit = TinySound.loadSound("global/sfx/enemy_hit.wav");
            playerHeal = TinySound.loadSound("global/sfx/player_heal.wav");
            pouwerup = TinySound.loadSound("global/sfx/pouwerup_used.wav");
            gameover = TinySound.loadSound("global/sfx/game_lost.wav");
            levelComplete = TinySound.loadSound("global/sfx/completion.wav");
            

            initialized = true;
        } catch (Exception e) {
            System.err.println("Warning: Audio initialization failed. Continuing silently. " + e.getMessage());
        }
    }

    /**
     * Shutdown the audio manager
     */
    public static void shutdown() {
        if (initialized) {
            TinySound.shutdown();
        }
    }

    /**
     * Play button click sound
     */
    public static void playButtonClick() {
        if (buttonClick != null) buttonClick.play();
    }

    /**
     * Play player hit sound
     */
    public static void playPlayerHit() {
        if (playerHit != null) playerHit.play();
    }

    /**
     * Play enemy hit sound
     */
    public static void playEnemyHit() {
        if (enemyHit != null) enemyHit.play();
    }

    /**
     * Play player heal sound
     */
    public static void playPlayerHeal() {
        if (playerHeal != null) playerHeal.play();
    }

    /**
     * Play powerup sound
     */
    public static void playPowerup() {
        if (pouwerup != null) pouwerup.play();
    }

    /**
     * Play game over sound
     */
    public static void playGameOver() {
        if (gameover != null) gameover.play();
    }

    /**
     * Play level complete sound
     */
    public static void playLevelComplete() {
        if (levelComplete != null) levelComplete.play();
    }
}
