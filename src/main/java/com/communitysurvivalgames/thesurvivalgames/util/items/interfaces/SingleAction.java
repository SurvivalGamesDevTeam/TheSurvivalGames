/**
 * Name: SingleAction.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

import java.util.List;

/**
 * The interface Single action.
 * <p>
 * Does the item only have a single Ability If they do what action sets it off
 * 
 */
public interface SingleAction {
    /**
     * Gets activators.
     * 
     * @return the activators
     */
    List<ActionActivator> getActivators();

}
