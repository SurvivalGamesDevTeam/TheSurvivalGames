/**
 * Name: SetupListener.java Created: 7 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.listeners;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SetupListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (SGApi.getArenaManager().getCreators().containsKey(e.getPlayer().getName()) && e.getAction() == Action.LEFT_CLICK_BLOCK) {
            SGWorld a = SGApi.getArenaManager().getCreators().get(e.getPlayer().getName());

            a.nextSpawn(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(SGApi.getArenaManager().prefix + "Spawn " + a.locs.size() + " set!");
        }
    }

}
