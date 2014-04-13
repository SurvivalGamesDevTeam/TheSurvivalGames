/**
 * Name: TimeManager.java
 * Created: 28 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;
import com.communitysurvivalgames.thesurvivalgames.listeners.MoveListener;
import com.communitysurvivalgames.thesurvivalgames.listeners.SafeEntityListener;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.net.SendWebsocketData;
import com.communitysurvivalgames.thesurvivalgames.objects.MapHash;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import com.communitysurvivalgames.thesurvivalgames.runnables.CodeExecutor;
import com.communitysurvivalgames.thesurvivalgames.runnables.Countdown;

public class TimeManager {

	private SGArena a;

	public TimeManager(SGArena a) {
		this.a = a;
	}

	public Countdown gameTime;
	public Countdown g;
	public Countdown cg;
	public Countdown dm;
	public Countdown cdm;
	public Countdown end;

	public void countdownLobby(int n) {
		// setup the voting
		int i = 0;
		List<MapHash> hashes = new ArrayList<>();
		Collections.shuffle(SGApi.getMultiWorldManager().getWorlds());
		for (SGWorld world : SGApi.getMultiWorldManager().getWorlds()) {
			if (world.isInLobby()) {
				continue;
			}

			if (world.getWorld().getPlayers().isEmpty() && i <= 5) {
				world.setInLobby(true);
				MapHash hash = new MapHash(world, i);
				hashes.add(hash);
				i++;
			}
		}
		for (MapHash hash : hashes) {
			a.votes.put(hash, 0);
		}

		for (String s : a.players) {
			ItemManager.instance.gem.givePlayerItem(Bukkit.getPlayer(s));
		}

		a.broadcast("Use the emerald in your inventory to vote!");
		a.broadcastVotes();

		a.setState(SGArena.ArenaState.PRE_COUNTDOWN);

		g = new Countdown(a, 1, n, "Game", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				//handle votes
				Map.Entry<MapHash, Integer> maxEntry = null;
				for (Map.Entry<MapHash, Integer> entry : a.votes.entrySet()) {
					if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
						maxEntry = entry;
					}
				}
				a.currentMap = maxEntry.getKey().getWorld();
				a.votes.remove(maxEntry);
				for (MapHash m : a.votes.keySet()) {
					m.getWorld().setInLobby(false);
				}
				a.votes.clear();
				a.broadcast(SGApi.getArenaManager().prefix + I18N.getLocaleString("MAP_WINNER") + " " + a.currentMap.getWorld().getName());

				Bukkit.getPluginManager().callEvent(new GameStartEvent(a));
				a.broadcast(I18N.getLocaleString("GAME_STARTING"));
				a.setState(SGArena.ArenaState.STARTING_COUNTDOWN);

				int index = 0;
				for (String s : a.getPlayers()) {
					Player p = Bukkit.getPlayerExact(s);
					Bukkit.getLogger().info("List: " + a.currentMap.locs.toString());
					Location loc = a.currentMap.locs.get(index);
					p.getInventory().clear();
					p.teleport(loc);

					index++;
				}
				for (String s : a.getPlayers()) {
					SendWebsocketData.stopMusic(Bukkit.getPlayer(s));
				}
				countdown();
				MoveListener.getPlayers().addAll(a.getPlayers());
			}
		});
		g.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), g, 0L, 60 * 20L));
	}

	public void countdown() {
		cg = new Countdown(a, 1, 10, "Game", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {

				SendWebsocketData.playToArena(a, "play");

				a.broadcast(I18N.getLocaleString("ODDS"));
				a.setState(SGArena.ArenaState.IN_GAME);
				for (String s : a.getPlayers()) {
					if (MoveListener.getPlayers().contains(s)) {
						MoveListener.getPlayers().remove(s);
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 500, 9));
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 250, 2));
						Bukkit.getPlayer(s).addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 20, 50));
					}
				}

				a.broadcast(ChatColor.GOLD + "You will get your kit in 10 seconds!");

				Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						for (String s : a.getPlayers()) {
							SGApi.getKitManager().giveKit(Bukkit.getPlayer(s));
						}
					}
				}, 200L);
				countupGame();
				countdownDm();
			}
		}, "sounds");
		cg.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), cg, 0L, 20L));
	}

	public void countupGame() {
		gameTime = new Countdown(a, -1, 1, "GameTime", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {
				//Ignored			
			}
		}, "check");
		gameTime.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), gameTime, 0L, 20L));
	}

	public void countdownDm() {
		dm = new Countdown(a, 1, SGApi.getPlugin().getPluginConfig().getDmTime(), "DeathMatch", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				a.broadcast(I18N.getLocaleString("DM_STARTING"));
				a.setState(SGArena.ArenaState.DEATHMATCH);
				for (int i = 0; i < a.getPlayers().size(); i++) {
					String s = a.getPlayers().get(i);
					Player p = Bukkit.getPlayer(s);
					p.teleport(a.getCurrentMap().locs.get(i));
				}
				MoveListener.getPlayers().addAll(a.getPlayers());
				commenceDm();
			}
		});
		dm.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), dm, 0L, 60 * 20L));
	}

	void commenceDm() {
		cdm = new Countdown(a, 1, 10, "DeathMatch", "seconds", new CodeExecutor() {
			@Override
			public void runCode() {
				a.broadcast(I18N.getLocaleString("START"));
				MoveListener.getPlayers().removeAll(a.getPlayers());

				countdownEnd();
			}
		});
		cdm.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), cdm, 0L, 20L));
	}

	void countdownEnd() {
		end = new Countdown(a, 1, 5, "EndGame", "minutes", new CodeExecutor() {
			@Override
			public void runCode() {
				a.broadcast(I18N.getLocaleString("END") + " TIED_GAME");
				// tp out of arena, rollback, pick up all items and arrows
			}
		});
		end.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(SGApi.getPlugin(), end, 0L, 60 * 20L));
	}

	public void forceReset() {
		if (g != null)
			Bukkit.getScheduler().cancelTask(g.getId());
		if (cg != null)
			Bukkit.getScheduler().cancelTask(cg.getId());
		if (dm != null)
			Bukkit.getScheduler().cancelTask(dm.getId());
		if (cdm != null)
			Bukkit.getScheduler().cancelTask(cdm.getId());
		if (end != null)
			Bukkit.getScheduler().cancelTask(end.getId());
		if (gameTime != null)
			Bukkit.getScheduler().cancelTask(gameTime.getId());
	}
}
