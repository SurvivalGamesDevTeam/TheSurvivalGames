package com.communitysurvivalgames.thesurvivalgames.objects;

import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;

public class MapHash {
    private final int id;
    private final SGWorld w;
    public MapHash(SGWorld w, int id) {
        this.w = w;
        this.id = id;
    }
    public SGWorld getWorld() {
        return w;
    }
    public int getId() {
        return id;
    }
}