/**
 * Name: TheSurvivalGames.java Created: 19 November 2013 Edited: 9 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.communitysurvivalgames.thesurvivalgames.ability.Archer;
import com.communitysurvivalgames.thesurvivalgames.ability.Crafter;
import com.communitysurvivalgames.thesurvivalgames.ability.Enchanter;
import com.communitysurvivalgames.thesurvivalgames.ability.Knight;
import com.communitysurvivalgames.thesurvivalgames.ability.Notch;
import com.communitysurvivalgames.thesurvivalgames.ability.Pacman;
import com.communitysurvivalgames.thesurvivalgames.ability.Pig;
import com.communitysurvivalgames.thesurvivalgames.ability.Skeleton;
import com.communitysurvivalgames.thesurvivalgames.ability.Toxicologist;
import com.communitysurvivalgames.thesurvivalgames.ability.Zelda;
import com.communitysurvivalgames.thesurvivalgames.command.CommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.PartyCommandHandler;
import com.communitysurvivalgames.thesurvivalgames.command.standalone.SponsorCommand;
import com.communitysurvivalgames.thesurvivalgames.command.standalone.TpxCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ChatCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.DeclineCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.InviteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.ListCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.PromoteCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.CreateCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.RemoveCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.RemoveKitSelectionLocationCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.SetKitSelectionLocationCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StartCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.StopCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.TestCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.UserCommand;
import com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.VoteCommand;
import com.communitysurvivalgames.thesurvivalgames.configs.ArenaConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.ConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.ManagerConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.WorldConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.kits.KitItem;
import com.communitysurvivalgames.thesurvivalgames.listeners.BlockListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ChatListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ChestListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityDamageListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.EntityInteractListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.ItemDropListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.MobSpawnListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.PlayerLoginListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.PlayerQuitListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SetupListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SignListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SoundEffectsListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ArenaManager;
import com.communitysurvivalgames.thesurvivalgames.managers.ItemManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.net.WebsocketServer;
import com.communitysurvivalgames.thesurvivalgames.objects.PlayerData;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.proxy.BungeecordListener;
import com.communitysurvivalgames.thesurvivalgames.rollback.ChangedBlock;
import com.communitysurvivalgames.thesurvivalgames.runnables.Scoreboard;
import com.communitysurvivalgames.thesurvivalgames.tracking.AnalyticsConfigData;
import com.communitysurvivalgames.thesurvivalgames.tracking.JGoogleAnalyticsTracker;
import com.communitysurvivalgames.thesurvivalgames.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import com.communitysurvivalgames.thesurvivalgames.util.DoubleJump;
import com.communitysurvivalgames.thesurvivalgames.util.LocationChecker;
import com.communitysurvivalgames.thesurvivalgames.util.SerializedLocation;
import com.communitysurvivalgames.thesurvivalgames.util.ThrowableSpawnEggs;
import com.communitysurvivalgames.thesurvivalgames.util.items.CarePackage;
import com.communitysurvivalgames.thesurvivalgames.util.items.RailGun;

public class TheSurvivalGames extends JavaPlugin {

	private ConfigurationData configurationData;
	private Economy econ = null;
	private Chat chat = null;
	private JGoogleAnalyticsTracker tracker;

	@Override
	public void onEnable() {
		ConfigurationSerialization.registerClass(SerializedLocation.class);
		ConfigurationSerialization.registerClass(LocationChecker.class);
		ConfigurationSerialization.registerClass(KitItem.class);

		SGApi.init(this);

		saveDefaultConfig();

		configurationData = new ConfigurationData();

		if (!configurationData.isBungeecordMode() && configurationData.isHub()) {
			Bukkit.getLogger().severe("How do you expect to have a hub server if you're not even running on Bungeecord Mode?");
			getServer().getPluginManager().disablePlugin(this);
		}

		new File(getDataFolder(), "maps").mkdirs();
		new File(getDataFolder(), "arenas").mkdirs();

		SGApi.getScheduler();
		SGApi.getSignManager();

		// TODO Add more languages!
		saveResource("locale/enUS.lang", true);
		saveResource("locale/idID.lang", true);
		saveResource("locale/esES.lang", true);

		setupEconomy();
		setupChat();
		setupDatabase();

		File i18N = new File(getDataFolder(), "locale/I18N.yml");
		if (!i18N.exists()) {
			saveResource("locale/I18N.yml", false);
		}

		FileConfiguration lang = YamlConfiguration.loadConfiguration(i18N);

		I18N.setupLocale();
		I18N.setLocale(lang.getString("language"));

		if (getPluginConfig().isBungeecordMode()) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
			getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeecordListener());
		}

		registerAll();

		saveResource("ArenaManager.yml", false);
		ConfigTemplate<ArenaManager> configTemplate = new ManagerConfigTemplate();
		configTemplate.deserialize();

		if (!getPluginConfig().isHub())
			SGApi.getArenaManager().loadGames();

		if (getPluginConfig().getUseServers()) {
			try {
				WebsocketServer.runServer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		AnalyticsConfigData config = new AnalyticsConfigData("UA-49716599-1");
		config.setUserAgent("Java/" + System.getProperty("java.version") + " : Bukkit/" + Bukkit.getVersion() + " (" + System.getProperty("os.name") + "; " + System.getProperty("os.arch") + ")");
		config.setFlashVersion("9.0 r24");
		tracker = new JGoogleAnalyticsTracker(config, GoogleAnalyticsVersion.V_4_7_2);
		tracker.setEnabled(true);
		tracker.trackEvent("Server Start", "Motd: " + ChatColor.stripColor(Bukkit.getMotd()) + ", Max Players: " + Bukkit.getMaxPlayers() + ", Version: " + Bukkit.getVersion() + " running on " + Bukkit.getBukkitVersion() + ", Java: " + System.getProperty("java.version"));
		
		getLogger().info(I18N.getLocaleString("BEEN_ENABLED"));
		getLogger().info(I18N.getLocaleString("COMMUNITY_PROJECT"));
	}

	@Override
	public void onDisable() {
		getLogger().info(I18N.getLocaleString("BEEN_DISABLED"));
		
		ConfigTemplate<ArenaManager> template = new ManagerConfigTemplate();
		template.serialize();

		for (SGArena arena : SGApi.getArenaManager().getArenas()) {
			List<ChangedBlock> data = arena.changedBlocks;

			for (int i = 0; i < data.size(); i++) {
				Bukkit.getLogger().info("Resetting block: " + data.get(i).getPrevid().toString());
				Location l = new Location(Bukkit.getWorld(data.get(i).getWorld()), data.get(i).getX(), data.get(i).getY(), data.get(i).getZ());
				Block b = l.getBlock();
				b.setType(data.get(i).getPrevid());
				b.setData(data.get(i).getPrevdata());
				b.getState().update();
			}
		}

		for (SGArena arena : SGApi.getArenaManager().getArenas()) {
			Bukkit.getLogger().info("Attemping to save arena: " + arena.toString());
			ConfigTemplate<SGArena> configTemplate = new ArenaConfigTemplate(arena);
			configTemplate.serialize();
		}

		for (SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
			Bukkit.getLogger().info("Attempting to save world: " + world);
			ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(world);
			configTemplate.serialize();
		}

		SGApi.getScheduler().shutdownAll();
	}

	void registerAll() {
		getCommand("sg").setExecutor(new CommandHandler());
		getCommand("party").setExecutor(new PartyCommandHandler());

		//I want the user based commands (ex. /kit /vote /sponsor) to not have the /sg prefix. Looks neater.   - Quantum64
		getCommand("sponsor").setExecutor(new SponsorCommand());
		getCommand("tpx").setExecutor(new TpxCommand());

		CommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.HelpCommand());
		CommandHandler.register("create", new CreateCommand());
		CommandHandler.register("remove", new RemoveCommand());
		CommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.JoinCommand());
		CommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg.LeaveCommand());
		CommandHandler.register("user", new UserCommand());
		CommandHandler.register("createlobby", new SetCommand());
		CommandHandler.register("setdeathmatch", new SetCommand());
		CommandHandler.register("setmaxplayers", new SetCommand());
		CommandHandler.register("setminplayers", new SetCommand());
		CommandHandler.register("setchest", new SetCommand());
		CommandHandler.register("setspawn", new SetCommand());
		CommandHandler.register("stop", new StopCommand());
		CommandHandler.register("start", new StartCommand());
		CommandHandler.register("finish", new CreateCommand());
		CommandHandler.register("vote", new VoteCommand());
		CommandHandler.register("test", new TestCommand());
		CommandHandler.register("setkitselectionlocation", new SetKitSelectionLocationCommand());
		CommandHandler.register("removekitselectionlocation", new RemoveKitSelectionLocationCommand());

		PartyCommandHandler.register("chat", new ChatCommand());
		PartyCommandHandler.register("decline", new DeclineCommand());
		PartyCommandHandler.register("help", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.HelpCommand());
		PartyCommandHandler.register("invite", new InviteCommand());
		PartyCommandHandler.register("join", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.JoinCommand());
		PartyCommandHandler.register("leave", new com.communitysurvivalgames.thesurvivalgames.command.subcommands.party.LeaveCommand());
		PartyCommandHandler.register("list", new ListCommand());
		PartyCommandHandler.register("promote", new PromoteCommand());

		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new CarePackage(this), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new SetupListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new DoubleJump(this), this);
		pm.registerEvents(new ItemDropListener(), this);
		pm.registerEvents(new ThrowableSpawnEggs(), this);
		pm.registerEvents(new EntityInteractListener(), this);
		pm.registerEvents(new SignListener(), this);
		pm.registerEvents(new ChestListener(), this);
		pm.registerEvents(new RailGun(), this);
		pm.registerEvents(new MobSpawnListener(), this);
		pm.registerEvents(new PlayerLoginListener(), this);
		pm.registerEvents(new SoundEffectsListener(), this);

		pm.registerEvents(new Archer(), this);
		pm.registerEvents(new Crafter(), this);
		pm.registerEvents(new Enchanter(), this);
		pm.registerEvents(new Knight(), this);
		pm.registerEvents(new Notch(), this);
		pm.registerEvents(new Pacman(), this);
		pm.registerEvents(new Pig(), this);
		pm.registerEvents(new Skeleton(), this);
		pm.registerEvents(new Toxicologist(), this);
		pm.registerEvents(new Zelda(), this);

		Scoreboard.registerScoreboard();
		ItemManager.register();
		
		SGApi.getEnchantmentManager().registerAll();

		SGApi.getKitManager().loadKits();
	}

	/**
	 * Setup Persistence Databases and Install DDL if there are none
	 */
	private void setupDatabase() {
		File ebean = new File(getDataFolder(), "ebean.properties");
		if (!ebean.exists()) {
			saveResource("ebean.properties", false);
		}
		try {
			getDatabase().find(PlayerData.class).findRowCount();
		} catch (PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}

		//SGApi.getSignManager().signs = getDatabase().find(JSign.class).findList();
	}

	/**
	 * Gets Persistence Database classes WARNING: DO NOT EDIT
	 * 
	 * @return The list of classes for the database
	 */
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<>();
		list.add(PlayerData.class);
		return list;
	}

	public PlayerData getPlayerData(Player player) {
		PlayerData data = getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
		if (data == null) {
			data = new PlayerData(player);
			setPlayerData(data);
		}
		return data;
	}

	public void setPlayerData(PlayerData data) {
		getDatabase().save(data);
	}

	public ConfigurationData getPluginConfig() {
		return configurationData;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			econ = economyProvider.getProvider();
		}

		return (econ != null);
	}

	public Economy getEcon() {
		return econ;
	}

	public Chat getChat() {
		return chat;
	}

	public boolean useEcon() {
		return (econ != null);
	}

	public boolean useChat() {
		return (chat != null);
	}

	public String getPrefix(Player p) {
		return chat.getPlayerPrefix(p);
	}

	public JGoogleAnalyticsTracker getTracker() {
		return tracker;
	}
}
