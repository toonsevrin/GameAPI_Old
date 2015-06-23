package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.exorath.game.api.Game;

/**
 * @author Nick Robson
 */
public class Players {
    
    private final Game game;
    
    private final Map<UUID, PlayerState> playerStates = new HashMap<>();
    
    public Players( Game game ) {
        this.game = game;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public PlayerState getPlayerState( GamePlayer player ) {
        PlayerState state = this.playerStates.get( player.getUUID() );
        if ( state == null ) {
            state = PlayerState.UNKNOWN;
            this.playerStates.put( player.getUUID(), state );
        }
        return state;
    }
    
    public boolean isPlaying( GamePlayer player ) {
        return this.getPlayerState( player ) == PlayerState.PLAYING;
    }
    
    public void setState( GamePlayer player, PlayerState state ) {
        this.playerStates.put( player.getUUID(), state );
    }
    
    public void add( GamePlayer player, PlayerState state ) {
        this.setState( player, state );
    }
    
    public void remove( GamePlayer player ) {
        this.setState( player, PlayerState.REMOVED );
    }
    
}
