/**
 * Name: AbstractMenuItem.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The type Abstract icon menu item
 */
public class AbstractMenuItem implements ILore, IDisplayName, Selectable, SingleAction, IPermissible, ConfigurationSerializable {
  private ItemStack item;
    private Material material;
    private ItemMeta meta;
    private List<String> lore;
    private String displayName;
    private Integer Id;
    private String permission;
    private List<ActionActivator> activators;

    /**
     * Instantiates a new Abstract menu item. This is NOT to be used
     * 
     * @param item the item
     * @param material the material
     * @param meta the meta
     * @param lore the lore
     * @param displayName the display name
     * @param id the id
     * @param permission the permission
     * @param activators the activators
     */
    protected AbstractMenuItem(ItemStack item, Material material, ItemMeta meta, List<String> lore, String displayName, Integer id, String permission,
            List<ActionActivator> activators) {
        this.item = item;
        this.material = material;
        this.meta = meta;
        this.lore = lore;
        this.displayName = displayName;
        Id = id;
        this.permission = permission;
        this.activators = activators;
    }

    /**
     * Deserialize abstract menu item. Returns a new instance of it's self
     * 
     * @param map the map
     * @return the abstract menu item
     * @throws NullPointerException the null pointer exception
     */
    public static AbstractMenuItem deserialize(Map<String, Object> map) throws NullPointerException {

        Object oStack = map.get("item"), oId = map.get("id"), oPerm = map.get("permission"), oAct = map.get("activators");

        if (oStack == null || oId == null || oPerm == null || oAct == null) {
            throw new NullPointerException("Error in deserialize of Menu Item");
        }
        ItemStack itemStack = (ItemStack) oStack;
        Integer id = (Integer) oId;
        String perm = (String) oPerm;
        String[] act = (String[]) oAct;
        List<ActionActivator> action = new ArrayList<>();
        for (int i = 1; i < act.length; i++) {
            action.add(ActionActivator.valueOf(act[i]));
        }

        return new AbstractMenuItem(itemStack, itemStack.getType(), itemStack.getItemMeta(), itemStack.getItemMeta().getLore(), itemStack.getItemMeta().getDisplayName(), id, perm,
                                    action);
    }

    /**
     * Creates a Map representation of this class.
     * <p/>
     * This class must provide a method to restore this class, as defined in the
     * {@link org.bukkit.configuration.serialization.ConfigurationSerializable}
     * interface javadocs.
     * 
     * @return Map containing the current state of this class
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", getItem());
        map.put("id", this.getId());
        map.put("permission", this.getPermission());
        String[] act = new String[activators.size()];
        for (int i = 1; i < activators.size(); i++) {
            act[i] = activators.get(i).name();
        }
        map.put("activators", act);
        return map;
    }

 /**
     * Gets display name.
     * 
     * @return the display name
     */
    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets display name.
     * 
     * @param name the name
     */
    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    /**
     * Gets lore.
     * 
     * @return the lore
     */
    @Override
    public List<String> getLore() {
        return this.lore;
    }

    /**
     * Add line.
     * 
     * @param line the line
     */
    @Override
    public void addLoreLine(String line) {
        this.lore.add(line);
    }

    /**
     * Gets item meta.
     * 
     * @param material the material
     * @return the ItemMeta related to the material
     */
    @Override
    public ItemMeta getItemMeta(Material material) {
        this.meta = Bukkit.getItemFactory().getItemMeta(material);
        this.item = new ItemStack(material, 1);
        return this.meta;
    }

    /**
     * Gets material. to use for the icon. {@link org.bukkit.Material}
     * 
     * @return the material
     */
    @Override
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Gets id thats unique for this item
     * 
     * @return the id
     */
    @Override
    public Integer getId() {
        return this.Id;
    }

    /**
     * Sets id the unique id
     * 
     * @param id the id
     */
    public void setId(Integer id) {
        this.Id = id;
    }

    /**
     * Sets material to be used as the icon
     * <p>
     * {@link org.bukkit.Material}
     * 
     * @param material the material
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Get item. To use in the icon menu
     * <p>
     * {@link org.bukkit.inventory.ItemStack}
     * 
     * @return the item stack
     */
    public ItemStack getItem() {
        return this.item;
    }

    /**
     * Sets item.
     * <p>
     * Sets the {@link org.bukkit.inventory.ItemStack} passing it a <br>
     * {@link org.bukkit.Material} to convert
     * 
     * @param material the material
     */
    public void setItem(Material material) {
        this.item = new ItemStack(material, 1);
    }

    /**
     * Has permission. Returns True if the permission has been set
     * 
     * @return the boolean
     */
    @Override
    public boolean hasPermission() {
        if (this.permission != null) {
            return true;
        }
        return true;
    }

    /**
     * Gets permission to use the item in a string format
     * 
     * @return the permission
     */
    @Override
    public String getPermission() {
        return this.permission;
    }

    /**
     * Sets permission. The permission required for a player to <br>
     * interact with. The permission is {@link java.lang.String} based
     * 
     * @param permission the permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets activators.
     * 
     * @return the activators
     */
    @Override
    public List<ActionActivator> getActivators() {
        return this.activators;
    }

    /**
     * Sets activator.
     * 
     * @param activator the activator
     */
    public void setActivator(ActionActivator activator) {
        this.activators.add(activator);
    }

    public void setMeta() {
        this.item.setItemMeta(this.meta);
    }

}
