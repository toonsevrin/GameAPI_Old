package com.exorath.game.api.events;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Game.StopCause;

/**
 * @author Nick Robson
 */
public class GameStateChangedEvent extends GameEvent {

    private final GameState oldState, newState;
    private final StopCause stopCause;

    public GameStateChangedEvent(Game game, GameState oldState, GameState newState) {
        this(game, oldState, newState, null);
    }

    public GameStateChangedEvent(Game game, GameState oldState, GameState newState, StopCause stopCause) {
        super(game);
        this.oldState = oldState;
        this.newState = newState;
        this.stopCause = stopCause;
    }

    public GameState getOldState() {
        return this.oldState;
    }

    public GameState getNewState() {
        return this.newState;
    }

    public StopCause getStopCause() {
        return this.stopCause;
    }

}
