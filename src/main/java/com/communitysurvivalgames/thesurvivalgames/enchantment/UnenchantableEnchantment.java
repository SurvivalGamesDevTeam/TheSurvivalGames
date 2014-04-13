package com.communitysurvivalgames.thesurvivalgames.enchantment;

import org.bukkit.ChatColor;

public class UnenchantableEnchantment extends SGEnchantment {

    public UnenchantableEnchantment(int id) {
		super(id);
	}

    @Override
    public String getName() {
        return "UNENCHANTABLE";
    }
    
	@Override
	public String getLore(int lvl) {
		return ChatColor.GRAY + "Un-Enchantable " + RomanNumeral.convert(lvl);
	}
}