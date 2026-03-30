package main.java.ca.uwo.cs2212.group54.stayingalive.powerups;

/**
 * Represents an item that can be purchased and equipped in the store.
 * All store items must provide information like as name and price,
 * and have purchasing and equipping functionality.
 */
public interface StoreItem {

    /**
     * Gets the name of store item.
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Gets the price of the store item.
     *
     * @return the price of the item in in-game currency
     */
    int getPrice();

    /**
     * Marks the item as purchased.
     * This method will  update the internal state to indicate
     * the item has been bought.
     */
    void purchase();

    /**
     * Equips the item.
     * This method should only take effect if the item has been purchased.
     */
    void equip();
}