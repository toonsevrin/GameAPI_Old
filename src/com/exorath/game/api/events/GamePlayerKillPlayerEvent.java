package com.exorath.game.api.events;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public class GamePlayerKillPlayerEvent extends GamePlayerEvent {
    
    private final GamePlayer killer;
    
    public GamePlayerKillPlayerEvent( Game game, GamePlayer dead, GamePlayer killer ) {
        super( game, dead );
        this.killer = killer;
    }
    
    public GamePlayer getKiller() {
        return this.killer;
    }
    
}
