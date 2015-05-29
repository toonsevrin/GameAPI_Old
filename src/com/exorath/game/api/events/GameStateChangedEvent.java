package com.exorath.game.api.events;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameState;

/**
 * @author Nick Robson
 */
public class GameStateChangedEvent extends GameEvent {
    
    private final GameState oldState, newState;
    
    public GameStateChangedEvent( Game game, GameState oldState, GameState newState ) {
        super( game );
        this.oldState = oldState;
        this.newState = newState;
    }
    
    public GameState getOldState() {
        return this.oldState;
    }
    
    public GameState getNewState() {
        return this.newState;
    }
    
}
