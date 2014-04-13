package com.communitysurvivalgames.thesurvivalgames.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class SGEnchantment extends Enchantment {
    public SGEnchantment(int id) {
		super(id);
	}

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public String getName() {
        return "null";
    }

    @Override
    public int getStartLevel() {
        return 1;
    }
    
    public String getLore(int lvl) {
    	return "null";
    }
}
