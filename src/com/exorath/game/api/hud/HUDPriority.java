package com.exorath.game.api.hud;

/**
 * Created by TOON on 8/9/2015.
 */
public enum HUDPriority {
    HIGHEST(5),
    HIGH(4),
    MEDIUM(3),
    GAME_API(2),
    LOW(1),
    LOWEST(0);

    private int priority;

    HUDPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
