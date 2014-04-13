package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SignManager {

	private Map<Location, String> signs = new HashMap<Location, String>();
	private FileConfiguration config;

	public SignManager() {

		SGApi.getPlugin().saveResource("signs.yml", false);

		config = YamlConfiguration.loadConfiguration(new File(SGApi.getPlugin().getDataFolder(), "signs.yml"));
		ConfigurationSection signConfig = config.getConfigurationSection("signs");
		if (signConfig == null) {
			return;
		}
		for (String s : signConfig.getKeys(false)) {
			ConfigurationSection currentSign = signConfig.getConfigurationSection(s);
			Location loc = SGApi.getArenaManager().deserializeLoc(currentSign.getString("loc"));
			signs.put(loc, s);
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (Location loc : signs.keySet()) {
					if(!(loc.getBlock().getState() instanceof Sign))
						continue;
					Sign sign = (Sign) loc.getBlock().getState();
					SGArena arena = null;
					// LINE 1
					try {
						arena = SGApi.getArenaManager().getArena(Integer.parseInt(signs.get(loc)));
					} catch (NumberFormatException e) {
						Bukkit.getLogger().severe("Number format exception");
						continue;
					} catch (ArenaNotFoundException e) {
						Bukkit.getLogger().severe("Arena not found exception");
						continue;
					}
					if (arena.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || arena.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
						sign.setLine(0, ChatColor.GREEN + "[Join]");
					} else if (arena.getState().equals(SGArena.ArenaState.IN_GAME) || arena.getState().equals(SGArena.ArenaState.STARTING_COUNTDOWN)) {
						sign.setLine(0, ChatColor.YELLOW + "[Spectate]");
					} else if (arena.getState().equals(SGArena.ArenaState.POST_GAME) || arena.getState().equals(SGArena.ArenaState.DEATHMATCH)) {
						sign.setLine(0, ChatColor.RED + "[NotJoinable]");
					}

					//LINE 2
					sign.setLine(1, "Arena: " + arena.getId());

					//LINE 3
					sign.setLine(2, arena.getPlayers().size() + "/" + arena.getMaxPlayers());

					//LINE 4
					if (arena.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || arena.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
						sign.setLine(3, "Voting for Map");
					} else {
						sign.setLine(3, arena.getCurrentMap().getDisplayName());
					}
					sign.update();
				}
			}
		}, 20L, 20L);
	}

	public void addSign(Location sign, int arenaId) {
		signs.put(sign, arenaId + "");
		config.set("signs." + arenaId + ".loc", SGApi.getArenaManager().serializeLoc(sign));
		try {
			config.save(new File(SGApi.getPlugin().getDataFolder(), "signs.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Location, String> getSigns() {
		return signs;
	}
}
