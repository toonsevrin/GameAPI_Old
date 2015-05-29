package com.exorath.game.api.spectate;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public class SpectateManager {
    
    private Game game;
    
    public SpectateManager( Game game ) {
        this.game = game;
    }
    
    public boolean isSpectating( GamePlayer player ) {
        // TODO
        return false;
    }
    
    public void setSpectating( GamePlayer player, boolean spectating ) {
        if ( spectating ) {
            this.addSpectator( player );
        } else {
            this.removeSpectator( player );
        }
    }
    
    public void addSpectator( GamePlayer player ) {
        // TODO
    }
    
    public void removeSpectator( GamePlayer player ) {
        // TODO
    }
    
}
