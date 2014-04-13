/**
 * Name: PromoteCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PromoteCommand implements SubCommand {

    /**
     * Promotes another player to party leader if the executer is the current
     * leader
     *
     * @param sender The player executing the command
     */
    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 2) && (args[0].equalsIgnoreCase("promote"))) {
            UUID id = SGApi.getPartyManager().getPlayers().get(sender.getName());
           if (id != null) {
                Party party = SGApi.getPartyManager().getParties().get(id);
              if (party.getLeader().equalsIgnoreCase(sender.getName())) {
                    for (String member : party.getMembers()) {
                        if ((member != null) && (member.equalsIgnoreCase(args[0]))) {
                            Player p = Bukkit.getServer().getPlayer(args[0]);
                            if (p != null) {
                                String oldLeader = party.getLeader();
                                party.setLeader(p.getName());
                                party.removeMember(p.getName());
                                party.addMember(oldLeader);
                                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("PROMOTED") + p.getName() + I18N.getLocaleString("TO_LEADER"));
                                p.sendMessage(org.bukkit.ChatColor.YELLOW + sender.getName() + I18N.getLocaleString("PROMOTED_YOU"));
                            } else {
                                sender.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + I18N.getLocaleString("NOT_ONLINE"));
                            }
                            return;
                        }
                    }

                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + args[0] + I18N.getLocaleString("NOT_IN_YOUR_PARTY"));
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("LEADER_TO_PROMOTE"));
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_PARTY_2"));
            }
        }
    }
}
