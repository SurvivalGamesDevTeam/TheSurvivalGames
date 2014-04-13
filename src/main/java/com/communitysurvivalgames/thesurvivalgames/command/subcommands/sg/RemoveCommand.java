package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RemoveCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (!p.hasPermission("sg.remove") && !p.isOp()) {
			return;
		}
		if (SGApi.getPlugin().getPluginConfig().isBungeecordMode()) {
			Bukkit.getLogger().severe("You're running the server in Bungeecord mode, yet you are not running Bungeecord at all... people these days");
		}
		if (cmd.equals("remove") && args.length == 1) {
			int num;
			try {
				num = Integer.parseInt(args[0]);
			} catch (NumberFormatException x) {
				p.sendMessage(SGApi.getArenaManager().error + I18N.getLocaleString("NOT_NUMBER"));
				return;
			}
			SGApi.getArenaManager().removeArena(num);
			p.sendMessage(SGApi.getArenaManager().prefix + I18N.getLocaleString("REMOVED_ARENA"));
		}
	}
}
