/**
 * Name: WorldDefaults.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.multiworld;

import com.communitysurvivalgames.thesurvivalgames.configs.FileLoader;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldDefaults {

    FileLoader configy;
    FileConfiguration config;

    public WorldDefaults(String name) {
        configy = new FileLoader("worldSettings");
        config = configy.getConfig();

        config.addDefault("testing.name", "relicum");
        config.addDefault("testing.friend", "Clive");

        configy.saveConfig();

    }

}
