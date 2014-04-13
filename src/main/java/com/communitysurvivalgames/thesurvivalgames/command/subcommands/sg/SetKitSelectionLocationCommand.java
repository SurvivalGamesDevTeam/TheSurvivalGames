package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;

public class SetKitSelectionLocationCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDER_CRYSTAL);
	}

}
