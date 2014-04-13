package com.communitysurvivalgames.thesurvivalgames.util;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {

	private final TheSurvivalGames plugin;

	public DoubleJump(TheSurvivalGames plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getCause() == EntityDamageEvent.DamageCause.FALL)) {
			if (SGApi.getArenaManager().isInGame((Player) e.getEntity())) {
				if (plugin.getPluginConfig().allowDoubleJumpIG())
					e.setCancelled(true);
				return;
			}
			if (plugin.getPluginConfig().allowDoubleJump()) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if ((event.getPlayer().getGameMode() != GameMode.CREATIVE) && (event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			if (SGApi.getArenaManager().isInGame(event.getPlayer())) {
				if (plugin.getPluginConfig().allowDoubleJumpIG()) {
					event.getPlayer().setAllowFlight(true);
				} else {
					event.getPlayer().setAllowFlight(false);
				}
				return;
			}
			if (plugin.getPluginConfig().allowDoubleJump()) {
				event.getPlayer().setAllowFlight(true);
			} else {
				event.getPlayer().setAllowFlight(false);
			}
		}
	}

	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if ((player.getGameMode() != GameMode.CREATIVE)) {
			event.setCancelled(true);
			player.setAllowFlight(false);
			player.setFlying(false);
			player.setVelocity(player.getLocation().getDirection().multiply(1.6D).setY(1.0D));
			player.getLocation().getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, -10.0F);
		}
	}

}
