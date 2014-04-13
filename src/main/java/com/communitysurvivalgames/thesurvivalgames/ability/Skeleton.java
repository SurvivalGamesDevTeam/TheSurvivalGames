package com.communitysurvivalgames.thesurvivalgames.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class Skeleton extends SGAbility implements Listener {

	public static List<Entity> arrows = new ArrayList<Entity>();

	public Skeleton() {
		super(8);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void entityDamagedByEntityt(EntityDamageByEntityEvent event) {
		if (arrows.contains(event.getDamager())) {
			arrows.remove(event.getDamager());
			event.setDamage(4);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void entityShootBowEvent(EntityShootBowEvent event) {

		if (event.getEntity() instanceof Player) {
			Player shooter = (Player) event.getEntity();
			if (hasAbility(shooter)) {
				if (event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)) {
					arrows.add(event.getProjectile());
				}
			}
		}
	}
}
