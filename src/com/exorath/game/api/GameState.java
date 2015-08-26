package com.exorath.game.api;

public enum GameState {

    WAITING,
    STARTING,
    INGAME,
    FINISHING,
    RESETTING,
    RESTARTING;

    public boolean is(GameState state) {
        if (state == this)
            return true;
        return false;
    }

}
