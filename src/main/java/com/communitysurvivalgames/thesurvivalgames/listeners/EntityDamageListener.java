/**
 * Name: EntityDamageListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.enchantment.ShockingEnchantment;
import com.communitysurvivalgames.thesurvivalgames.event.PlayerKilledEvent;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.ItemManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.net.SendWebsocketData;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.util.DeathMessages;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;
import com.communitysurvivalgames.thesurvivalgames.util.PlayerVanishUtil;

public class EntityDamageListener implements Listener {

	/**
	 * Listens for a player being hit by a snow ball, gives player Slowness II
	 * for 30 seconds
	 * 
	 * @param event - The EntityDamageByEntityEvent event
	 */

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHungerChange(FoodLevelChangeEvent event) {
		if (event.getEntity().getWorld() == Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld())) {
			if (event.getEntity() instanceof Player) {
				Player p = (Player) event.getEntity();
				p.setExhaustion(0);
				p.setFoodLevel(20);
				return;
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof EnderCrystal) {
			event.setCancelled(true);
			return;
		}

		if (event.getEntity().getWorld() == Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld())) {
			event.setCancelled(true);
			return;
		}

		if (SGApi.getPlugin().getPluginConfig().doBloodEffect()) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(TheSurvivalGames.getPlugin(TheSurvivalGames.class), new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < event.getDamage(); i++) {
						event.getDamager().getWorld().playEffect(event.getEntity().getLocation().add(0.0D, 0.8D, 0.0D), Effect.STEP_SOUND, Material.REDSTONE_WIRE);
					}
				}
			});
		}

		Entity entity = event.getDamager();
		if (entity instanceof Player) {
			Player damager = (Player) entity;
			if (damager.getItemInHand().containsEnchantment(new ShockingEnchantment(120))) {
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.BLACK).withFade(Color.RED).with(Type.BALL).trail(true).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getEntity().getWorld(), event.getEntity().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
				Vector v = event.getEntity().getVelocity();
				event.getEntity().setVelocity(v.add(new Vector(0, Math.abs(v.getY() - (v.getY() - 0.15)), 0)));
			}
		}
		if (event.getEntity() instanceof Player) {
			Player damaged = (Player) event.getEntity();

			Entity eentity = event.getDamager();
			if (eentity instanceof Player) {
				Player damager = (Player) eentity;
				try {

					if (SGApi.getArenaManager().getArena(damaged).spectators.contains(damager.getName())) {
						event.setCancelled(true);
						return;
					}
					if (SGApi.getTimeManager(SGApi.getArenaManager().getArena(damaged)).gameTime.count > 30) {
						if (!SendWebsocketData.music.containsKey(damaged.getName()) || SendWebsocketData.music.get(damaged.getName()).equalsIgnoreCase("ambient")) {
							SendWebsocketData.music.put(damaged.getName(), "battle");
							SendWebsocketData.playMusicToPlayer(damaged, SendWebsocketData.getRandomMusic("battle-music"));
						}
						if (!SendWebsocketData.music.containsKey(damaged.getName()) || SendWebsocketData.music.get(damaged.getName()).equalsIgnoreCase("ambient")) {
							SendWebsocketData.music.put(damager.getName(), "battle");
							SendWebsocketData.playMusicToPlayer(damaged, SendWebsocketData.getRandomMusic("battle-music"));
						}
					}
				} catch (ArenaNotFoundException e1) {}
			}
			if (SGApi.getArenaManager().isInGame(damaged)) {
				if (entity instanceof Snowball) {
					event.setDamage(3);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 2, false));
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, false));
				}
				if (entity instanceof Egg) {
					event.setDamage(16);
					damaged.getWorld().strikeLightning(damaged.getLocation());
					damaged.getWorld().strikeLightning(damaged.getLocation());
				}

				if ((damaged.getHealth() - event.getDamage()) <= 0) {
					event.setCancelled(true);
					killPlayer(damaged, event.getDamager(), event.getCause());
				}
				return;
			}
			if ((damaged.getHealth() - event.getDamage()) <= 0) {
				event.setCancelled(true);
				damaged.setHealth(20);
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false));
				damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1, false));
				damaged.setVelocity(new Vector(0, 0, 0.5));
				for (int i = 0; i < 4; i++)
					fireworkIt(event.getDamager().getLocation());
				TheSurvivalGames.getPlugin(TheSurvivalGames.class).getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("FAIL") + " &e&l" + event.getDamager()));
			}
		}
		return;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.CUSTOM)
			return;

		if (event.getEntity().getWorld() == Bukkit.getWorld(SGApi.getPlugin().getPluginConfig().getHubWorld())) {
			event.setCancelled(true);
			return;
		}

		if (event.getEntity() instanceof Player) {
			if (SGApi.getArenaManager().isInGame((Player) event.getEntity())) {
				try {
					if (SGApi.getArenaManager().getArena((Player) event.getEntity()).getState() == SGArena.ArenaState.WAITING_FOR_PLAYERS || SGApi.getArenaManager().getArena((Player) event.getEntity()).getState() == SGArena.ArenaState.STARTING_COUNTDOWN) {
						event.setCancelled(true);
						return;
					}
				} catch (ArenaNotFoundException ignored) {}
			}
		}

		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player damaged = (Player) event.getEntity();
			if ((damaged.getHealth() - event.getDamage()) <= 0) {
				if (SGApi.getArenaManager().isInGame(damaged)) {
					killPlayer(damaged, null, event.getCause());
				} else {
					event.setCancelled(true);
					damaged.setHealth(20);
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false));
					damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 40, 1, false));
					damaged.setVelocity(new Vector(0, 0, 0.5));
					for (int i = 0; i < 4; i++)
						fireworkIt(damaged.getLocation());
					TheSurvivalGames.getPlugin(TheSurvivalGames.class).getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("FAIL") + " &e&l" + event.getCause().toString()));
				}
				event.setCancelled(true);

			}
		}
	}

	public void killPlayer(Player damaged, Entity entity, DamageCause dc) {

		Bukkit.getServer().getPluginManager().callEvent(new PlayerKilledEvent(damaged, entity));

		for (int i = 0; i < 4; i++)
			fireworkIt(damaged.getLocation());

		PlayerVanishUtil.hideAll(damaged);

		if (entity != null) {
			if (entity instanceof Player) {
				Player damager = (Player) entity;
				try {
					boolean shouldWaitDamager = false;
					boolean shouldWaitDamaged = false;
					if (SendWebsocketData.music.containsKey(damaged.getName())) {
						SendWebsocketData.music.remove(damaged.getName());
						SendWebsocketData.playToPlayer(damaged, "stop");
						SendWebsocketData.playToPlayer(damaged, "stinger" + new Random().nextInt(7));
						shouldWaitDamaged = true;
					}
					if (SendWebsocketData.music.containsKey(damager.getName())) {
						SendWebsocketData.music.remove(damager.getName());
						SendWebsocketData.playToPlayer(damager, "stop");
						SendWebsocketData.playToPlayer(damager, "stinger" + new Random().nextInt(7));
						shouldWaitDamager = true;
					}
					if (SGApi.getPlugin().getPluginConfig().getUseServers()) {
						if (!SGApi.getArenaManager().getArena(damager).firstBlood) {
							SGApi.getArenaManager().getArena(damager).firstBlood = true;
							SendWebsocketData.playToArena(SGApi.getArenaManager().getArena(damager), "firstblood");
						} else {
							if (shouldWaitDamaged) {
								Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

									@Override
									public void run() {

									}
								}, 20L);
							} else {

							}
							if (shouldWaitDamager) {
								Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

									@Override
									public void run() {

									}
								}, 20L);
							} else {

							}
						}
					}
					if (damager.getInventory().getItemInHand() != null) {
						String name = damager.getInventory().getItemInHand().getItemMeta().hasDisplayName() ? damager.getInventory().getItemInHand().getItemMeta().getDisplayName() : damager.getInventory().getItemInHand().getType().toString().replace("_", " ").toLowerCase();
						SGApi.getArenaManager().getArena(damager).broadcast(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("KILLED_BY") + " &e&l" + damager.getDisplayName() + " &r&6" + I18N.getLocaleString("WITH_A") + " &e&l" + name));
					} else {
						SGApi.getArenaManager().getArena(damager).broadcast(ChatColor.translateAlternateColorCodes('&', "&e&l" + damaged.getDisplayName() + " &r&6" + I18N.getLocaleString("KILLED_BY") + " &e&l" + damager.getDisplayName() + " &r&6" + I18N.getLocaleString("WITH_A") + " &e&l" + "fist"));
					}
					SGApi.getArenaManager().getArena(damager).addKill(damager);
				} catch (ArenaNotFoundException e) {
					e.printStackTrace();
				}
			}

		} else {
			String message = DeathMessages.getDeathMessage(damaged, dc);
			try {
				SGApi.getArenaManager().getArena(damaged).broadcast(message);
			} catch (ArenaNotFoundException e) {}
		}

		damaged.setHealth(20);
		damaged.setVelocity(new Vector(0, 0, 0.5));
		damaged.setGameMode(GameMode.CREATIVE);
		damaged.setAllowFlight(true);
		damaged.setFlying(true);
		damaged.setCanPickupItems(false);
		
		try {
			SGApi.getArenaManager().playerKilled(damaged, SGApi.getArenaManager().getArena(damaged));
		} catch (ArenaNotFoundException e) {}
		for (ItemStack is : damaged.getInventory().getContents()) {
			if (is == null || is.getType() == Material.AIR)
				continue;
			damaged.getWorld().dropItem(damaged.getLocation(), is);
		}
		for (ItemStack is : damaged.getInventory().getArmorContents()) {
			if (is == null || is.getType() == Material.AIR)
				continue;
			damaged.getWorld().dropItem(damaged.getLocation(), is);
		}
		damaged.getInventory().clear();
		damaged.getInventory().setArmorContents(null);
		ItemManager.instance.star.givePlayerItem(damaged);
	}

	public void fireworkIt(Location loc) {

		// Spawn the Firework, get the FireworkMeta.
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		// Our random generator
		Random r = new Random();

		// Get the type
		int rt = r.nextInt(5) + 1;
		Type type = Type.BALL;
		if (rt == 1)
			type = Type.BALL;
		if (rt == 2)
			type = Type.BALL_LARGE;
		if (rt == 3)
			type = Type.BURST;
		if (rt == 4)
			type = Type.CREEPER;
		if (rt == 5)
			type = Type.STAR;

		// Get our random colors
		int r1i = r.nextInt(255) + 1;
		int r2i = r.nextInt(255) + 1;
		int r3i = r.nextInt(255) + 1;
		Color c1 = Color.fromRGB(r1i, r2i, r3i);
		Color c2 = Color.fromRGB(r1i, r2i, r3i);

		// Create our effect with this
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

		// Then apply the effect to the meta
		fwm.addEffect(effect);

		// Generate some random power and set it
		int rp = r.nextInt(2) + 1;
		fwm.setPower(rp);

		// Then apply this to our rocket
		fw.setFireworkMeta(fwm);

	}
}
