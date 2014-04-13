package com.communitysurvivalgames.thesurvivalgames.ability;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class Crafter extends SGAbility implements Listener {

	public Crafter() {
		super(3);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (event.getPlayer().getItemInHand().getType() == Material.WORKBENCH) {
                Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.WORKBENCH);
                event.getPlayer().openInventory(inv);
			}
		}
	}
}
