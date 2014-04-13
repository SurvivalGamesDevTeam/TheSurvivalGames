package com.communitysurvivalgames.thesurvivalgames.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKilledEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	Player player;
	Entity killer;

	/**
	 * Constructs a new KitGivenEvent
	 *
	 * @param player the player that recieved the kit
	 * @param killer the killer
	 */
	public PlayerKilledEvent(Player player, Entity killer) {
		this.player = player;
		this.killer = killer;
	}

	/**
	 * Gets the player that died
	 *
	 * @return the player that died
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the killer
	 * 
	 * @return the player object that killed the player of the event
	 */
	public Entity getKiller() {
		return killer;
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
