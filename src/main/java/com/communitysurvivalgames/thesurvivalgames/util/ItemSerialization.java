package com.communitysurvivalgames.thesurvivalgames.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;

public class ItemSerialization {

	public static String inventoryToString(Inventory invInventory) {
		String serialization = 54 + ";";
		for (int i = 0; i < invInventory.getSize(); i++) {
			ItemStack is = invInventory.getItem(i);
			if (is != null) {
				String serializedItemStack = "";

				String isType = is.getType().name();
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0) {
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1) {
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}

				Map<Enchantment, Integer> isEnch = is.getEnchantments();
				if (isEnch.size() > 0) {
					for (Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
						serializedItemStack += ":e@" + ench.getKey().getName() + "@" + ench.getValue();
					}
				}

				serializedItemStack += ":n@" + is.getItemMeta().getDisplayName();

				if (is.getItemMeta().getLore() != null) {
					serializedItemStack += ":l@";
					for (String l : is.getItemMeta().getLore()) {
						serializedItemStack += l + "%";
					}
				}

				serialization += i + "#" + serializedItemStack + ";";
			}
		}
		return serialization;
	}

	public static Inventory stringToInventory(String invString) {
		String[] serializedBlocks = invString.split(";");
		String invInfo = serializedBlocks[0];
		Inventory deserializedInventory = Bukkit.getServer().createInventory(null, 54, "Hi");

		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] serializedBlock = serializedBlocks[i].split("#");
			int stackPosition = Integer.valueOf(serializedBlock[0]);

			if (stackPosition >= deserializedInventory.getSize())
				continue;

			ItemStack is = null;
			boolean createdItemStack = false;

			String[] serializedItemStack = serializedBlock[1].split(":");
			for (String itemInfo : serializedItemStack) {
				String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")) {
					is = new ItemStack(Material.getMaterial(itemAttribute[1]));
					createdItemStack = true;
				} else if (itemAttribute[0].equals("s") && createdItemStack) {
					createdItemStack = true;
					SpawnEgg se = new SpawnEgg();
					se.setSpawnedType(EntityType.valueOf(itemAttribute[1]));
					is = se.toItemStack();
				} else if (itemAttribute[0].equals("d") && createdItemStack) {
					is.setDurability(Short.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("a") && createdItemStack) {
					is.setAmount(Integer.valueOf(itemAttribute[1]));
				} else if (itemAttribute[0].equals("e") && createdItemStack) {
					ItemMeta meta = is.getItemMeta();
					meta.addEnchant(Enchantment.getByName(itemAttribute[1]), Integer.valueOf(itemAttribute[2]), true);
					is.setItemMeta(meta);
				} else if (itemAttribute[0].equals("n") && createdItemStack) {
					ItemMeta meta = is.getItemMeta();
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemAttribute[1]));
					is.setItemMeta(meta);

				} else if (itemAttribute[0].equals("l") && createdItemStack) {
					ItemMeta meta = is.getItemMeta();
					List<String> lore = new ArrayList<String>();
					String[] loreArray = itemAttribute[1].split("%");
					for (String c : loreArray) {
						lore.add(ChatColor.translateAlternateColorCodes('&', c));
					}
					meta.setLore(lore);
					is.setItemMeta(meta);
				}

			}
			deserializedInventory.setItem(stackPosition, is);
		}

		return deserializedInventory;
	}
}
