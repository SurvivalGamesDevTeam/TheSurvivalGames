/**
 * Name: Party.java Edited: 8 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;


import com.communitysurvivalgames.thesurvivalgames.managers.PartyManager;

import java.util.UUID;

public class Party {

    private String leader;
    private final String[] members;
    private final UUID id;

    /**
     * Creates an instance of a party
     *
     * @param leader The username of the leader of the party
     */
    public Party(String leader) {
        this.leader = leader;
        this.members = new String[PartyManager.getMaxPartySize() - 1];
        this.id = UUID.randomUUID();
    }

    /**
     * Gets the party's leader
     *
     * @return The username of the player leading the party
     */
    public String getLeader() {
        return this.leader;
    }

    /**
     * Sets the party's leader
     *
     * @param player THe new leader
     */
    public void setLeader(String player) {
        this.leader = player;
    }

    /**
     * Gets the members of the party
     *
     * @return members as an array of usernames
     */
    public String[] getMembers() {
        return this.members;
    }

    /**
     * Adds a player to the party
     *
     * @param player The player to be added
     */
    public void addMember(String player) {
        for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
            if (this.members[i] == null) {
                this.members[i] = player;
                return;
            }
        }
    }

    /**
     * Checks to see if the player is a member of this party
     *
     * @param player The player to be checked
     * @return If the player is a member
     */
    public boolean hasMember(String player) {
        for (String member : this.members) {
            if (player.equalsIgnoreCase(member)) {
                return true;
            }
        }
        return this.leader.equalsIgnoreCase(player);
    }

    /**
     * Removes a player from the party
     *
     * @param player Player to be removed
     */
    public void removeMember(String player) {
        for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
            if ((this.members[i] != null) && (this.members[i].equalsIgnoreCase(player))) {
                this.members[i] = null;
                return;
            }
        }

    }

    /**
     * Removes all members of the party -- How sad :(
     */
    public void removeAll() {
        for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
            this.members[i] = null;
        }
    }

    /**
     * Gets the UUID (Unique ID) of the party
     *
     * @return UUID - the ID
     */
    public UUID getID() {
        return this.id;
    }

    /**
     * Checks to see if the party has members -- I am looking to remove this
     * method in the future
     *
     * @return If the party has no members
     */
    public boolean hasNoMembers() {
        for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
            if (this.members[i] != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see if the party has room for more members
     *
     * @return If the party has room
     */
    public boolean hasRoom() {
        for (int i = 0; i < PartyManager.getMaxPartySize() - 1; i++) {
            if (this.members[i] == null) {
                return true;
            }
        }
        return false;
    }
}
