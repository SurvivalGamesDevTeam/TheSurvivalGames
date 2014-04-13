package com.communitysurvivalgames.thesurvivalgames.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SerializableAs("KitItem")
public class KitItem implements ConfigurationSerializable {
	private ItemStack item;


	public KitItem() {
	}

	public KitItem(Material type) {

        //This will return the correct ItemMeta for the Material specified
        //As some items has specialist meta eg Skulls

        //Now looking at it there is no need to even store ItemMeta separately
        //As it's attached to the item stack any how.

		item = new ItemStack(type);
	    item.setItemMeta(Bukkit.getItemFactory().getItemMeta(type));
	}

    protected KitItem(ItemStack it){
        this.item = it;
    }

	public ItemStack getItem() {
		return item;
	}

	public void addEnchantment(Enchantment e, int level) {

		//Just a thought That is what chaining is for
        item.getItemMeta().addEnchant(e,level,true);

	}

	public void addEnchantment(Enchantment e) {
		item.getItemMeta().addEnchant(e, 1, true);
	}

	public void addEnchantment(String e, int level) {
		item.getItemMeta().addEnchant(Enchantment.getByName(e), level, true);
	}

	public void addEnchantment(String e) {
		item.getItemMeta().addEnchant(Enchantment.getByName(e), 1, true);
	}

	public void setLore(String s) {

        //Very wasteful Lore is already a In built list again its Chaining
        item.getItemMeta().getLore().add(ChatCode(s));
	}

	public void setLore(List<String> s) {

        //Again not need lore Type even is a List<String>
        //Also unless its your intention this overrides any single entries
		for (String string : s)
			item.getItemMeta().getLore().add(ChatCode(string));
	}

	public void setDisplayName(String s) {
		item.getItemMeta().setDisplayName(ChatCode(s));
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

    /**
     * Chat code.
     * Short hand and reduce code
     * ChatColor.translateAlternateColorCodes is its functionality
     *
     * @param s the s
     * @return the string
     */
    private String ChatCode(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    /**
     * Deserialize kit item.
     *
     * @param map the map
     * @return the kit item
     */
    public static KitItem deserialize(Map<String, Object> map) {
        return new KitItem((ItemStack)map.get("stack"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>(1);
        map.put("stack",this.item);
        return map;
    }
}
