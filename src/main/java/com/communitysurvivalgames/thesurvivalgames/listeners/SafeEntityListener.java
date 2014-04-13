/**
 * Name: SafeEntityListener.java 
 * Created: 29 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class SafeEntityListener implements Listener {

    private static final List<String> safe = new ArrayList<>();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (safe.contains(((Player) e.getEntity()).getName())) {
                e.setCancelled(true);
            }
        }
    }

    public static List<String> getPlayers() {
        return safe;
    }

}
