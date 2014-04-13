/**
 * Name: FileLoader.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Used to load and save {@link org.bukkit.configuration.file.FileConfiguration}
 * files
 * <p>
 * The class file function like the default actions except their
 * <p>
 * there is no saveDefaultConfig method the saveConfig acts in the same way
 * 
 * @author CommunitySurvivalGames
 * @version 0.0.3
 * 
 */
public class FileLoader {

    public final String n;
    public File f;
    public FileConfiguration fc;

    /**
     * Instantiates a new File using the
     * <p>
     * give {@link java.io.File} name as a {@link java.lang.String}.
     * 
     * @param name the name of the file as a {@link java.lang.String}
     */
    public FileLoader(String name) {
        this.n = name;
    }

    /**
     * Gets config Method works the same as the default getConfig()
     * 
     * @return the config
     */
    public FileConfiguration getConfig() {
        if (fc == null) {
            reloadConfig();
        }
        return fc;
    }

    /**
     * Reload config file or creates a new one if now doesn't exist
     */
    public void reloadConfig() {
        if (fc == null) {
            f = new File(SGApi.getPlugin().getDataFolder(), n + ".yml");
        }

        fc = YamlConfiguration.loadConfiguration(f);
        InputStream defConfigStream = SGApi.getPlugin().getResource(n + ".yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fc.setDefaults(defConfig);

        }
    }

    /**
     * Save config file. This acts the same as
     * {@link org.bukkit.plugin.java.JavaPlugin} saveDefaultConfig() No need for
     * to
     * 
     */
    public void saveConfig() {
        if (f == null) {
            f = new File(SGApi.getPlugin().getDataFolder(), n + ".yml");
        }
        try {
            getConfig().options().copyDefaults(true);
            getConfig().save(f);
        } catch (IOException ex) {
            System.out.println("[SG]:[WARNING] error saving " + n + ".yml");
        }
    }
}
