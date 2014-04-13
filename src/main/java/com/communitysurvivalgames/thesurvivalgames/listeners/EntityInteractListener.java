package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class EntityInteractListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() instanceof EnderCrystal) {
			SGApi.getKitManager().displayDefaultKitSelectionMenu(event.getPlayer());
		}
	}
}
