package com.exorath.game.api.action;

import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class JoinAction {
    
    public abstract void onJoin( PlayerJoinEvent event, GamePlayer player, Game game );
    
    public static class SpectateIngame extends JoinAction {
        
        @Override
        public void onJoin( PlayerJoinEvent event, GamePlayer player, Game game ) {
            switch ( game.getState() ) {
                case WAITING:
                case STARTING:
                    
                    break;
                case INGAME:
                case FINISHING:
                case RESETTING:
                case RESTARTING:
                    
                    break;
            }
        }
    }
    
}
