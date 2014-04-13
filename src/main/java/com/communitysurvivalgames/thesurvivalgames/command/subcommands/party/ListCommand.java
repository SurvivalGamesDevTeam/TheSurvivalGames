/**
 * Name: ListCommand.java Created: 8 December 2013
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

public class ListCommand implements SubCommand {

    /**
     * Lists the current members of your party
     *
     * @param sender The player executing the command
     */
    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 1) && (args[0].equalsIgnoreCase("list")) && (sender.hasPermission("party.admin.list"))) {
            executeAdmin(sender, args[0]);
        }
        if ((cmd.equalsIgnoreCase("list"))) {

            UUID id = SGApi.getPartyManager().getPlayers().get(sender.getName());
           if (id != null) {
                Party party = SGApi.getPartyManager().getParties().get(id);
              String list = org.bukkit.ChatColor.GOLD + party.getLeader() + " ";
                for (String member : party.getMembers()) {
                    if (member != null) {
                        Player player = Bukkit.getServer().getPlayer(member);
                        if (player == null) {
                            list = list + org.bukkit.ChatColor.DARK_GRAY + member + " ";
                        } else {
                            list = list + org.bukkit.ChatColor.WHITE + member + " ";
                        }
                    }
                }

                sender.sendMessage(list);
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NO_PARTY_2"));
            }
        }
    }

    /**
     * Lists the members of another player's party
     *
     * @param sender The player executing the command
     * @param args   The player's username of the party who you want to list the
     *               members of
     */
    private static void executeAdmin(Player sender, String args) {
        Player p = Bukkit.getServer().getPlayer(args);
        if (p != null) {
            UUID id = SGApi.getPartyManager().getPlayers().get(p.getName());
        if (id != null) {
                Party party = SGApi.getPartyManager().getParties().get(id);
        String list = org.bukkit.ChatColor.GOLD + party.getLeader() + " ";
                for (String member : party.getMembers()) {
                    if (member != null) {
                        Player player = Bukkit.getServer().getPlayer(member);
                        if (player == null) {
                            list = list + org.bukkit.ChatColor.DARK_GRAY + member + " ";
                        } else {
                            list = list + org.bukkit.ChatColor.WHITE + member + " ";
                        }
                    }
                }

                sender.sendMessage(list);
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + args + I18N.getLocaleString("NOT_IN_PARTY"));
            }
        } else {
            sender.sendMessage(org.bukkit.ChatColor.YELLOW + args + I18N.getLocaleString("NOT_IN_PARTY"));
        }
    }
}
