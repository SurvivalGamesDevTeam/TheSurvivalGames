/**
 * Name: ILore.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items.interfaces;

import java.util.List;

/**
 * Simple Interface to an create Lore
 */
public interface ILore extends IMetable {
    /**
     * Gets lore.
     * 
     * @return the lore
     */
    public List<String> getLore();

    /**
     * Add line.
     * 
     * @param line the line
     */
    public void addLoreLine(String line);
}
