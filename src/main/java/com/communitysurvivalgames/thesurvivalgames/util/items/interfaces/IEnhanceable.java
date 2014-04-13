/**
 * Name: IEnhanceable.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

import java.util.List;
import org.bukkit.enchantments.Enchantment;

/**
 * The interface I enhanceable.
 */
public interface IEnhanceable extends IMetable {
    /**
     * Gets enchants.
     * 
     * @return the enchants
     */
    List<Enchantment> getEnchants();
}
