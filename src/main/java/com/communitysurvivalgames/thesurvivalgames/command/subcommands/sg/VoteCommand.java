package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.exception.ArenaNotFoundException;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.entity.Player;

public class VoteCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if ((cmd.equalsIgnoreCase("vote") || cmd.equalsIgnoreCase("v"))) {
			int map;
			try {
				map = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
				return;
			}
			if (SGApi.getArenaManager().isInGame(p)) {
				try {
					SGApi.getArenaManager().getArena(p).vote(p, map);
				} catch (ArenaNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("LOL_NOPE"));
			}
		}
	}

}
