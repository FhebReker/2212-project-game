package main.java.ca.uwo.cs2212.group54.stayingalive.powerups;

/**
 * Represents a cosmetic item in the game store.
 * <p>
 * A cosmetic item is a non-functional item that can changes the visual of a player
 * appearance of a character or element in the game. Each cosmetic has
 * a name, price, type, and associated sprite for display in the store and inventory.
 */
public class Cosmetic {

    /** The name of the type of cosmetic */
    private String name;

    /** The price of the cosmetic item in the in-game currency */
    private int price;

    /** The category of the cosmetic */
    private Cosmetic_type type;

    /** Lastly the visual representation of the cosmetic */
    private Sprite sprite;

    /**
     * Constructs a new {@code Cosmetic} for the attributes.
     *
     * @param name    the name of the cosmetic item
     * @param price  the price of the cosmetic in in-game currency (coins)
     * @param type   the type of category of the cosmetic
     * @param sprite the visual sprite of the cosmetic
     */
    public Cosmetic(String name, int price, Cosmetic_type type, Sprite sprite) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.sprite = sprite;
    }

    /**
     * Returns the name of this cosmetic item.
     *
     * @return the name of the cosmetic
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of this cosmetic item.
     *
     * @return the price in in-game currency
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the type of this cosmetic item.
     *
     * @return the cosmetic type
     */
    public Cosmetic_type getType() {
        return type;
    }

    /**
     * Returns the sprite  with this cosmetic.
     *
     * @return the sprite for this cosmetic
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Her it sets the name of this cosmetic item.
     *
     * @param name the new name of the cosmetic
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of this cosmetic item.
     *
     * @param price the new price in coins for the cosmetic
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Sets the type of this cosmetic item.
     *
     * @param type the new cosmetic type
     */
    public void setType(Cosmetic_type type) {
        this.type = type;
    }

    /**
     * Sets the sprite of this cosmetic item.
     *
     * @param sprite the new sprite for the cosmetic
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
