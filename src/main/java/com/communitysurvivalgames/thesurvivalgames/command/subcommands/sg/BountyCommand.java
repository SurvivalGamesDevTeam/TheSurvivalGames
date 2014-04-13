package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;

public class BountyCommand implements SubCommand {
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("bounty")) {
			{
				//TODO: I18N
				p.sendMessage("This command has not been fully implemented yet!");
			}
		}
	}
}
