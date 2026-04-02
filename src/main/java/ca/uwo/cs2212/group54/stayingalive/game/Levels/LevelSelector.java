package ca.uwo.cs2212.group54.stayingalive.game.Levels;

import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy;
import ca.uwo.cs2212.group54.stayingalive.game.Enemies.Enemy_Attribute;
import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;
/**
 * LevelSelector class
 *
 * @author Malik Alghneimin
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
     * Returns a new LevelData for the given section and level number, or null
     * if no such level is defined.
     *
     * @param section    Section number (1 or 2)
     * @param number     Level number within the section (1–3)
     * @param difficulty Difficulty chosen by the player, used to select words
     * @return           A fully constructed LevelData, or null if not found
     */
    public static LevelData getLevel(int section, int number, Difficulty difficulty) {
        String[] wordPool = selectWordPool(difficulty);

        switch (section) {
            case 1:
                return buildLevel(number, wordPool);
            default:
                return null;
        }
    }
    
    private static LevelData buildLevel(int number, String[] wordPool) {
        Sprite background = new Sprite(null, 0, 0); // TODO: replace null with section-1 ImageIcon

        switch (number) {
            case 1: {
                // 10 Normal enemies — gentle introduction
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,  wordPool),
                };
                return new LevelData(1, enemies, background);
            }
            case 2: {
                // Mix of Normal and a hasHeart enemy
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL,   wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                };
                return new LevelData(2, enemies, background);
            }
            case 3: {
                // Introduce the Big enemy type
                Enemy[] enemies = {
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.BIG,    wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.HAS_HEART, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.BIG,    wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool),
                    makeEnemy(1, Enemy_Attribute.NORMAL, wordPool)
               };
                return new LevelData(3, enemies, background);
            }
            default: {
                return null;
            }
        }
    }

    // Helpers

    /**
     * Creates an Enemy with randomly sampled words from the given pool.
     * The number of words assigned scales with the enemy's attribute weight:
     *   Normal / hasHeart → 5 words
     *   Big               → 10 words
     *   Boss              → 15 words
     *
     * @param section   Unused for now but reserved for future sprite selection
     * @param attribute The enemy type
     * @param wordPool  Pool to sample from
     * @return          A new Enemy instance
     */
    private static Enemy makeEnemy(int section, Enemy_Attribute attribute, String[] wordPool) {
        int wordCount;
        switch (attribute) {
            case BIG:                   wordCount = 10; break;
            case BOSS:                  wordCount = 15; break;
            case NORMAL: case HAS_HEART:
            default:                    wordCount = 5; break;
        }

        String[] words = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            words[i] = randomWord(wordPool);
        }

        Sprite sprite = new Sprite(null, 0, 0); // TODO: replace with actual enemy sprite
        return new Enemy(words, attribute, sprite);
    }

    /**
     * Returns a random word from the given pool.
     * 
     * @param pool The pool of words to choose from
     * @return     A random word from the pool
     */
    private static String randomWord(String[] pool) {
        int index = (int) (Math.random() * pool.length);
        return pool[index];
    }

    /**
     * Returns the word pool corresponding to the chosen difficulty.
     * 
     * @param difficulty The difficulty chosen by the player
     * @return           The word pool for the chosen difficulty
     */
    private static String[] selectWordPool(Difficulty difficulty) {
        switch (difficulty) {
            case MEDIUM: return WORDS_MEDIUM;
            case HARD:   return WORDS_HARD;
            case EASY:
            default:     return WORDS_EASY;
        }
    }
}
