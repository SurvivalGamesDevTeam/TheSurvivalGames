/**
 * Name: Countdown.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.runnables;

import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.net.SendWebsocketData;
import com.communitysurvivalgames.thesurvivalgames.net.WebsocketServer;
import com.communitysurvivalgames.thesurvivalgames.net.WebsocketSessionManager;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;

public class Countdown implements Runnable {

	private SGArena a = null;
	public int amount = 0;
	public int count = 0;
	public final String[] s = new String[2];
	private CodeExecutor ce = null;
	private int id = 0;
	private String data = "null";

	/**
	 * Constructs a new countdown for this arena
	 *
	 * @param a      The arena to countdown on
	 * @param amount The amount to decrement each time
	 * @param count  The amount of numbers to countdown from
	 * @param stage  The next stage to go to
	 * @param unit   The trailing suffix after count
	 * @param ce     The code to run when the runnable stops
	 */
	public Countdown(SGArena a, int amount, int count, String stage, String unit, CodeExecutor ce) {
		this.a = a;
		this.amount = amount;
		this.count = count;
		s[0] = stage;
		s[1] = unit;
		this.ce = ce;
	}

	public Countdown(SGArena a, int amount, int count, String stage, String unit, CodeExecutor ce, String data) {
		this.a = a;
		this.amount = amount;
		this.count = count;
		s[0] = stage;
		s[1] = unit;
		this.ce = ce;
		this.data = data;
	}

	/**
	 * Sets the id for the runnable to cancel
	 *
	 * @param id The return value from starting a new task
	 */
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String timeToString() {
		int time = count + 1;
		return time + "";
	}

	/**
	 * The overrided method to run
	 */
	@Override
	public void run() {
		if (count <= 0) {
			Bukkit.getServer().getScheduler().cancelTask(this.id);
			ce.runCode();
			return;
		}
		count -= amount;
		if (SGApi.getPlugin().getPluginConfig().getUseServers()) {
			if (data.equalsIgnoreCase("sounds")) {
				SendWebsocketData.playToArena(a, (count + 1) + "");
			}
		}
		if (data.equalsIgnoreCase("check")) {
			if (a.getPlayers().size() == 0) {
				Bukkit.getServer().getScheduler().cancelTask(this.id);
				a.end();
			}
		}
		if (data.equalsIgnoreCase("nocount") || data.equalsIgnoreCase("check")) {
			return;
		}
		a.broadcast(s[0] + " " + I18N.getLocaleString("STARTING_IN") + " " + (count + 1) + " " + s[1]);
	}
}
