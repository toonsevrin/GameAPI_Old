package com.exorath.game.api.gamestates;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Manager;
import com.exorath.game.api.events.GameStateChangedEvent;

/**
 * Created by Toon Sevrin on 8/25/2015.
 */
public class StateManager implements Manager {
    private static StateManager instance;
    private Game game;

    private GameState state = GameState.WAITING;

    public StateManager(Game game) {
        instance = this;
        this.game = game;
    }
    public static StateManager getInstance(){
        return instance;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState(GameState state) {
        GameState old = this.state;
        this.state = state;
        GameStateChangedEvent event = new GameStateChangedEvent(game, old, state);
        for (GameListener listener : game.getListeners())
            listener.onGameStateChange(event);
    }
    protected void startGame() {
        this.setState(GameState.STARTING);
    }

    protected void stopGame() {
        this.setState(GameState.FINISHING);
    }
}
