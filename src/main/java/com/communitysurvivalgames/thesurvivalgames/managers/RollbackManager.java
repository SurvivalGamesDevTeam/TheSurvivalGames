package com.communitysurvivalgames.thesurvivalgames.managers;

import org.bukkit.Bukkit;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.rollback.Rollback;

public class RollbackManager {
	public void rollbackArena(SGArena a) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Rollback(a));
	}
}
