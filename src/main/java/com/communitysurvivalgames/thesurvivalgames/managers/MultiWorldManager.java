/**
 * Name: MultiworldMain.java Created: 13 December 2013
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;

public class MultiWorldManager {
    
    List<SGWorld> worlds = new ArrayList<SGWorld>();

    public MultiWorldManager() {
    }

    SGWorld createWorld(String name, String display) {
        SGWorld world = new SGWorld(name, display);
        world.create();
        worlds.add(world);
        return world;
    }

    public void deleteWorld(String name) {
        SGWorld w = worldForName(name);
        if(worlds.contains(w)) {
            worlds.remove(w);
            w.remove();
        }
    }

    public World copyFromInternet(final Player sender, final String worldName, final String display) {
    	
        createWorld(worldName, display);

        return Bukkit.getWorld(worldName);
    }

    public World importWorldFromFolder(final Player sender, final String worldName, String display) {
        if (!checkIfIsWorld(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), worldName))) {
            sender.sendMessage("That's not a world :/");
            return null;
        }
        createWorld(worldName, display);

        return Bukkit.getWorld(worldName);
    }

    public World createRandomWorld(final String worldName) {
        // TODO
        return Bukkit.getWorld(worldName);
    }
    
    public List<SGWorld> getWorlds() {
        return worlds;
    }

    public SGWorld worldForName(String name) {
        for(SGWorld world : getWorlds()) {
            if(world.getWorld().getName().equalsIgnoreCase(name)) {
                return world;
            }
        }
        return null;
    }

    private static boolean checkIfIsWorld(File worldFolder) {
        if (worldFolder.isDirectory()) {
            File[] files = worldFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.equalsIgnoreCase("level.dat");
                }
            });
            if (files != null && files.length > 0) {
                return true;
            }
        }
        return false;
    }
}
