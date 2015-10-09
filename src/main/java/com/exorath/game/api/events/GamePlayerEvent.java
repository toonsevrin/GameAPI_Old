package com.exorath.game.api.events;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class GamePlayerEvent extends GameEvent {

    private final GamePlayer player;

    public GamePlayerEvent(Game game, GamePlayer player) {
        super(game);
        this.player = player;
    }

    public GamePlayer getPlayer() {
        return this.player;
    }

}
