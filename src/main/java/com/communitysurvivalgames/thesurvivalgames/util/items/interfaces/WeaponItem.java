/**
 * Name: WeaponItem.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

/**
 * The enum Weapon item. This is a list of all craftable tools. All can be used
 * as a weapon special item
 * 
 */
public enum WeaponItem {
    GOLD_SWORD, GOLD_AXE, GOLD_PICKAXE, GOLD_HOE, GOLD_SPADE, IRON_SWORD, IRON_AXE, IRON_PICKAXE, IRON_HOE, IRON_SPADE, DIAMOND_SWORD, DIAMOND_AXE, DIAMOND_PICKAXE, DIAMOND_HOE, DIAMOND_SPADE, WOOD_SWORD, WOOD_AXE, WOOD_PICKAXE, WOOD_HOE, WOOD_SPADE, STONE_SWORD, STONE_AXE, STONE_PICKAXE, STONE_HOE, STONE_SPADE;

    /**
     * Search the enum to see if a given item is on the list
     * <p>
     * Return true if it is false if it's not
     * 
     * @param item the item {@link org.bukkit.Material} in it's String format
     * @return the boolean
     */
    public static boolean find(String item) {
        for (WeaponItem v : values()) {
            if (v.name().equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }
}
