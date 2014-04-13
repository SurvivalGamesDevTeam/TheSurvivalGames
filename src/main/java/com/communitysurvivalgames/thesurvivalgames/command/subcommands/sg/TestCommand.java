package com.communitysurvivalgames.thesurvivalgames.command.subcommands.sg;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.util.FakeBlockUtil;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkUtil;

public class TestCommand implements SubCommand {

	@Override
	public void execute(String cmd, Player p, String[] args) {
		if (args[0].equalsIgnoreCase("firework"))
			FireworkUtil.getCircleUtil().playFireworkRing(p, FireworkEffect.builder().withColor(Color.FUCHSIA).withFade(Color.BLUE).trail(true).flicker(false).with(Type.BALL).build(), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		if (args[0].equalsIgnoreCase("kit"))
			SGApi.getKitManager().displayDefaultKitSelectionMenu(p);
		if (args[0].equalsIgnoreCase("sphere"))
			FakeBlockUtil.createFakeSphere(p);
	}

}
