package com.exorath.game.api.action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.exorath.game.GameAPI;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 * @author Nick Robson
 */
public abstract class GameEndAction {
    
    public abstract void end();
    
    public static class SendToServer extends GameEndAction {
        
        private String server;
        
        public SendToServer( String server ) {
            this.server = server;
        }
        
        @Override
        public void end() {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF( "Connect" );
            out.writeUTF( this.server );
            byte[] bytes = out.toByteArray();
            if ( !Bukkit.getMessenger().isOutgoingChannelRegistered( GameAPI.getInstance(), "BungeeCord" ) ) {
                Bukkit.getMessenger().registerOutgoingPluginChannel( GameAPI.getInstance(), "BungeeCord" );
            }
            for ( Player player : Bukkit.getOnlinePlayers() ) {
                player.sendPluginMessage( GameAPI.getInstance(), "BungeeCord", bytes );
            }
        }
    }
    
}
