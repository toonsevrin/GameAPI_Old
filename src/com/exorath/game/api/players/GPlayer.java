package com.exorath.game.api.players;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.exorath.game.api.Properties;

/**
 * Created by too on 23/05/2015.
 * Base player object in GameAPI
 */
public class GPlayer {
    
    private UUID uuid;
    private Properties properties = new Properties();
    
    public GPlayer( UUID id ) {
        this.uuid = id;
    }
    
    public GPlayer( Player player ) {
        this( player.getUniqueId() );
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer( this.uuid );
    }
    
    public Player getBukkitPlayer() {
        OfflinePlayer player = this.getOfflinePlayer();
        if ( player != null && player.isOnline() ) {
            return player.getPlayer();
        }
        return null;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    protected void setProperties( Properties properties ) {
        this.properties = properties;
    }
    
}
