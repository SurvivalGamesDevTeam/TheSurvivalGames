package com.communitysurvivalgames.thesurvivalgames.ability;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Pig extends SGAbility implements Listener {

	Random r = new Random();

	public Pig() {
		super(9);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.PORK && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Oink?")) {
				this.removeOneFromHand(player);
				for (int i = 0; i < 15; i++) {
					Entity e = player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
					e.setVelocity(new Vector(r.nextDouble(), r.nextDouble(), r.nextDouble()));
				}
			}
		}
	}
}
