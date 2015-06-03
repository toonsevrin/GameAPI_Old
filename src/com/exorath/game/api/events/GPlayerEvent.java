package com.exorath.game.api.events;

import com.exorath.game.api.players.GPlayer;

/**
 * Created by too on 30/05/2015.
 */
public abstract class GPlayerEvent extends GEvent {
    private GPlayer player;

    public GPlayerEvent(GPlayer player) {
        this.player = player;
    }
    public GPlayer getPlayer(){
        return player;
    }
}
