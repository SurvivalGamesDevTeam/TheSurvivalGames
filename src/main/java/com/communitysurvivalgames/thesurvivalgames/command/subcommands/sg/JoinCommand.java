/**
 * Name: JoinCommand.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.managers.MeunManager;

public class JoinCommand implements SubCommand {

	/**
	 * The join command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 * 
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("join")) {
			MeunManager.getMenuManager().displayJoinMenu(p);
		}
	}
}
