/**
 * Name: ChatListener.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.net.SendWebsocketData;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;
import com.communitysurvivalgames.thesurvivalgames.util.PlayerNameUtil;

public class ChatListener implements Listener {

	/**
	 * Formats chat and detects if the player is using party chat, if so, it
	 * will only send messages to the people in that player's party
	 * 
	 * @param event The event being called
	 */
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getMessage().equalsIgnoreCase("When life gives you lemons, make lemonade.")) {
			SendWebsocketData.playToAll("lemons");
		}
		if (SGApi.getPartyManager().getPartyChat().contains(event.getPlayer().getName())) {
			UUID id = SGApi.getPartyManager().getPlayers().get(event.getPlayer().getName());
			if (id != null) {
				Party party = SGApi.getPartyManager().getParties().get(id);
				if (party != null) {
					String[] members = party.getMembers();
					for (String member : members) {
						if (member != null) {
							Player p = Bukkit.getServer().getPlayer(member);
							if (p != null) {
								p.sendMessage(ChatColor.DARK_AQUA + "[P] " + event.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + ": " + event.getMessage());
							}
						}
					}
					org.bukkit.entity.Player p = org.bukkit.Bukkit.getServer().getPlayer(party.getLeader());
					if (p != null) {
						p.sendMessage(org.bukkit.ChatColor.DARK_AQUA + "[P] " + event.getPlayer().getDisplayName() + org.bukkit.ChatColor.DARK_AQUA + ": " + event.getMessage());
					}
				}
				event.setCancelled(true);
				Bukkit.getLogger().log(Level.INFO, "[P] {0}: {1}", new Object[] { event.getPlayer().getDisplayName(), event.getMessage() });

				org.bukkit.entity.Player[] playerList = org.bukkit.Bukkit.getServer().getOnlinePlayers();
				int playersNum = org.bukkit.Bukkit.getServer().getOnlinePlayers().length;
				for (int i = 0; i < playersNum; i++) {
					org.bukkit.entity.Player p = playerList[i];
					assert party != null;
					if ((p.hasPermission("partymanager.admin.spy")) && (!party.hasMember(p.getName()))) {
						p.sendMessage(org.bukkit.ChatColor.GRAY + "[P] " + event.getPlayer().getName() + ": " + event.getMessage());
					}
				}
			} else {
				SGApi.getPartyManager().getPartyChat().remove(event.getPlayer().getName());
			}
		} else {
			String prefix = PlayerNameUtil.getDevs().contains(event.getPlayer().getName()) ? ChatColor.translateAlternateColorCodes('&', "&0&l&kiiii&4&lD&6&le&e&lv&2&le&a&ll&b&lo&9&lp&5&le&1&lr&0&l&kiiii") : ChatColor.translateAlternateColorCodes('&', SGApi.getPlugin().getPlayerData(event.getPlayer()).getRank());
			String name = PlayerNameUtil.getDevs().contains(event.getPlayer().getName()) ? ChatColor.translateAlternateColorCodes('&', "&r&a" + event.getPlayer().getDisplayName() + "&r") : ChatColor.translateAlternateColorCodes('&', "&r" + event.getPlayer().getDisplayName());
			event.setFormat(ChatColor.GRAY + "[" + SGApi.getPlugin().getPlayerData(event.getPlayer()).getKills() + "] " + prefix + ChatColor.GRAY + " " + name + ": " + event.getMessage());
		}
	}
}
