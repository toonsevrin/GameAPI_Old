package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.exorath.game.api.Properties;
import com.exorath.game.lib.Rank;

/**
 * Created by too on 23/05/2015.
 * Base player object in GameAPI
 */
public class GamePlayer {
    
    private static final List<UUID> PLAYER_CACHE_ADDED = new LinkedList<>();
    private static final Map<UUID, GamePlayer> PLAYER_CACHE = new HashMap<>( 150 );
    
    public static GamePlayer fromUUID( UUID uuid ) {
        GamePlayer player = GamePlayer.PLAYER_CACHE.get( uuid );
        if ( player == null ) {
            player = new GamePlayer( uuid );
            if ( GamePlayer.PLAYER_CACHE_ADDED.size() == 150 ) {
                int i = 0;
                UUID u = null;
                while ( u == null || Bukkit.getPlayer( u ) != null ) {
                    u = GamePlayer.PLAYER_CACHE_ADDED.get( i++ );
                }
                GamePlayer.PLAYER_CACHE_ADDED.remove( u );
                GamePlayer.PLAYER_CACHE.remove( u );
            }
            GamePlayer.PLAYER_CACHE_ADDED.add( uuid );
            GamePlayer.PLAYER_CACHE.put( uuid, player );
        }
        return player;
    }
    
    private UUID uuid;
    private Properties properties = new Properties();
    
    public GamePlayer( UUID id ) {
        this.uuid = id;
    }
    
    public GamePlayer( Player player ) {
        this( player.getUniqueId() );
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public OfflinePlayer getOfflinePlayer() {
        Player p = Bukkit.getPlayer( this.uuid );
        return p != null ? p : Bukkit.getOfflinePlayer( this.uuid );
    }
    
    public boolean isOnline() {
        return this.getOfflinePlayer().isOnline();
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
    
    //TODO: ADD CONTENT TO THESE METHODS!!
    public Rank getRank() {
        return Rank.NONE;
    }
    
    public void setRank( Rank rank ) {}
    
    //Start currency functions
    //TODO: Implement currency functions
    public int getHonorPoints() {
        return 0;
    }
    
    public int getCredits() {
        return 0;
    }
    
    public void addCredits( int credits ) {}
    
    public void removeCredits( int credits ) {}
    
    public boolean hasCredits( int credits ) {
        return true;
    }
    
    public void addHonorPoints( int honorPoints ) {}
    
    public void removeHonorPoints( int honorPoints ) {}
    
    public boolean hasHonorPoints( int honorPoints ) {
        return true;
    }
    //End currency functions
    
}
