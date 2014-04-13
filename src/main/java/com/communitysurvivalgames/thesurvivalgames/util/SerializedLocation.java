/**
 * Name: SerializedLocation.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.util.Vector;

/**
 * SerializedLocation Used to store Locations in a standard Configuration file
 * <p>
 * You are not required to call the serialize and deserialize when saving or
 * loading the Location
 * 
 * @author TheCommunitySurvivalGames
 * @version 0.0.2
 */
@SerializableAs("SerializedLocation")
public class SerializedLocation implements ConfigurationSerializable {

    private final int x;
    private final int y;
    private final int z;
    private final float yaw;
    private final float pitch;
    private final String world;
    private final String type;

    /**
     * Instantiates a new Serialized location.
     * 
     * @param world the world
     * @param x the x
     * @param y the y
     * @param z the z
     * @param yaw the yaw
     * @param pitch the pitch
     */
    public SerializedLocation(String world, int x, int y, int z, float yaw, float pitch, LocationType locationType) {
       this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
        this.type = locationType.name();
   }

    /**
     * Instantiates a new Serialized location.
     * 
     * @param location the location
     */
    public SerializedLocation(Location location, LocationType locationType) {
      this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getName();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.type = locationType.name();
 }

    /**
     * Deserialize serialized location. <b>Should never need to call this
     * directly</b>
     * 
     * @param map the map
     * @return the serialized location
     */
    public static SerializedLocation deserialize(Map<String, Object> map) {
        Object xObject = map.get("xpos"), yObject = map.get("ypos"), zObject = map.get("zpos"), worldObject = map.get("world"), yawObject = map.get("yawpos"), pitchObject = map.get("pitchpos"), pType = map.get("type");
  if (xObject == null || yObject == null || zObject == null || worldObject == null || !(xObject instanceof Integer) || !(yObject instanceof Integer)
                || !(zObject instanceof Integer)) {
            return null;
        }
        Integer x = (Integer) xObject, y = (Integer) yObject, z = (Integer) zObject;
        Double yaw = (Double) yawObject, pitch = (Double) pitchObject;
        String worldString = worldObject.toString();
        LocationType ty = LocationType.valueOf((String) pType);
        return new SerializedLocation(worldString, x, y, z, yaw.floatValue(), pitch.floatValue(), ty);

    }

   /**
     * Gets {@link org.bukkit.Location}
     * 
     * @return the location
     */
    public Location getLocation() {
        return new Location(Bukkit.getServer().getWorld(world), x + 0.5, y + 0.5, z + 0.5, yaw, pitch);
    }

    /**
     * Get the location as a {@link org.bukkit.util.Vector}
     * 
     * @return the vector
     */
    public Vector getVector() {
        return new Vector(x, y, z);
    }

    /**
     * Serialize map.
     * 
     * <b>This should never be called directly</b>
     * 
     * @return the map ready to be serialised
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("world", world);
        map.put("xpos", x);
        map.put("ypos", y);
        map.put("zpos", z);
        map.put("yawpos", yaw);
        map.put("pitchpos", pitch);
        map.put("type", type);
        return map;
    }

    /**
     * Get the X coordinate of the {@link org.bukkit.Location}
     * 
     * @return the x coordinate as an int
     */
    public int getX() {
        return x;
    }

    /**
     * Gets world.
     * 
     * @return the world as a string
     */
    public String getWorld() {
        return world;
    }

    /**
     * Get the Z coordinate of the {@link org.bukkit.Location}
     * 
     * @return the z coordinate as an int
     */
    public int getZ() {
        return z;
    }

    /**
     * Get the Y coordinate of the {@link org.bukkit.Location}
     * 
     * @return the y coordinate as an int
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the yaw to set the {@link org.bukkit.Location} to
     * 
     * @return the yaw as a float
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Gets the pitch to set the {@link org.bukkit.Location} to
     * 
     * @return the pitch as a float
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Get {@link com.communitysurvivalgames.thesurvivalgames.util.LocationType}
     * of this {@link org.bukkit.Location}
     * 
     * @return the location type
     */
    public LocationType getLocationType() {
        return LocationType.valueOf(this.type);
    }

    /**
     * String representation of SerializedLocation
     * 
     * @return the string
     */
    @Override
    public String toString() {
        return "SerializedLocation:world=" + getWorld() + ":x=" + getX() + ":y=" + getY() + ":z=" + getZ() + ":yaw=" + getYaw() + ":pitch=" + getPitch();
    }
}
