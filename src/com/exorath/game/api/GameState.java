package com.exorath.game.api;

public enum GameState {

    WAITING,
    STARTING,
    INGAME,
    FINISHING,
    RESETTING,
    RESTARTING;

    public boolean is(GameState... states) {
        for (GameState state : states)
            if (state == this)
                return true;
        return false;
    }

    @Override
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }

}
