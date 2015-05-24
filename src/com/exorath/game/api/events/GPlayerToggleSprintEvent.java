package com.exorath.game.api.events;

import org.bukkit.event.HandlerList;

/**
 * Created by too on 24/05/2015.
 * TODO: Create this event
 */
public class GPlayerToggleSprintEvent extends GEvent {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
