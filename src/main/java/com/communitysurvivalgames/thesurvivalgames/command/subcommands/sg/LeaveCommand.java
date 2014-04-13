/**
 * Name: LeaveCommand.java
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import org.bukkit.entity.Player;

public class LeaveCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (cmd.equalsIgnoreCase("leave")) {
			if (SGApi.getArenaManager().isInGame(p)) {
				try {
					if (SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS) || SGApi.getArenaManager().getArena(p).getState().equals(SGArena.ArenaState.WAITING_FOR_PLAYERS))
						SGApi.getArenaManager().removePlayer(p);
					else
						SGApi.getArenaManager().playerDeathAndLeave(p, SGApi.getArenaManager().getArena(p));
				} catch (ArenaNotFoundException e) {
					p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("LOL_NOPE"));
					return;
				}
				p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("LEFT_ARENA"));
			} else {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("LOL_NOPE"));
			}
		}
	}

}
