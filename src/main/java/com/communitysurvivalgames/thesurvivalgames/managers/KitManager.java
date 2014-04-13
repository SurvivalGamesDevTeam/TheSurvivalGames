package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.communitysurvivalgames.thesurvivalgames.event.KitGivenEvent;
import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import com.communitysurvivalgames.thesurvivalgames.util.IconMenu;
import com.communitysurvivalgames.thesurvivalgames.util.ItemSerialization;

public class KitManager {
	private List<Kit> kits = new ArrayList<Kit>();
	private List<IconMenu> menus = new ArrayList<IconMenu>();
	private Map<String, Kit> playerKits = new HashMap<String, Kit>();

	public void loadKits() {

		saveDefaultKits();
		readKitsFromFiles();

		for (int i = 0; i < 9; i++) {
			menus.add(new IconMenu("Select Your Kit - " + i, 54, false, new IconMenu.OptionClickEventHandler() {
				@Override
				public void onOptionClick(IconMenu.OptionClickEvent event) {
					if (event.getName().startsWith("Page")) {
						displayKitSelectionMenu(event.getPlayer(), (Integer.parseInt(event.getName().charAt(5) + "") - 1));
						return;
					}
					if (event.getPlayer().hasPermission("sg.kits.*") || event.getPlayer().hasPermission("sg.kits." + event.getName()) || event.getPlayer().isOp()) {
						event.getPlayer().sendMessage("You have chosen the " + event.getName() + " kit!");
						setPlayerKit(event.getPlayer(), getKit(event.getName()));
						event.setWillClose(true);
						return;
					}
					event.setWillClose(true);
					event.getPlayer().sendMessage(ChatColor.RED + "Sorry, but you do not have permission to use this kit!");

				}
			}, SGApi.getPlugin()));
		}
		//TODO Is this really the best way to go about this?
		int menu = 0;
		int row = 0;
		int index = 0;
		String currentC = kits.get(0).getType();

		for (Kit k : kits) {
			if (!currentC.equalsIgnoreCase(k.getType()) || index == 8) {
				currentC = k.getType();
				index = 0;
				row++;
				if (row == 5) {
					row = 0;
					menu++;
					if (menu == 9) {
						Bukkit.getLogger().severe("You can't have more that 486 kits!  (Are you insane?)");
					}
				}
			}

			menus.get(menu).setOption((row * 9) + index, k.getIcon(), k.getName(), k.getIconLore());
			index++;

			//kits1.setOption(index, k.getIcon(), k.getName(), k.getIconLore());
			//index++;
		}

		for (int i = 0; i < 9; i++) {
			menus.get(i).setOption(45, new ItemStack(Material.PAPER), "Page 1", "Click to go to page 1!");
			menus.get(i).setOption(46, new ItemStack(Material.PAPER), "Page 2", "Click to go to page 2!");
			menus.get(i).setOption(47, new ItemStack(Material.PAPER), "Page 3", "Click to go to page 3!");
			menus.get(i).setOption(48, new ItemStack(Material.PAPER), "Page 4", "Click to go to page 4!");
			menus.get(i).setOption(49, new ItemStack(Material.PAPER), "Page 5", "Click to go to page 5!");
			menus.get(i).setOption(50, new ItemStack(Material.PAPER), "Page 6", "Click to go to page 6!");
			menus.get(i).setOption(51, new ItemStack(Material.PAPER), "Page 7", "Click to go to page 7!");
			menus.get(i).setOption(52, new ItemStack(Material.PAPER), "Page 8", "Click to go to page 8!");
			menus.get(i).setOption(53, new ItemStack(Material.PAPER), "Page 9", "Click to go to page 9!");
		}
	}

	public void readKitsFromFiles() {
		String[] files = new File(SGApi.getPlugin().getDataFolder(), "kits").list();

		for (String file : files) {
			if (file.startsWith("kit_")) {
				FileConfiguration kitData = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), "kits/" + file));

				String kitName = kitData.getString("name");
				String type = kitData.getString("type");
				String[] iconU = kitData.getString("icon").split(":");
				ItemStack icon = null;
				if (iconU.length > 1) {
					if (iconU[0].equalsIgnoreCase("@p")) {
						icon = new ItemStack(Material.POTION);
						Potion potion = new Potion(1);
						potion.setType(PotionType.valueOf(iconU[1]));
						potion.setSplash(Boolean.valueOf(iconU[2]));
					}
				} else {
					icon = new ItemStack(Material.getMaterial(iconU[0]));
				}
				String iconLore = kitData.getString("iconLore");
				String serializedInventory = kitData.getString("items.lvl1.inventory");

				Bukkit.getServer().getLogger().info("Attempting to read inventory string of " + kitName + ". If it errors here, its a problem with this kit.");

				Inventory inventory = ItemSerialization.stringToInventory(serializedInventory); // TODO Not a temp solution, this is awesome!
				List<KitItem> list = new ArrayList<>();
				for (ItemStack itemStack : inventory) {
					KitItem ki = new KitItem();
					ki.setItem(itemStack);
					list.add(ki);
				}

				List<Integer> abilityIds = kitData.getIntegerList("ability-ids");

				kits.add(new Kit(kitName, type, list, icon, iconLore, abilityIds));
			}
		}

		Bukkit.getLogger().info("Sorted: " + kits);

		Collections.sort(kits, new Comparator<Kit>() {
			public int compare(Kit o1, Kit o2) {
				return o1.getType().compareTo(o2.getType());
			}
		});

		Bukkit.getLogger().info("Into: " + kits);
	}

	void saveDefaultKits() {
		SGApi.getPlugin().saveResource("kits/kit_archer.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_crafter.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_enchanter.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_knight.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_notch.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_pacman.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_pig.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_skeleton.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_toxicologist.yml", true);
		SGApi.getPlugin().saveResource("kits/kit_zelda.yml", true);
	}

	public Kit getKit(String name) {
		for (Kit k : kits) {
			if (k.getName().equalsIgnoreCase(name))
				return k;
		}
		return kits.get(0);
	}

	public Kit getKit(Player p) {
		return playerKits.get(p.getName());
	}

	public List<Kit> getKits() {
		return kits;
	}

	public void displayDefaultKitSelectionMenu(Player p) {
		menus.get(0).open(p);
	}

	public void displayKitSelectionMenu(final Player p, final int i) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				menus.get(i).open(p);

			}
		}, 2L);
	}

	public void setPlayerKit(Player player, Kit kit) {
		Bukkit.getServer().getPluginManager().callEvent(new KitGivenEvent(player, kit));
		playerKits.put(player.getName(), kit);
	}

	public void giveKit(Player p) {
		Kit kit = playerKits.get(p.getName());
		if (kit == null) {
			p.sendMessage(ChatColor.RED + "You did not select a kit so no kit will be given to you.");
			return;
		}
		p.sendMessage(ChatColor.GOLD + "You got your kit!");
		for (KitItem item : kit.getItems()) {
			p.getInventory().addItem(item.getItem());
		}
	}

}
