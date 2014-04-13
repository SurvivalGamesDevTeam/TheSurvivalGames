/**
 * Name: IMetable.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The interface I metable.
 */
public interface IMetable {
    /**
     * Gets item meta.
     * 
     * @param material the material
     * @return the ItemMeta related to the material
     */
    ItemMeta getItemMeta(Material material);

    /**
     * Gets material. to use for the icon. {@link org.bukkit.Material}
     * 
     * @return the material
     */
    Material getMaterial();

    /**
     * Gets id thats unique for this item
     * 
     * @return the id
     */
    Integer getId();
}
