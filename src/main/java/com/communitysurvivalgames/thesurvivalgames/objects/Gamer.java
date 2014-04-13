/**
 * Name: Gamer.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.objects;

import lombok.Data;
import com.communitysurvivalgames.thesurvivalgames.util.PlayerType;

/**
 * Gamer Lightweight storage of an player involved in a game. Records this
 * current game stats and holds data which removes the amount of for loop lookup
 * Only persists the duration of the game
 */
@Data
public class Gamer {
    private String Id;
    private PlayerType playerType;
    private String prefix;
    private String name;

    public Gamer(String n, String uuid) {
        this.name = n;
        this.Id = uuid;
    }

    public void setId(String uuid) {
        this.Id = uuid;
    }

    public void setPlayerType(PlayerType pt) {
        this.playerType = pt;
        this.prefix = pt.getType().replace("%player%", "Relicum");
    }

    private void setPrefix(String prefix) {

    }

}
