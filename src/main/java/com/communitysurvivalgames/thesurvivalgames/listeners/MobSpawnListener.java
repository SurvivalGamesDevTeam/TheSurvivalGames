package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class MobSpawnListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onSpawn(CreatureSpawnEvent event) {
		for (int i = 0; i < SGApi.getMultiWorldManager().getWorlds().size(); i++) {
			if (SGApi.getMultiWorldManager().getWorlds().get(i).getWorld() == event.getLocation().getWorld()) {
				if (event.getSpawnReason() == SpawnReason.NATURAL)
					event.setCancelled(true);
			}
		}
	}
}
