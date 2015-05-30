package com.exorath.game.api.events;

import com.exorath.game.api.players.GPlayer;
import org.bukkit.event.HandlerList;

/**
 * Created by too on 24/05/2015.
 * TODO: Create this event
 */
public class GPlayerRespawnEvent extends GPlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    public GPlayerRespawnEvent(GPlayer player){
        super(player);
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
