/**
 * Name: PlayerType.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util;

import org.bukkit.ChatColor;

public enum PlayerType {

    PLAYER(ChatColor.GRAY + "%player%"), MOD(ChatColor.BLUE + "%player%"), ADMIN(ChatColor.RED + "%player%"), VIP(ChatColor.GREEN + "%player%"), MVP(ChatColor.AQUA + "%player%"), YOUTUBER(
            ChatColor.GOLD + "%player%"), OWNER(ChatColor.DARK_PURPLE + "%player%"), DEV(ChatColor.DARK_RED + "%player%");
    /**
     * The Type.
     */
    private final String type;

    /**
     * Instantiates a new Player Types
     * 
     * @param type the node
     */
    private PlayerType(String type) {
        this.type = type;
    }

    /**
     * Gets node. For Lobby Status
     * 
     * @return the node
     */
    public String getType() {
        return type;
    }
}
