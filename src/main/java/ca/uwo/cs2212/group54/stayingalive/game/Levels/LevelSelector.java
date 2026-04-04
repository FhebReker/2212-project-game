package ca.uwo.cs2212.group54.stayingalive.game.Levels;


import java.awt.Image;

import javax.swing.ImageIcon;

import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy_Attribute;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * LevelSelector class is responsible for generating LevelData based on the section, level number, and difficulty.
 *
 * @author Malik Alghneimin
 * @author Mohmed Ahmed
 */
public class LevelSelector {
    // Words for each difficulty level
    // Easy: 3 letters
    private static final String[] WORDS_EASY = {
        "cat", "dog", "run", "hop", "sit", "big", "box", "cup",
        "red", "sun", "hat", "map", "log", "bin", "fog", "bed",
        "pig", "hen", "ant", "bat", "cot", "den", "fan", "gap",
        "hen", "ink", "jet", "kit", "lap", "mat", "net", "owl",
        "pan", "rat", "sap", "tap", "van", "wag", "yak", "zap"
    };

    // Medium: 5 letters
    private static final String[] WORDS_MEDIUM = {
        "apple", "chair", "cloud", "dance", "eagle", "flame",
        "grape", "honey", "image", "joker", "kneel", "lemon",
        "mango", "nerve", "ocean", "piano", "queen", "river"
    };

    // Hard: 7 letters
    private static final String[] WORDS_HARD = {
        "balance", "captain", "because", "diamond", "element",
        "furnace", "glacier", "hunting", "integer", "journey",
        "kingdom", "lantern", "machine", "network", "october",
        "passion", "quantum", "railway", "science", "thunder",
        "rhythm", "synergy", "vortex", "zenith", "abyss"
    };

    /**
     * this returns a new LevelData for the given section and level number or null if no such level is defined.
     *
     *
     * @param section    gets the section number (1 or 2)
     * @param number      gets the level number from the section (1–3)
     * @param difficulty Difficulty chosen by the player, used to select words
     * @return           A fully constructed LevelData, or null if not found
     */
    public static LevelData getLevel(int section, int number, Difficulty difficulty) {
        String[] wordPool = selectWordPool(Difficulty.values()[number]);
        if (number > 0 && number < 4) return buildLevel(number,wordPool); // number has to be between highest and lowest amnt of levels
        return null;
    }

    /**
     * Builds a level with the given number and word pool.
     *
     * @param number   The level number.
     * @param wordPool The pool of words to use for the level.
     * @return The LevelData for the given number and word pool.
     */
    private static LevelData buildLevel(int number, String[] wordPool) {
        Sprite background = new Sprite(null, 0, 0); // TODO: replace null with section background image later

        switch (number) {
            case 1: {
                // 10 Normal enemies — gentle introduction
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool)
                };
                return new LevelData(1, enemies, background);
            }

            case 2: {
                // Mix of Normal and HAS_HEART enemies
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool)
                };
                return new LevelData(2, enemies, background);
            }

            case 3: {
                // Introduce BIG enemy type
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.BIG, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.BIG, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool)
                };
                return new LevelData(3, enemies, background);
            }

            default:
                return null;
        }
    }

    /**
     * Creates an Enemy with randomly sampled words from the given pool.
     * The number of words assigned scales with the enemy's attribute:
     * Normal / HAS_HEART -> 5 words
     * Big                -> 10 words
     * Boss               -> 15 words
     *
     * @param section   Unused for now but reserved for future sprite/background selection
     * @param attribute The enemy type
     * @param wordPool  Pool to sample from
     * @return A new Enemy instance
     */
    private static Enemy makeEnemy(int section, Enemy_Attribute attribute, String[] wordPool) {
        int wordCount;

        switch (attribute) {
            case BIG:
                wordCount = 10;
                break;
            case BOSS:
                wordCount = 15;
                break;
            case NORMAL:
            case HAS_HEART:
            default:
                wordCount = 5;
                break;
        }

        String[] words = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            words[i] = randomWord(wordPool);
        }

        // these are the 3 bird images that can be used for enemies
        String[] birdImages = {
            "global/Bird1.png",
            "global/Bird2.png",
            "global/Bird3.png"
        };

        // this rndomly choose one bird image each time an enemy is created
        String chosenBird = birdImages[(int) (Math.random() * birdImages.length)];

        // Load the chosen image from the resources/images folder
        ImageIcon icon = new ImageIcon(chosenBird);
        Image image = icon.getImage();

        // Here is the the enemy sprite using the bird image and starting position (0,0)
        Sprite sprite = new Sprite(image, 0, 0);

        // Here the enemy is created with the randomly sampled words, the specified attribute, and the sprite with the bird image
        return new Enemy(words, attribute, sprite);
    }

    /**
     * Returns a random word from the given pool.
     *
     * @param pool The pool of words to choose from
     * @return A random word from the pool
     */
    private static String randomWord(String[] pool) {
        int index = (int) (Math.random() * pool.length);
        return pool[index];
    }

    /**
     * Returns the word pool corresponding to the chosen difficulty.
     *
     * @param difficulty The difficulty chosen by the player
     * @return The word pool for the chosen difficulty
     */
    private static String[] selectWordPool(Difficulty difficulty) {
        switch (difficulty) {
            case MEDIUM:
                return WORDS_MEDIUM;
            case HARD:
                return WORDS_HARD;
            case EASY:
            default:
                return WORDS_EASY;
        }
    }
}