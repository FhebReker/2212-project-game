package ca.uwo.cs2212.group54.stayingalive.powerups;

/**
 * Represents the different types of power-up effects available in the game.
 * <p>
 * Each power type defines a specific ability that can be activated by the player.
 * These types are used to control both the behavior of the power-up and how it
 * is displayed in the user interface.
 *  @author Mohamed Ahme
 */
public enum Power_type {

    /**
     * Temporarily freezes all enemies on the screen, preventing all enemy movement and actions.
     */
    TIME_STOP,

    /**
     * This power deals damage to all enemies from a the radius of the player.
     */
    AREA_OF_EFFECT,

    /**
     * Absorbs or blocks any incoming damage to the player for a limited amount of time.
     */
    SHIELD;

    /**
     * Returns a user-friendly display name for the shield power up.
     * <p>
     * The value is used in the game's user interface to present readable names
     * instead of enum constants.
     *
     * @return the formatted display name of the power type
     */
    public String getDisplayName() {
        return switch (this) {
            case TIME_STOP       -> "Time Stop";
            case AREA_OF_EFFECT  -> "Area of Effect";
            case SHIELD          -> "Shield";
        };
    }

    /**
     * returns a short description of the power-up effect for display in the store or inventory.
     * <p>
     * This part isto give players a quick understanding of what the power-up does without needing
     * does when viewed in the store or inventory.
     *
     * @return a brief description of the power-up's effect
     */
    public String getEffectDescription() {
        return switch (this) {
            case TIME_STOP       -> "Freezes all enemies for a short duration";
            case AREA_OF_EFFECT  -> " Eliminates all nearby enemies";
            case SHIELD          -> "Blocks incoming damage for a limited time";
        };
    }
}
