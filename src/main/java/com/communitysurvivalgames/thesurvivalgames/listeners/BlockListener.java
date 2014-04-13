/**
 * Name: BlockListener.java 
 * Created: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.rollback.ChangedBlock;

public class BlockListener implements Listener {

	final List<Material> allowed;
	static List<Block> breakable;

	public BlockListener() {

		allowed = new ArrayList<Material>();
		breakable = new ArrayList<Block>();
		for (String s : SGApi.getPlugin().getPluginConfig().getAllowedBlockBreaks()) {
			allowed.add(Material.valueOf(s));
		}
	}

	public static void addBreakable(Block block) {
		breakable.add(block);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		if (SGApi.getArenaManager().isInGame(event.getPlayer())) {
			if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
				event.setCancelled(true);
				return;
			}
			if (event.getBlock().getType().equals(Material.TNT)) {
				event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
				ItemStack item = event.getPlayer().getInventory().getItemInHand();
				int amount = item.getAmount() - 1;
				item.setAmount(amount);
				event.getPlayer().getInventory().setItemInHand(item);
			}
			event.setCancelled(true);
		}

		if (Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()) == event.getPlayer().getWorld() && !event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		try {
			SGArena a = SGApi.getArenaManager().getArena(event.getPlayer());
			if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
				event.setCancelled(true);
				return;
			}
			if (allowed.contains(event.getBlock().getType())) {
				a.changedBlocks.add(new ChangedBlock(event.getBlock().getWorld().getName(), event.getBlock().getType(), event.getBlock().getData(), Material.AIR, Byte.parseByte(0 + ""), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ()));
			} else {
				event.setCancelled(true);
			}
			return;
		} catch (ArenaNotFoundException ignored) {}

		if (Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()) == event.getPlayer().getWorld() && !event.getPlayer().isOp()) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
			event.blockList().clear();
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			if (SGApi.getArenaManager().isInGame((Player) event.getEntity())) {
				event.setCancelled(true);
			}
		}
	}
}
