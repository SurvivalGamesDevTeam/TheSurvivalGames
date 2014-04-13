package com.communitysurvivalgames.thesurvivalgames.command.standalone;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class SponsorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player p = (Player) sender;
		try {
			SGArena a = SGApi.getArenaManager().getArena(p);
			if (a.players.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You must be dead to sponsor players");
			} else {
				SGApi.getSponsorManager(a).sponsor(p);
			}
		} catch (ArenaNotFoundException e) {
			p.sendMessage(ChatColor.RED + "You must be in a game to use that");
		}
		return false;
	}

}
