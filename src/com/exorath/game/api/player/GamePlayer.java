package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.exorath.game.api.Game;
import com.exorath.game.api.Properties;
import com.exorath.game.api.menu.Menu;
import com.exorath.game.lib.Rank;

public final class GamePlayer {
    
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
    private Rank rank = Rank.NONE;
    private int honor = 0, wonHonor = 0, credits = 0;
    private Properties properties = new Properties();
    private Menu menu;
    
    public GamePlayer( UUID id ) {
        this.uuid = id;
    }
    
    public GamePlayer( Player player ) {
        this( player.getUniqueId() );
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    protected void setProperties( Properties properties ) {
        this.properties = properties;
    }
    
    public UUID getUUID() {
        return this.uuid;
    }
    
    public OfflinePlayer getOfflinePlayer() {
        Player p = Bukkit.getPlayer( this.uuid );
        return p != null ? p : Bukkit.getOfflinePlayer( this.uuid );
    }
    
    public boolean isOnline() {
        OfflinePlayer offline = this.getOfflinePlayer();
        return offline == null ? false : offline.isOnline();
    }
    
    public Player getBukkitPlayer() {
        OfflinePlayer player = this.getOfflinePlayer();
        if ( player != null && player.isOnline() ) {
            return player.getPlayer();
        }
        return null;
    }
    
    public boolean isAlive( Game game ) {
        return game.getPlayers().getPlayerState( this ) == PlayerState.PLAYING;
    }
    
    public Rank getRank() {
        return this.rank;
    }
    
    public void setRank( Rank rank ) {
        this.rank = rank;
    }
    
    public int getCredits() {
        return this.credits;
    }
    
    public void addCredits( int credits ) {
        this.credits += credits;
    }
    
    public void removeCredits( int credits ) {
        this.credits -= credits;
    }
    
    public boolean hasCredits( int credits ) {
        return this.credits >= credits;
    }
    
    public int getHonorPoints() {
        return this.honor;
    }
    
    public int getWonHonorPoints() {
        return this.wonHonor;
    }
    
    public void addHonorPoints( int honorPoints ) {
        this.honor += honorPoints;
        this.wonHonor += honorPoints;
    }
    
    public void removeHonorPoints( int honorPoints ) {
        this.honor -= honorPoints;
        this.wonHonor -= honorPoints;
    }
    
    public boolean hasHonorPoints( int honorPoints ) {
        return this.honor >= honorPoints;
    }
    
    public void sendMessage( String message ) {
        Player p = this.getBukkitPlayer();
        if ( p != null ) {
            p.sendMessage( message );
        }
    }
    
    public void sendMessage( String format, Object... params ) {
        Player p = this.getBukkitPlayer();
        if ( p != null ) {
            p.sendMessage( String.format( format, params ) );
        }
    }
    
    public PlayerState getState( Game game ) {
        return game.getPlayers().getPlayerState( this );
    }
    
    public boolean openMenu( Menu menu ) {
        this.menu = menu;
        Player p = this.getBukkitPlayer();
        if ( p != null && p.getOpenInventory() == null ) { //  we don't want to aggressively open a menu if they shouldn't be opening...
            Inventory inv = Bukkit.createInventory( null, menu.getSize() );
            menu.dump( inv );
            p.openInventory( inv );
            return true;
        }
        return false;
    }
    
    public boolean closeMenu() {
        Player p = this.getBukkitPlayer();
        if ( p != null ) {
            if ( this.menu != null ) {
                p.closeInventory();
            } else {
                return false;
            }
        }
        this.menu = null;
        if ( p == null ) {
            return false;
        }
        return true;
    }
    
    public Menu getCurrentMenu() {
        return this.menu;
    }
    
}
