/**
 * Name: AbstractItem.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items;

import java.util.List;
import com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.IDisplayName;
import com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.IEnhanceable;
import com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.ILore;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AbstractItem implements IDisplayName, ILore, IEnhanceable {

    private ItemStack item;
    private Material material;
    private ItemMeta meta;
    private String displayName;
    private List<Enchantment> enchantments;
    private Integer Id;
    private String permission;

    public abstract void setPermission(String s);

    public abstract String getPermission();

    /**
     * Gets item meta.
     * 
     * @param material the material
     * @return the ItemMeta related to the material
     */
    @Override
    public ItemMeta getItemMeta(Material material) {
        return null;
    }

    /**
     * Gets display name.
     * 
     * @return the display name
     */
    @Override
    public String getDisplayName() {
        return null;
    }

    /**
     * Sets display name.
     * 
     * @param name the name
     */
    @Override
    public void setDisplayName(String name) {

    }

    /**
     * Gets enchants.
     * 
     * @return the enchants
     */
    @Override
    public List<Enchantment> getEnchants() {
        return null;
    }

    /**
     * Gets lore.
     * 
     * @return the lore
     */
    @Override
    public List<String> getLore() {
        return null;
    }

    /**
     * Add line.
     * 
     * @param line the line
     */
    @Override
    public void addLoreLine(String line) {

    }
}
