/**
 * Name: ArenaStatus.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

/**
 * The Arena status. This is different from Game status
 */
public enum ArenaStatus {
    /**
     * ARENA DISABLED.
     */
    DISABLED,
    /**
     * ARENA IS READY TO BE USED
     */
    READY,
    /**
     * ARENA IS INGAME.
     */
    INGAME,
    /**
     * GAME FINISHED.
     */
    RELOAD,
    /**
     * UNKNOWN ERROR.
     */
    ERROR
}
