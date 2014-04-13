/**
 * Name: CreateCommand.java Created: 25 November 2013 Edited: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class CreateCommand implements SubCommand {

	/**
	 * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 * 
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (!p.hasPermission("sg.create") || !p.isOp())
			return;
		if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
			Bukkit.getLogger().severe("You're running the server in Bungeecord mode, yet you are not running Bungeecord at all... people these days");
		}
		if (cmd.equalsIgnoreCase("create")) {
			try {
				if (args.length == 0) {
					p.sendMessage("Format: /sg create <import type> <world name>");
					return;
				}
				if (args[0].equalsIgnoreCase("custom")) {
					if (args.length == 1) {
						p.sendMessage("Format: /sg create <import type> <world name>");
						return;
					}
					SGApi.getArenaManager().createWorld(p, args[1], args[1]);
					p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("CREATING_ARENA"));
				} else if (args[0].equalsIgnoreCase("download")) {
					if (args.length == 1 || args.length == 2) {
						p.sendMessage("Format: /sg create <import type> <world name>");
						return;
					}
					SGApi.getPlugin().getTracker().trackEvent("Map Download", args[1]);
					SGApi.getArenaManager().createWorldFromDownload(p, args[1], args[1]);
				} else if (args[0].equalsIgnoreCase("import")) {
					if (args.length == 1 || args.length == 2) {
						p.sendMessage("Format: /sg create <import type> <world name>");
						return;
					}
					SGApi.getArenaManager().createWorldFromImport(p, args[1], args[1]);
				}
				return;
			} catch (ArrayIndexOutOfBoundsException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
			}
		}

		if (cmd.equalsIgnoreCase("finish")) {
			SGApi.getArenaManager().getCreators().remove(p.getName());
			p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("FINISHED"));
		}
	}
}
