package ca.uwo.cs2212.group54.stayingalive.store;

/**
 * This class represents a purchasable power-up in the game store.
 * Power-ups are items that provide temporary gameplay effects and have a cooldown period.
 */
public class Powerup implements StoreItem {

    private final String name;
    private final int price;
    private final String description;
    private final Power_type type;
    private final int cooldownDuration;
    private final Sprite sprite;

    private boolean bought;
    private boolean equipped;
    private boolean onCooldown;
    private long cooldownEndTime;

    /**
     * Creates new Powerup.
     * @param name             name for in store and HUD
     * @param price            cost to purchase
     * @param description      short store description
     * @param type             the power-up effect type
     * @param cooldownDuration then cooldowns seconds after use
     * @param sprite           visual representation
     */
    public Powerup(String name, int price, String description,
                   Power_type type, int cooldownDuration, Sprite sprite) {
        if (name == null || name.isBlank())  throw new IllegalArgumentException("Name cannot be blank");
        if (price < 0)                       throw new IllegalArgumentException("Price cannot be negative");
        if (cooldownDuration < 0)            throw new IllegalArgumentException("Cooldown cannot be negative");

        this.name             = name;
        this.price            = price;
        this.description      = description != null ? description : "";
        this.type             = type;
        this.cooldownDuration = cooldownDuration;
        this.sprite           = sprite;
        this.bought           = false;
        this.equipped         = false;
        this.onCooldown       = false;
        this.cooldownEndTime  = 0;
    }

    //  This is the storeItem interface 

    @Override
    public String getName() { return name; }

    @Override
    public int getPrice() { return price; }

    @Override
    public String getDescription() { return description; }

    @Override
    public boolean isBought() { return bought; }

    /**
     *Here marks this powerup as purchased.
     *
     * @return true if purchase succeeded or false only if already owned
     */
    @Override
    public boolean purchase() {
        if (bought) return false;
        bought = true;
        return true;
    }

    /**
     * Equips this power-up if owned and not already equipped.
     *
     * @return true if equip succeeded
     */
    @Override
    public boolean equip() {
        if (!bought || equipped) return false;
        equipped = true;
        return true;
    }

    /** Unequips the power-up and resets cooldown state. */
    @Override
    public void unequip() {
        equipped     = false;
        onCooldown   = false;
        cooldownEndTime = 0;
    }

    //Powerup-specific
    /**
     * Activates the power-up effect and starts the cooldown timer.
     * Has no effect if not equipped or currently on cooldown.
     * @return true if activation succeeded
     */
    public boolean activate() {
        if (!equipped || onCooldown) return false;
        onCooldown      = true;
        cooldownEndTime = System.currentTimeMillis() + (cooldownDuration * 1000L);
        return true;
    }

    /**
     * Checks and updates the cooldown state.
     * Call this each game tick or before checking {@link #isOnCooldown()}.
     *
     * @return true if the cooldown has finished (or was never active)
     */
    public boolean isCooldownFinished() {
        if (onCooldown && System.currentTimeMillis() >= cooldownEndTime) {
            onCooldown = false;
        }
        return !onCooldown;
    }

    /** @return remaining cooldown in seconds, or 0 if not on cooldown */
    public long getRemainingCooldown() {
        if (!onCooldown) return 0;
        long remaining = cooldownEndTime - System.currentTimeMillis();
        return Math.max(0, remaining / 1000L);
    }

    public boolean isEquipped()       { return equipped; }
    public boolean isOnCooldown()     { return onCooldown; }
    public Power_type getType()       { return type; }
    public int getCooldownDuration()  { return cooldownDuration; }
    public Sprite getSprite()         { return sprite; }

    @Override
    public String toString() {
        return String.format("Powerup{name='%s', type=%s, price=%d, bought=%b, equipped=%b}",
                name, type, price, bought, equipped);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Powerup other)) return false;
        return name.equals(other.name) && type == other.type;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + (type != null ? type.hashCode() : 0);
    }
}