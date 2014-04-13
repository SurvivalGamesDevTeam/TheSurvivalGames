package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.communitysurvivalgames.thesurvivalgames.managers.MeunManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class SignListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChange(SignChangeEvent event) {
		if (event.getBlock() == null)
			return;
		if (event.getBlock().getState() == null)
			return;
		if(event.getLines()[0] == null)
			return;
		if (event.getLines()[0].equals("[SGJoin]")) {
			event.setLine(0, ChatColor.BLUE + "[SGJoin]");
			return;
		}

		if (event.getLines()[0].equals("[SGKit]")) {
			event.setLine(0, ChatColor.BLUE + "[SGKit]");
			return;
		}
		
		if (event.getLines()[0].equals("[SGSign]")) {
			if(event.getLines()[1] == null)
				return;
			SGApi.getSignManager().addSign(event.getBlock().getLocation(), Integer.parseInt(event.getLines()[1]));
			return;
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onClick(PlayerInteractEvent event) {
		if (event.getClickedBlock() == null)
			return;
		if (event.getClickedBlock().getState() == null)
			return;
		if (event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLines()[0].equals(ChatColor.BLUE + "[SGJoin]")) {
				MeunManager.getMenuManager().displayJoinMenu(event.getPlayer());
				return;
			}
			if (sign.getLines()[0].equals(ChatColor.BLUE + "[SGKit]")) {
				SGApi.getKitManager().displayDefaultKitSelectionMenu(event.getPlayer());
				return;
			}
			if (sign.getLines()[0].equals(ChatColor.GREEN + "[Join]") || sign.getLines()[0].equals(ChatColor.YELLOW + "[Spectate]")) {
				SGApi.getArenaManager().addPlayer(event.getPlayer(), Integer.parseInt(SGApi.getSignManager().getSigns().get(sign.getBlock().getLocation())));
				return;
			}
		}
	}
}
