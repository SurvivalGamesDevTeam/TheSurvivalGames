package com.communitysurvivalgames.thesurvivalgames.event;

import com.communitysurvivalgames.thesurvivalgames.kits.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
 
public final class KitGivenEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player player;
    Kit kit;

    /**
     * Constructs a new KitGivenEvent
     *
     * @param player the player that received the kit
     * @param kit the kit given to the player
     */
    public KitGivenEvent(Player player, Kit kit) {
        this.player = player;
        this.kit = kit;
    }

    /**
     * Gets the kit that was given to the player
     *
     * @return the kit given
     */
    public Kit getKit() {
        return kit;
    }

    /**
     * Gets the player that recieved the kit
     *
     * @return the player that recieved the kit
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the {@link HandlerList} for the event
     *
     * @return the {@link HandlerList} for the event
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Gets the handlers for the event
     *
     * @return the handlers
     */
    public HandlerList getHandlers() {
        return handlers;
    }
}