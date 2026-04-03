package ca.uwo.cs2212.group54.stayingalive.powerups;

/**
 * Represents an item that can be purchased and equipped in the store.
 * All store items must provide information such as name and price,
 * and have purchasing and equipping functionality.
 * @author Mohamed Ahmed
 */
public interface StoreItem {

    /**
     * Gets the name of store item.
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Here gets the price of the store item.
     *
     * @return the price of the item in coins
     */
    int getPrice();

    /**
     * Marks the item as purchased.
     * This method will  update the internal state to indicate
     * then the item has been bought.
     */
    boolean purchase();

    /**
     * Equips the item.
     * This method should only take effect if the item has been purchased.
     */
    boolean equip();
}
