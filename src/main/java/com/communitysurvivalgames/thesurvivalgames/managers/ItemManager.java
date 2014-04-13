package com.communitysurvivalgames.thesurvivalgames.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.communitysurvivalgames.thesurvivalgames.util.player.items.SGItem;
import com.communitysurvivalgames.thesurvivalgames.util.player.items.ce.SingleExecutor;

public class ItemManager implements Listener {

	public static ItemManager instance;

	public SGItem clock;
	public SGItem compass;
	public SGItem gem;
	public SGItem star;

	public ItemManager() {

		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta meta = emerald.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Click to vote for a map");
		emerald.setItemMeta(meta);

		ItemStack compassItem = new ItemStack(Material.COMPASS);
		ItemMeta compassmeta = compassItem.getItemMeta();
		compassmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Click to join a SG game");
		compassItem.setItemMeta(compassmeta);

		ItemStack clockItem = new ItemStack(Material.WATCH);
		ItemMeta clockmeta = clockItem.getItemMeta();
		clockmeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Click to connect to the soundserver");
		clockItem.setItemMeta(clockmeta);
		
		ItemStack starItem = new ItemStack(Material.NETHER_STAR);
		ItemMeta starmeta = starItem.getItemMeta();
		starmeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Click to spectate a player");
		starItem.setItemMeta(starmeta);
		
		clock = new SGItem(clockItem, 8, true, false, new SingleExecutor() {

			@Override
			public void use(Player p) {
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Want to here LIVE music, announcers, and sound effects?");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Click this link:");
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.UNDERLINE + "http://communitysurvivalgames.com/sg/index.html?name=" + p.getName() + "&session=" + SGApi.getPlugin().getPluginConfig().getServerIP());
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Simply leave your browser window open in the background, turn up your speakers, and we'll do the rest!");
				p.sendMessage(ChatColor.AQUA + "");
				p.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮■▮");

			}
		});

		compass = new SGItem(compassItem, 0, true, false, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MeunManager.getMenuManager().displayJoinMenu(player);
			}
		});

		gem = new SGItem(emerald, 0, true, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MeunManager.getMenuManager().displayVoteMenu(player);
			}
		});
		
		star = new SGItem(starItem, 0, false, true, new SingleExecutor() {

			@Override
			public void use(Player player) {
				MeunManager.getMenuManager().displaySpecMenu(player);
			}
		});
	}

	public static void register() {
		instance = new ItemManager();
		SGApi.getPlugin().getServer().getPluginManager().registerEvents(instance, SGApi.getPlugin());
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldChange(final PlayerChangedWorldEvent event) {
		if (event.getPlayer().getWorld().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
			event.getPlayer().getInventory().clear();
			clock.givePlayerItem(event.getPlayer());
			compass.givePlayerItem(event.getPlayer());
		}
	}

	public void onPlayerJoin(final PlayerJoinEvent event) {
		Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (event.getPlayer().getWorld().getName().equals(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()))) {
					event.getPlayer().getInventory().clear();
					clock.givePlayerItem(event.getPlayer());
					compass.givePlayerItem(event.getPlayer());
				}
			}

		}, 10L);

	}
}
