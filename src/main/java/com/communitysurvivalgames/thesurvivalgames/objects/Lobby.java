/**
 * Name: Lobby.java Edited: 21 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

import com.communitysurvivalgames.thesurvivalgames.util.LocationChecker;

public class Lobby {

    private LocationChecker locationChecker;

    /**
     * Gets location checker.
     * 
     * @return the location checker
     */
    public LocationChecker getLocationChecker() {
        return locationChecker;
    }

    /**
     * Sets location checker.
     * 
     * @param locationChecker the location checker
     */
    public void setLocationChecker(LocationChecker locationChecker) {
        this.locationChecker = locationChecker;
    }
}
