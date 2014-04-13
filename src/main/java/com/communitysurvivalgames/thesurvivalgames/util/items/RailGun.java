package com.communitysurvivalgames.thesurvivalgames.util.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class RailGun implements Listener {
    int timer, id = 0;
    private final Random gen = new Random();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(event.getItem() == null)
    		return;
        if (event.getItem().getType() == Material.DIAMOND_HOE)
            try {
                for (Block loc : getLineOfSigt(event.getPlayer())) {
                    playFirework(loc.getLocation());
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private final Object[] dataStore = new Object[5];

    void playFirework(Location loc) throws Exception {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        if (dataStore[0] == null)
            dataStore[0] = getMethod(loc.getWorld().getClass(), "getHandle");
        if (dataStore[2] == null)
            dataStore[2] = getMethod(fw.getClass(), "getHandle");
        dataStore[3] = ((Method) dataStore[0]).invoke(loc.getWorld(), (Object[]) null);
        dataStore[4] = ((Method) dataStore[2]).invoke(fw, (Object[]) null);
        if (dataStore[1] == null)
            dataStore[1] = getMethod(dataStore[3].getClass(), "addParticle");
        ((Method) dataStore[1]).invoke(dataStore[3], "fireworksSpark", loc.getX(), loc.getY(), loc.getZ(), gen.nextGaussian() * 0.05D, -(loc.getZ() * 1.15D) * 0.5D, gen.nextGaussian() * 0.05D);
        fw.remove();
    }

    private Method getMethod(Class<?> cl, String method) {
        for (Method m : cl.getMethods())
            if (m.getName().equals(method))
                return m;
        return null;
    }

    public Set<Block> getLineOfSigt(Player p) {
        @SuppressWarnings("Convert2Diamond") Set<Block> set = new HashSet<>();
        Iterator<Block> it = new BlockIterator(p, 100);
        while (it.hasNext()) {
            set.add(it.next());
        }
        return set;
    }
}
