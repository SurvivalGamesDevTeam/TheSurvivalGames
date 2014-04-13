/**
 * Name: PlayersData.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.configs;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("PlayersData")
public class PlayersData implements ConfigurationSerializable {

    /**
     * Creates a Map representation of this class.
     * <p/>
     * This class must provide a method to restore this class, as defined in the
     * {@link org.bukkit.configuration.serialization.ConfigurationSerializable}
     * interface javadocs.
     * 
     * @return Map containing the current state of this class
     */
    @Override
    public Map<String, Object> serialize() {
        return null;
    }

    public static PlayersData deserialize(Map<String, Object> map) {

        return new PlayersData();
    }
}
