package com.exorath.game.api.events;

import com.exorath.game.api.Game;

/**
 * @author Nick Robson
 */
public abstract class GameEvent {
    
    private Game game;
    
    public GameEvent( Game game ) {
        this.game = game;
    }
    
    public Game getGame() {
        return this.game;
    }
    
}
