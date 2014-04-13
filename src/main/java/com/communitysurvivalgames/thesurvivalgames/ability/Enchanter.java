package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Enchanter extends SGAbility implements Listener {

	public Enchanter() {
		super(6);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (event.getPlayer().getItemInHand().getType() == Material.ENCHANTMENT_TABLE) {
				this.removeOneFromHand(player);
				Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.ENCHANTING);
				event.getPlayer().openInventory(inv);
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.PURPLE).withFade(Color.NAVY).with(Type.BURST).trail(false).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}
}
