package com.communitysurvivalgames.thesurvivalgames.util;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerVanishUtil {

	public static void hideAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
					Bukkit.getServer().getOnlinePlayers()[i].hidePlayer(player);
				}
			}
		});

	}

	public static void showAll(final Player player) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class), new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().length; i++) {
					Bukkit.getServer().getOnlinePlayers()[i].showPlayer(player);
				}
			}
		});

	}
}
