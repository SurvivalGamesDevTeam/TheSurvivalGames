/**
 * Name: InviteCommand.java Created: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.command.subcommands.party;

import java.util.UUID;

import com.communitysurvivalgames.thesurvivalgames.command.subcommands.SubCommand;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand implements SubCommand {

    /**
     * Invites a player to your party - creates a party if you don't have one
     *
     * @param sender The player executing the command
     */
    public void execute(String cmd, Player sender, String[] args) {
        if ((args.length == 1) && (args[0].equalsIgnoreCase("invite"))) {
            UUID partyID = SGApi.getPartyManager().getPlayers().get(sender.getName());
          if (partyID == null) {
                partyID = PartyManager.startParty(sender);

                sendInvite(sender, args[0], partyID);
            } else if (SGApi.getPartyManager().getParties().get(partyID).hasRoom()) {
                if (SGApi.getPartyManager().getParties().get(partyID).getLeader().equalsIgnoreCase(sender.getName())) {
                sendInvite(sender, args[0], partyID);
                } else {
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("NOT_LEADER"));
                }
            } else {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("PARTY_FULL"));
            }
        }
    }

    /**
     * NOTE: This is a backend function and is not to be used outside of this
     * class
     * <p/>
     * Sends the invite to the player
     *
     * @param sender The player sending the invite
     * @param player The player to receive the invite
     * @param id     The UUID of the player to be invited
     */
    private static void sendInvite(Player sender, String player, UUID id) {
        Player p = Bukkit.getServer().getPlayer(player);
        if (p != null) {
            if (SGApi.getPartyManager().getPlayers().get(p.getName()) != null) {
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + I18N.getLocaleString("IN_PARTY"));
                if (SGApi.getPartyManager().getParties().get(id).hasNoMembers()) {
    PartyManager.endParty(sender.getName(), id);
                }
                return;
            }
            if ((SGApi.getPartyManager().getInvites().containsKey(p.getName())) && (SGApi.getPartyManager().getInvites().containsValue(id))) {
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + player + I18N.getLocaleString("PENDING_INVITE"));
                return;
            }

            SGApi.getPartyManager().getInvites().put(p.getName(), id);
            p.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("INVITED_TO_PARTY") + sender.getName());
            sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("YOU_INVITED") + player + I18N.getLocaleString("TO_JOIN_PARTY"));
        } else {
            sender.sendMessage(org.bukkit.ChatColor.YELLOW + I18N.getLocaleString("COULD_NOT_FIND_INVITE") + player);
            if (SGApi.getPartyManager().getParties().get(id).hasNoMembers()) {
                PartyManager.endParty(sender.getName(), id);
            }
        }
    }
}
