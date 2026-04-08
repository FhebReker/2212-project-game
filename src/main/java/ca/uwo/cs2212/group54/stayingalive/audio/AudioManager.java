package ca.uwo.cs2212.group54.stayingalive.audio;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;
import java.io.File;

/**
 * Audio manager class
 * 
 * @author Malik Alghneimin
 */
public class AudioManager {
    public static Sound playerHit;
    public static Sound enemyHit;
    public static Sound playerError;
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

            buttonClick = TinySound.loadSound(new File("global/sfx/press_button.wav"));
            playerHit = TinySound.loadSound(new File("global/sfx/player_damaged.wav"));
            enemyHit = TinySound.loadSound(new File("global/sfx/enemy_hit.wav"));
            playerError = TinySound.loadSound(new File("global/sfx/player_error.wav"));
            playerHeal = TinySound.loadSound(new File("global/sfx/player_heal.wav"));
            pouwerup = TinySound.loadSound(new File("global/sfx/powerup_used.wav"));
            gameover = TinySound.loadSound(new File("global/sfx/game_lost.wav"));
            levelComplete = TinySound.loadSound(new File("global/sfx/completion.wav"));
            

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
     * Play player error sound
     */
    public static void playPlayerError() {
        if (playerError != null) playerError.play();
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
