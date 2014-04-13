package com.communitysurvivalgames.thesurvivalgames.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class IconMenu implements Listener {

	private String name;
	private int size;
	private OptionClickEventHandler handler;
	private Plugin plugin;
	private boolean update;

	private Map<String, Integer> tasks = new HashMap<String, Integer>();

	private String[] optionNames;
	private ItemStack[] optionIcons;

	public IconMenu(String name, int size, boolean update, OptionClickEventHandler handler, Plugin plugin) {
		this.name = name;
		this.size = size;
		this.handler = handler;
		this.plugin = plugin;
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
		this.update = update;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
		optionNames[position] = name;
		optionIcons[position] = setItemNameAndLore(icon, name, info);
		return this;
	}

	public void clearOptions() {
		for (int i = 0; i < optionNames.length; i++) {
			this.optionNames = new String[size];
			this.optionIcons = new ItemStack[size];
		}
	}

	public void open(Player player) {
		final Inventory inventory = Bukkit.createInventory(player, size, name);
		for (int i = 0; i < optionIcons.length; i++) {
			if (optionIcons[i] != null) {
				inventory.setItem(i, optionIcons[i]);
			}
		}
		player.openInventory(inventory);
		if (update) {
			tasks.put(player.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < optionIcons.length; i++) {
						if (optionIcons[i] != null) {
							inventory.setItem(i, optionIcons[i]);
						}
					}
				}
			}, 20L, 20L));
		}
	}

	public void destroy() {
		HandlerList.unregisterAll(this);
		handler = null;
		plugin = null;
		optionNames = null;
		optionIcons = null;
	}

	public void clear() {
		this.optionNames = new String[size];
		this.optionIcons = new ItemStack[size];
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			event.setCancelled(true);
			int slot = event.getRawSlot();
			if (slot >= 0 && slot < size && optionNames[slot] != null) {
				Plugin plugin = this.plugin;
				OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot], optionIcons[slot]);
				handler.onOptionClick(e);
				if (e.willClose()) {
					final Player p = (Player) event.getWhoClicked();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							p.closeInventory();
						}
					}, 1);
				}
				if (e.willDestroy()) {
					destroy();
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getTitle().equals(name)) {
			if (update) {
				Bukkit.getScheduler().cancelTask(tasks.get(event.getPlayer().getName()));
				tasks.remove(event.getPlayer().getName());
			}
		}
	}

	public interface OptionClickEventHandler {
		public void onOptionClick(OptionClickEvent event);
	}

	public class OptionClickEvent {
		private Player player;
		private int position;
		private String name;
		private ItemStack item;
		private boolean close;
		private boolean destroy;

		public OptionClickEvent(Player player, int position, String name, ItemStack item) {
			this.player = player;
			this.position = position;
			this.name = name;
			this.item = item;
			this.close = true;
			this.destroy = false;
		}

		public Player getPlayer() {
			return player;
		}

		public int getPosition() {
			return position;
		}

		public String getName() {
			return name;
		}

		public ItemStack getItem() {
			return item;
		}

		public boolean willClose() {
			return close;
		}

		public boolean willDestroy() {
			return destroy;
		}

		public void setWillClose(boolean close) {
			this.close = close;
		}

		public void setWillDestroy(boolean destroy) {
			this.destroy = destroy;
		}
	}

	private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
		if (item.getItemMeta() instanceof SkullMeta) {
			return item;
		}
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		return item;
	}
}