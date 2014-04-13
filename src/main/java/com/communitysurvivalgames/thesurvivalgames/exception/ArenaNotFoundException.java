package com.communitysurvivalgames.thesurvivalgames.exception;

public class ArenaNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ArenaNotFoundException() {
        super("Could not find given arena with given Player or ID");
    }

    public ArenaNotFoundException(String message) {
        super(message);
    }

}
