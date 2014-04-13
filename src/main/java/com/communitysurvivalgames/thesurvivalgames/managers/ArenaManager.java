/**
 * Name: ArenaManager.java Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.communitysurvivalgames.thesurvivalgames.configs.ArenaConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.ConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.WorldConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.io.DownloadMap;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.net.SendWebsocketData;
import com.communitysurvivalgames.thesurvivalgames.objects.MapHash;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.util.PlayerVanishUtil;

public class ArenaManager {

	public final String prefix = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.GOLD;
	public final String error = ChatColor.DARK_AQUA + "[TheSurvivalGames]" + ChatColor.RED;
	private final Map<String, SGWorld> creators = new HashMap<>();
	public final Map<String, Location> locs = new HashMap<>();
	public final Map<String, ItemStack[]> inv = new HashMap<>();
	public final Map<String, ItemStack[]> armor = new HashMap<>();
	private final List<SGArena> arenas = new ArrayList<>();

	/**
	 * The constructor for a new reference of the singleton
	 */
	public ArenaManager() {
	}

	/**
	 * Gets an arena from an integer ID
	 * 
	 * @param i The ID to get the Arena from
	 * @return The arena from which the ID represents. May be null.
	 * @throws ArenaNotFoundException
	 */
	public SGArena getArena(int i) throws ArenaNotFoundException {
		for (SGArena a : arenas) {
			if (a.getId() == i) {
				return a;
			}
		}
		throw new ArenaNotFoundException("Could not find given arena with given ID: " + i);
	}

	public SGArena getArena(Player p) throws ArenaNotFoundException {
		for (SGArena a : arenas) {
			if (a.getPlayers().contains(p.getName())) {
				return a;
			}
			if (a.getSpectators().contains(p.getName())) {
				return a;
			}
		}
		throw new ArenaNotFoundException("Could not find given arena with given Player: " + p.getDisplayName());
	}

	/**
	 * Adds a player to the specified arena
	 * 
	 * @param p The player to be added
	 * @param i The arena ID in which the player will be added to.
	 */
	public void addPlayer(Player p, int i) {
		SGArena a;
		try {
			a = getArena(i);
		} catch (ArenaNotFoundException e) {
			Bukkit.getLogger().severe(e.getMessage());
			return;
		}

		if (isInGame(p)) {
			p.sendMessage(error + I18N.getLocaleString("NOT_JOINABLE"));
			return;
		}

		if (!a.getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) && !a.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
			a.spectators.add(p.getName());
			p.teleport(a.getCurrentMap().locs.get(0));
			p.setGameMode(GameMode.CREATIVE);
			p.setCanPickupItems(false);
			p.setAllowFlight(true);
			p.setFlying(true);
			PlayerVanishUtil.hideAll(p);
			return;
		}
		if (a.getState().equals(SGArena.ArenaState.PRE_COUNTDOWN)) {
			//a.broadcastVotes();
			p.sendMessage(prefix + "Type in /sg vote <ID> to vote for a map.");
			ItemManager.instance.gem.givePlayerItem(p);
		}

		if (SGApi.getPlugin().getPluginConfig().getUseServers()) {
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
			
			//TODO Temp
		}

		a.getPlayers().add(p.getName());
		for (String s : a.players) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		for (String s : a.spectators) {
			Player player = Bukkit.getPlayer(s);
			SendWebsocketData.updateArenaStatusForPlayer(player);
		}
		inv.put(p.getName(), p.getInventory().getContents());
		armor.put(p.getName(), p.getInventory().getArmorContents());

		p.getInventory().setArmorContents(null);
		p.getInventory().clear();
		p.setExp(0);

		locs.put(p.getName(), p.getLocation());
		p.teleport(a.lobby);
		p.setExhaustion(0);
		p.setGameMode(GameMode.SURVIVAL);
		p.setFoodLevel(20);
		// Ding!
		for (Player player : SGApi.getPlugin().getServer().getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
		}
	
		SendWebsocketData.stopMusic(p);
		SendWebsocketData.playMusicToPlayer(p, SendWebsocketData.getRandomMusic("lobby-music"));

		if (a.getPlayers().size() == a.minPlayers && a.countdown == false) {
			a.countdown = true;
			SGApi.getTimeManager(a).countdownLobby(2);
		}
	}

	/**
	 * Removes the player from an arena
	 * 
	 * @param p The player to remove from an arena
	 */
	public void removePlayer(Player p) {
		PlayerVanishUtil.showAll(p);

		try {
			if (this.getArena(p).getState().equals(SGArena.ArenaState.PRE_COUNTDOWN) || this.getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS)) {
				SGArena a = getArena(p);
				a.getPlayers().remove(p.getName());
				if (a.players.size() < a.getMinPlayers())
					a.restart();
				if (a.getSpectators().contains(p.getName()))
					a.getSpectators().remove(p.getName());
				else {
					a.getPlayers().remove(p.getName());
				}
				p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
				p.setGameMode(GameMode.SURVIVAL);
				p.getActivePotionEffects().clear();
				p.setAllowFlight(false);
				p.setFlying(false);
				p.setCanPickupItems(true);
				p.setHealth(20);
				p.setFoodLevel(20);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				for (PotionEffect effect : p.getActivePotionEffects()) {
					p.removePotionEffect(effect.getType());
				}

				p.setFireTicks(0);
				return;
			}
		} catch (ArenaNotFoundException e) {
			p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
			p.setGameMode(GameMode.SURVIVAL);
			p.getActivePotionEffects().clear();
			p.setAllowFlight(false);
			p.setFlying(false);
			p.setCanPickupItems(true);
			p.setHealth(20);
			p.setFoodLevel(20);

			for (PotionEffect effect : p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}

			p.setFireTicks(0);
			SendWebsocketData.stopMusic(p);
		}
		SGArena a = null;
		for (SGArena arena : arenas) {
			if (arena.getPlayers().contains(p.getName()) || arena.getSpectators().contains(p.getName())) {
				a = arena;
			}
		}

		p.getInventory().clear();
		p.getInventory().setArmorContents(null);

		inv.remove(p.getName());
		armor.remove(p.getName());
		//p.teleport(locs.get(p.getName()));
		p.teleport(Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld()).getSpawnLocation());
		p.setGameMode(GameMode.SURVIVAL);
		p.getActivePotionEffects().clear();
		p.setAllowFlight(false);
		p.setFlying(false);
		p.setCanPickupItems(true);
		p.setHealth(20);
		p.setExhaustion(0);
		p.setFoodLevel(20);
		locs.remove(p.getName());

		for (PotionEffect effect : p.getActivePotionEffects()) {
			p.removePotionEffect(effect.getType());
		}

		p.setFireTicks(0);
		
		if(a == null)
			return;
		
		if (a.getSpectators().contains(p.getName()))
			a.getSpectators().remove(p.getName());
		else {
			a.getPlayers().remove(p.getName());
		}
		
		//p.getInventory().setContents(inv.get(p.getName()));
		//p.getInventory().setArmorContents(armor.get(p.getName()));
	}

	/**
	 * Player disconnects in game :/
	 * 
	 * We can't get the player's inventory here to shoot out the items.  Oh well.
	 */
	public void playerDisconnect(Player p) {
		try {
			SGApi.getArenaManager().getArena(p).deathWithQuit(p);
		} catch (ArenaNotFoundException e) {}
	}

	/**
	 * Creates a lobby
	 */
	public SGArena createLobby(Player p) {
		SGArena a = new SGArena();

		int s = arenas.size();
		s += 1;

		a.createArena(s);

		a.lobby = p.getLocation();

		arenas.add(a);

		a.restart();

		return a;
	}

	/**
	 * Creates a new arena
	 * 
	 * @param creator The creator attributed with making the arena
	 */
	public void createWorld(final Player creator, final String worldName, final String display) {
		creator.getInventory().addItem(new ItemStack(Material.BLAZE_ROD));

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				// todo this is only a temp solution to create a new map
				SGWorld world = SGApi.getMultiWorldManager().createWorld(worldName, display);
				creator.teleport(new Location(world.getWorld(), world.getWorld().getSpawnLocation().getX(), world.getWorld().getSpawnLocation().getY(), world.getWorld().getSpawnLocation().getZ()));
				creators.put(creator.getName(), world);
			}
		});

	}

	public void createWorldFromDownload(final Player creator, final String worldName, final String displayName) {

		new DownloadMap(creator, worldName).begin();

	}

	public void createWorldFromImport(final Player creator, final String worldName, final String displayName) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			@Override
			public void run() {
				SGApi.getMultiWorldManager().importWorldFromFolder(creator, worldName, displayName);
			}
		});

	}

	/**
	 * Removes an arena from memory
	 * 
	 * @param i The ID of the arena to be removed
	 */
	public void removeArena(int i) {
		SGArena a;
		try {
			a = getArena(i);
		} catch (ArenaNotFoundException e) {
			Bukkit.getLogger().severe(e.getMessage());
			return;
		}
		arenas.remove(a);
		new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/arenas/" + i + ".yml").delete();
	}

	/**
	 * Gets whether the player is playing
	 * 
	 * @param p The player that will be scanned
	 * @return Whether the player is in a game
	 */
	public boolean isInGame(Player p) {
		for (SGArena a : arenas) {
			if (a.getPlayers().contains(p.getName())) {
				return true;
			}

			if (a.getSpectators().contains(p.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads the game into memory after a shutdown or a relaod
	 */
	public void loadGames() {
		File arenas = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/arenas/");
		File maps = new File(SGApi.getPlugin().getDataFolder().getAbsolutePath() + "/maps/");

		if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
			if (arenas.listFiles().length > 1) {
				Bukkit.getLogger().severe("You cannot have mutiple arenas on Bngeecord mode");
				Bukkit.getPluginManager().disablePlugin(SGApi.getPlugin());
			}
		}

		if (maps.listFiles().length == 0)
			return;
		for (File file : maps.listFiles()) {
			ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(file);
			SGWorld world = configTemplate.deserialize();
			Bukkit.getLogger().info("Loaded map! " + world.toString());
			SGApi.getMultiWorldManager().getWorlds().add(world);
		}

		if (arenas.listFiles().length == 0)
			return;
		for (File file : arenas.listFiles()) {
			ConfigTemplate<SGArena> configTemplate = new ArenaConfigTemplate(file);
			SGArena arena = configTemplate.deserialize();
			Bukkit.getLogger().info("Loaded arena! " + arena.toString());
			this.arenas.add(arena);

			arena.restart();
		}
	}

	public void playerKilled(Player p, SGArena a) {
		a.death(p);
	}

	public void playerDeathAndLeave(Player p, SGArena a) {
		a.deathAndLeave(p);
	}

	/**
	 * Gets the HashMap that contains the creators
	 * 
	 * @return The HashMap of creators
	 */
	public Map<String, SGWorld> getCreators() {
		return creators;
	}

	/**
	 * Get the arenas
	 * 
	 * @return the ArrayList of arenas
	 */
	public List<SGArena> getArenas() {
		return arenas;
	}

	/**
	 * Serializes a location to a string
	 * 
	 * @param l The location to serialize
	 * @return The serialized location
	 */
	public String serializeLoc(Location l) {
		return l.getWorld().getName() + "," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ();
	}

	public String serializeBlock(Block b) {
		return b.getType() + ":" + serializeLoc(b.getLocation());
	}

	/**
	 * Gets a location from a string
	 * 
	 * @param s The string to deserialize
	 * @return The location represented from the string
	 */
	public Location deserializeLoc(String s) {
		String[] st = s.split(",");
		return new Location(Bukkit.getWorld(st[0]), Integer.parseInt(st[1]), Integer.parseInt(st[2]), Integer.parseInt(st[3]));
	}

	public Block deserializeBlock(String st) {
		String[] s = st.split(":");
		return deserializeLoc(serializeLoc(new Location(Bukkit.getServer().getWorld(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Integer.parseInt(s[4])))).getBlock();
	}
}
