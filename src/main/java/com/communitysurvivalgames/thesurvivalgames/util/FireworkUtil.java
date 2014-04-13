package com.communitysurvivalgames.thesurvivalgames.util;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class FireworkUtil implements Listener {

	static FireworkUtil circleUtil = new FireworkUtil();

	public static FireworkUtil getCircleUtil() {
		return circleUtil;
	}

	public void playFireworkCircle(final Player player, Location fLoc, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = fLoc.add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(l, effect), index);

		}
	}
	
	public void playFireworkCircle(final Player player, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(l, effect), index);

		}
	}

	public void playFireworkRing(final Player player, final FireworkEffect effect, int size, final int distance) {
		Bukkit.getLogger().info("Called firework method");
		int index = 0;
		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(l, effect), index);

		}

		for (double t = 0; t < 2 * Math.PI; t += Math.toRadians(size)) {
			index += 3;
			Location l = player.getLocation().add(Math.cos(t) * distance, 0.5, Math.sin(t) * distance);
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(l, effect), index);
		}
	}
	
	public void playFireworkLine(Location top, Location bottom, final FireworkEffect effect, int number) {
		Bukkit.getLogger().info("Called firework method");
		//double interval = (top.getY() - bottom.getY()) / number;
		double interval = 1;
		int index = 0;
		double currentY = top.getY();
		for (int i = 0; i < number; i++) {
			currentY -= interval;
			Location l = new Location(top.getWorld(), top.getX(), bottom.getY() + currentY, top.getZ());
			index += 3;
			Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new PlayFireworkEffect(l, effect), index);

		}

	}

	public class PlayFireworkEffect implements Runnable {
		Location fLoc;
		FireworkEffect effect;

		public PlayFireworkEffect(Location fLoc, FireworkEffect fEffect) {
			this.fLoc = fLoc;
			this.effect = fEffect;
		}

		@Override
		public void run() {
			try {
				FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(fLoc.getWorld(), fLoc, effect);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
