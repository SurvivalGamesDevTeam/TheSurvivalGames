/**
 * Name: FireWorkEffect.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.callables;

import java.util.Random;
import java.util.concurrent.Callable;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 * Creates a random Firework effect.
 * <p>
 * This is run under a {@link java.util.concurrent.Callable} which is simila to
 * a Runnable but will return a result. This should really be called though
 * {@link com.communitysurvivalgames.thesurvivalgames.managers.ScheduleManager#executor}
 */
public class RandomFirework implements Callable {
    /**
     * Computes a result, or throws an exception if unable to do so.
     * 
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {

        FireworkMeta fwm = (FireworkMeta) Bukkit.getItemFactory().getItemMeta(Material.FIREWORK);

        // Our random generator
        Random r = new Random();

        // Get the type
        int rt = r.nextInt(5) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1)
            type = FireworkEffect.Type.BALL;
        if (rt == 2)
            type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3)
            type = FireworkEffect.Type.BURST;
        if (rt == 4)
            type = FireworkEffect.Type.CREEPER;
        if (rt == 5)
            type = FireworkEffect.Type.STAR;

        // Get our random colors
        int r1i = r.nextInt(255) + 1;
        int r2i = r.nextInt(255) + 1;
        int r3i = r.nextInt(255) + 1;
        Color c1 = Color.fromRGB(r1i, r2i, r3i);
        Color c2 = Color.fromRGB(r1i, r2i, r3i);

        // Create our effect with this
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

        // Then apply the effect to the meta
        fwm.addEffect(effect);

        // Generate some random power and set it
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        return fwm;
    }
}
