package com.communitysurvivalgames.thesurvivalgames.event;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    SGArena arena = null;

    /**
     * Constructs a new GameStartEvent
     *
     * @param arena the {@link SGArena} that started
     */
    public GameEndEvent(SGArena arena) {
        this.arena = arena;    
    }

    /**
     * Gets the {@link SGArena} that started
     *
     * @return the {@link SGArena} that stared
     */
    public SGArena getArena() {
        return arena;
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
