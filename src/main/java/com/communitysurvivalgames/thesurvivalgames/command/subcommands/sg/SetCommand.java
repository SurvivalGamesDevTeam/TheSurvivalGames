/**
 * Name: CreateCommand.java 
 * Created: 21 December 2013 
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class SetCommand implements SubCommand {
	// TODO permissions

	/**
	 * The create command. DO NOT CALL DIRECTLY. Only use in CommandHandler
	 * 
	 * @param cmd The command that was executed
	 * @param p The player that executed the command
	 * @param args The arguments after the command
	 */
	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (!p.hasPermission("sg.set") && !p.isOp()) {
			p.sendMessage(ChatColor.RED + "You do not have the permission for [SG.SET]");
		}
		try {
			if (cmd.equalsIgnoreCase("createlobby")) {
				SGArena a = SGApi.getArenaManager().createLobby(p);
				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("CREATING_LOBBY") + " " + a.getId());
			} else if (cmd.equalsIgnoreCase("setmaxplayers")) {
				int i;
				int amount;
				try {
					i = Integer.parseInt(args[0]);
					amount = Integer.parseInt(args[1]);
				} catch (NumberFormatException x) {
					p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
					return;
				}

				SGArena a;
				try {
					a = SGApi.getArenaManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
				a.maxPlayers = amount;
				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("Set min players for: ") + " " + a.getId());
			} else if (cmd.equalsIgnoreCase("setminplayers")) {
				int i;
				int amount;
				try {
					i = Integer.parseInt(args[0]);
					amount = Integer.parseInt(args[1]);
				} catch (NumberFormatException x) {
					p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
					return;
				}

				SGArena a;
				try {
					a = SGApi.getArenaManager().getArena(i);
				} catch (ArenaNotFoundException e) {
					Bukkit.getLogger().severe(e.getMessage());
					return;
				}
				a.minPlayers = amount;
				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("Set min players for: ") + " " + a.getId());
			} else if (cmd.equalsIgnoreCase("setchest")) {
				SGWorld world = SGApi.getMultiWorldManager().worldForName(args[1]);
				BlockIterator bit = new BlockIterator(p, 6);
				Block next;
				while (bit.hasNext()) {
					next = bit.next();
					if (next.getType() == Material.CHEST) {
						if (args[0].equalsIgnoreCase("t2") && !world.t2.contains(next.getState())) {
							world.t2.add(next.getState());
						} else if (args[0].equalsIgnoreCase("t1") && world.t2.contains(next.getState())) {
							world.t2.remove(next.getState());
						} else {
							p.chat("/sg help");
						}
					}
				}

				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("SET_CHEST") + " " + world.getDisplayName());
			} else if (cmd.equalsIgnoreCase("setgamespawn")) {
				int spawn;
				try {
					spawn = Integer.parseInt(args[0]);
				} catch (NumberFormatException x) {
					p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
					return;
				}

				SGWorld world = SGApi.getMultiWorldManager().worldForName(args[1]);
				if (world == null) {
					return;
				}
				world.locs.set(spawn - 1, p.getLocation());

				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("SET_SPAWN") + " " + world.getWorld().getName());
			}
		} catch (ArrayIndexOutOfBoundsException x) {
			p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("INVALID_ARGUMENTS"));
		}
	}
}
