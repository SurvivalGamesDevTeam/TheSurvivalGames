/**
 * Name: MultiAction.java Edited: 20 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

import java.util.List;

/**
 * The interface Multi action.
 * <p>
 * Does the item have more than one Ability If they do what action sets each one
 * off
 * <p>
 * Select the activators from
 * {@link com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.ActionActivator}
 * 
 * @author TheCommunitySurvivalGames
 * @version 0.0.1
 */
public interface MultiAction {
    /**
     * Gets Activators for action 1 An action can have more than one activator
     * 
     * @see {@link com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.ActionActivator}
     *      *
     * @return the action 1 activators as a list
     */
    List<ActionActivator> getAction1();

    /**
     * Gets Activators for action 2 An action can have more than one activator
     * 
     * @see {@link com.communitysurvivalgames.thesurvivalgames.util.items.interfaces.ActionActivator}
     * 
     * @return the action 2 activators as a list
     */
    List<ActionActivator> getAction2();
}
