package ca.uwo.cs2212.group54.stayingalive.powerups;

import ca.uwo.cs2212.group54.stayingalive.sprites.Sprite;

/**
 * Represents a purchasable power-up in the game store.
 * Each power-up has a name, price, description, type, and sprite.
 * A power-up can be purchased, equipped, activated, and placed on cooldown.
 *  @author Mohamed Ahmed
 * <p> The cooldown duration depends on the type of power-up:</p>
 * 
 *     <li>TIME_STOP: 10 seconds
 *     <li>SHIELD: 15 seconds
 *     <li>AREA_OF_EFFECT: 0 seconds
 * 
 */
public class Powerup implements StoreItem {

    /** Cooldown duration in seconds for the time stop power-up. */
    private static final int TIME_STOP_COOLDOWN = 10;

    /** Cooldown duration in seconds for the shield power-up. */
    private static final int SHIELD_COOLDOWN = 15;

    /** Cooldown duration in seconds for the area of effect power-up. */
    private static final int AREA_OF_EFFECT_COOLDOWN = 0;

    /** Name shown in the store */
    private final String name;

    /** Price of the power-up in in-game currency. */
    private final int price;

    /** Short description shown in the store. */
    private final String description;

    /** Type of power-up effect. */
    private final Power_type type;

    /** Visual of the power-up. */
    private final Sprite sprite;

    /** True if the player has purchased this power-up. */
    private boolean bought;

    /** True if the power-up is currently equipped. */
    private boolean equipped;

    /** True if the power-up is currently on cooldown. */
    private boolean onCooldown;

    /** Time in milliseconds for when the cooldown ends. */
    private long cooldownEndTime;

    /**
     * Creates a new power-up.
     *
     * @param name the name of the power up
     * @param price the price of the powerup
     * @param description the short description of the powerup
     * @param type the type of power-up effect
     * @param sprite the sprite used to display the powerup
     * @throws IllegalArgumentException if the name is blank or negative 
     */
    public Powerup(String name, int price, String description,
                   Power_type type, Sprite sprite) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.name = name;
        this.price = price;
        this.description = description != null ? description : "";
        this.type = type;
        this.sprite = sprite;
        this.bought = false;
        this.equipped = false;
        this.onCooldown = false;
        this.cooldownEndTime = 0;
    }

    /**
     * Gets the name of the power-up.
     *
     * @return the power-up name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the power-up.
     *
     * @return the power-up price
     */
    @Override
    public int getPrice() {
        return price;
    }

    /**
     * Gets the description of this power-up.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks whether this power-up has been purchased.
     *
     * @return true if purchased, false otherwise
     */
    public boolean isBought() {
        return bought;
    }

    /**
     * This marks this power-up as purchased.
     *
     * @return true if the purchase succeeded
     */
    @Override
    public boolean purchase() {
        if (bought) {
            return false;
        }
        bought = true;
        return true;
    }

    /**
     * Equips this power-up if it has already been purchased
     * and is not currently equipped.
     *
     * @return true if equipping succeeded, false otherwise
     */
    @Override
    public boolean equip() {
        if (!bought || equipped) {
            return false;
        }
        equipped = true;
        return true;
    }

    /**
     * Unequips this power-up and clears any active cooldown state.
     */
    public void unequip() {
        equipped = false;
        onCooldown = false;
        cooldownEndTime = 0;
    }

    /**
     * Activates this power-up if it is equipped and not on cooldown.
     * If activation succeeds, the cooldown timer starts based on the
     * power-up type.
     *
     * @return true if activation succeeded
     */
    public boolean activate() {
        isCooldownFinished();

        if (!equipped || onCooldown) {
            return false;
        }

        int cooldown = getCooldownDuration();
        if (cooldown > 0) {
            onCooldown = true;
            cooldownEndTime = System.currentTimeMillis() + (cooldown * 1000L);
        }

        return true;
    }

    /**
     * Checks whether the cooldown has finished.
     * If the cooldown end time has passed, the cooldown state is cleared.
     *
     * @return true if the power-up is not on cooldown
     */
    public boolean isCooldownFinished() {
        if (onCooldown && System.currentTimeMillis() >= cooldownEndTime) {
            onCooldown = false;
        }
        return !onCooldown;
    }

    /**
     * Gets the remaining cooldown time in seconds.
     *
     * @return the remaining cooldown in seconds or 0 if not on cooldown
     */
    public long getRemainingCooldown() {
        isCooldownFinished();

        if (!onCooldown) {
            return 0;
        }

        long remaining = cooldownEndTime - System.currentTimeMillis();
        return Math.max(0, remaining / 1000L);
    }

    /**
     * Checks whether this power-up is currently equipped.
     *
     * @return true if equipped, false otherwise
     */
    public boolean isEquipped() {
        return equipped;
    }

    /**
     * This part checks whether the power-up is on cooldown.
     *
     * @return true if on cooldown, false otherwise
     */
    public boolean isOnCooldown() {
        isCooldownFinished();
        return onCooldown;
    }

    /**
     * Gets the type of this power-up.
     *
     * @return the power-up type
     */
    public Power_type getType() {
        return type;
    }

    /**
     * Here gets the cooldown duration for this power-up type.
     *
     * @return the cooldown duration in seconds
     */
    public int getCooldownDuration() {
        switch (type) {
            case TIME_STOP:
                return TIME_STOP_COOLDOWN;
            case SHIELD:
                return SHIELD_COOLDOWN;
            case AREA_OF_EFFECT:
                return AREA_OF_EFFECT_COOLDOWN;
            default:
                return 0;
        }
    }

    /**
     * Here gets the sprite associated with this power-up.
     *
     * @return the power-up sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns a string representation of this power-up.
     *
     * @return a formatted string for the power-up information
     */
    @Override
    public String toString() {
        return String.format(
                "Powerup{name='%s', type=%s, price=%d, bought=%b, equipped=%b}",
                name, type, price, bought, equipped
        );
    }

    /**
     * Compares this power-up for equality.
     * 
     *
     * @param o the object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Powerup other)) {
            return false;
        }
        return name.equals(other.name) && type == other.type;
    }
}
