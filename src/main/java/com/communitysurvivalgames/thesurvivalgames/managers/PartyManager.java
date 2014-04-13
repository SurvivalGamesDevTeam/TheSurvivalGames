/**
 * Name: PartyManager.java Edited: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.*;
import com.communitysurvivalgames.thesurvivalgames.locale.I18N;
import com.communitysurvivalgames.thesurvivalgames.objects.Party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PartyManager {

    private static final Map<String, UUID> partyPlayers = new HashMap<>();
    private static final Map<UUID, Party> parties = new HashMap<>();
    private static final Map<String, UUID> invites = new HashMap<>();
   private static final Set<String> partyChat = new HashSet<>();
    private static int partySize;

    /**
     * Gets the list of partyPlayers in parties
     * 
     * @return THe list of partyPlayers
     */
    public Map<String, UUID> getPlayers() {
        return partyPlayers;
  }

    /**
     * Gets the list of parties
     * 
     * @return List of parties
     */
    public Map<UUID, Party> getParties() {
    return parties;
    }

    /**
     * Gets the list of current party invites
     * 
     * @return The list of invites
     */
    public Map<String, UUID> getInvites() {
        return invites;
    }

    /**
     * Gets the list of usernames of partyPlayers currently in party chat
     * 
     * @return List of usernames as a Set
     */
    public Set<String> getPartyChat() {
        return partyChat;
    }

    /**
     * @param player The player starting the party
     * @return UUID The UUID (Unique ID) of the new party
     */
    public static UUID startParty(Player player) {
        Party party = new Party(player.getName());
        partyPlayers.put(player.getName(), party.getID());
        parties.put(party.getID(), party);
        if (player != null) { // TODO problem-an NPE would be thrown before the
                              // statement is reached
    player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
        }
        return party.getID();
    }

    /**
     * Ends a party
     * 
     * @param name Name of the player ending the party
     * @param id The UUID of the party to end
     */
    public static void endParty(String name, java.util.UUID id) {
        Party party = parties.get(id);

        for (String members : party.getMembers()) {
            if (members != null) {
                Player player = Bukkit.getServer().getPlayer(members);
                if (player != null) {
                    player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
                }
                partyPlayers.remove(members);
            }
      }
        party.removeAll();
        Player player = Bukkit.getServer().getPlayer(name);
        if (player != null) {
            player.sendMessage(ChatColor.YELLOW + I18N.getLocaleString("PARTY_CREATED"));
        }
        partyPlayers.remove(name);
        parties.remove(id);
    }

    /**
     * Gets the max size of a party
     * 
     * @return The max size
     */
    public static int getMaxPartySize() {
        return partySize; // TODO Right now this will null pointer, this will be
                          // a config value when we get around to a config
                          // loader
    }

    /**
     * Gets the party members of the party the player is in
     * 
     * @param p The player to get the members of
     * @return null if the player is not in a party, a String separated by
     *         commas of usernames
     */
    public String getParty(Player p) {
        UUID id = partyPlayers.get(p.getName());
        if (id != null) {
            Party party = parties.get(id);
            if (party != null) {
                String partyMembers = party.getLeader();
                for (String member : party.getMembers()) {
                    if (member != null) {
                        partyMembers = partyMembers + "," + member;
                    }
                }
                return partyMembers;
            }

            return null;
        }

        return null;
    }

    /**
     * Gets if the player is the party leader
     * 
     * @param p The player to be checked
     * @return If the player is the leader
     */
    public boolean isPartyLeader(Player p) {
        UUID id = partyPlayers.get(p.getName());
        if (id != null) {
            Party party = parties.get(id);
            if (party != null) {
                if (party.getLeader().equalsIgnoreCase(p.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

}
