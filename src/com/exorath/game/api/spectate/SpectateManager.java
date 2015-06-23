package com.exorath.game.api.spectate;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerState;

/**
 * @author Nick Robson
 */
public class SpectateManager {
    
    private Game game;
    
    public SpectateManager( Game game ) {
        this.game = game;
    }
    
    public Game getGame() {
        return this.game;
    }
    
    public boolean isSpectating( GamePlayer player ) {
        return this.game.getPlayers().getPlayerState( player ) == PlayerState.SPECTATING;
    }
    
    public void setSpectating( GamePlayer player, boolean spectating ) {
        if ( spectating ) {
            this.addSpectator( player );
        } else {
            this.removeSpectator( player );
        }
    }
    
    public void addSpectator( GamePlayer player ) {
        this.game.getPlayers().setState( player, PlayerState.SPECTATING );
    }
    
    public void removeSpectator( GamePlayer player ) {
        this.game.getPlayers().remove( player );
    }
    
}
