package com.exorath.game.api.action;

import org.bukkit.event.entity.PlayerDeathEvent;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class DieAction {
    
    public abstract void onDie( PlayerDeathEvent event, GamePlayer player, Game game );
    
    public static class Spectate extends DieAction {
        
        @Override
        public void onDie( PlayerDeathEvent event, GamePlayer player, Game game ) {
            game.getSpectateManager().setSpectating( player, true );
        }
        
    }
    
    public static class SendToServer extends DieAction {
        
        private String server;
        
        public SendToServer( String server ) {
            this.server = server;
        }
        
        @Override
        public void onDie( PlayerDeathEvent event, GamePlayer player, Game game ) {
            GameAPI.sendPlayerToServer( event.getEntity(), this.server );
        }
        
    }
    
}
