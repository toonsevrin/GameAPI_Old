package com.exorath.game.api.team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.exorath.game.GameAPI;
import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Properties;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;

/**
 * Created by too on 23/05/2015.
 * Base object for a Team.
 */
public class Team {

    protected static final String DEFAULT_NAME = "team";
    protected static final int DEFAULT_MAX_PLAYER_AMOUNT = Bukkit.getServer().getMaxPlayers();

    public static Map<TeamColor, Team> teams = Maps.newHashMap();

    public static Team getTeam( TeamColor color, boolean create ) {
        if ( create && !Team.teams.containsKey( color ) ) {
            Team.teams.put( color, new Team().setTeamColor( color ) );
        }
        return Team.teams.get( color );
    }

    private Properties properties = new Properties();

    private Set<UUID> players = new HashSet<>();
    private Set<UUID> activePlayers = new HashSet<>();

    private final Set<GameListener> listeners = new HashSet<>();

    public Team() {
        //TODO: This should be set with the map system
        this.properties.set(TeamProperty.SPAWNS, new ArrayList<Location>());
    }

    /**
     * @return A collection of all online players in this team
     */
    public Set<GamePlayer> getPlayers() {
        return this.players.stream().map( u -> GameAPI.getPlayer( u ) ).collect( Collectors.toSet() );
    }

    /**
     * @return a collection of all online players in this team which haven't been set inactive
     */
    public Set<UUID> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Adds a player to the team
     */
    public void addPlayer(GamePlayer player) {
        players.add( player.getUUID() );
        activePlayers.add( player.getUUID() );
    }

    /**
     * @return Checks if the given player is in this team
     */
    public boolean containsPlayer(GamePlayer player) {
        return players.contains(player);
    }

    /**
     * @return Checks if the given player is in this team and still active
     */
    public boolean containsActivePlayer(GamePlayer player) {
        return players.contains(player);
    }

    /**
     * Removes player from this team completely
     */
    public void removePlayer(GamePlayer player) {
        if (players.contains(player))
            players.remove(player);
        if (activePlayers.contains(player))
            activePlayers.remove(player);
    }

    /**
     * Removes all offline players from this team
     */
    public void removeOfflinePlayers() {
        Iterator<UUID> it = this.players.iterator();
        while (it.hasNext()) {
            UUID uuid = it.next();
            if ( Bukkit.getPlayer( uuid ) == null ) {
                it.remove();
                if ( activePlayers.contains( uuid ) )
                    activePlayers.remove( uuid );
            }
        }
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Team setName( String name ) {
        this.properties.set( TeamProperty.NAME, name );
        return this;
    }

    public String getName() {
        return this.properties.as(TeamProperty.NAME, String.class);
    }

    public Team setPvpEnabled( boolean enabled ) {
        this.properties.set( BasePlayerProperty.PVP, enabled );
        return this;
    }

    public boolean isPvpEnabled() {
        return this.properties.as(BasePlayerProperty.PVP, boolean.class);
    }

    public Team setMaxTeamSize( int amount ) {
        this.properties.set( TeamProperty.MAX_SIZE, amount );
        return this;
    }

    public int getMaxTeamSize() {
        return this.properties.as(TeamProperty.MAX_SIZE, int.class);
    }

    public Team setMinTeamSize( int amount ) {
        this.properties.set( TeamProperty.MIN_SIZE, amount );
        return this;
    }

    public int getMinTeamSize() {
        return this.properties.as(TeamProperty.MIN_SIZE, int.class);
    }

    public boolean isFriendlyFire() {
        return this.properties.as(TeamProperty.FRIENDLY_FIRE, boolean.class);
    }

    public Team setFriendlyFire( boolean ff ) {
        this.properties.set( TeamProperty.FRIENDLY_FIRE, ff );
        return this;
    }

    public Team addSpawnPoint( GameMap map, Location spawn ) {
        this.getSpawns( map ).add( spawn );
        return this;
    }

    public Team removeSpawnPoint( GameMap map, Location spawn ) {
        if ( !this.getSpawns( map ).contains( spawn ) ) {
            return this;
        }
        this.getSpawns( map ).remove( spawn );
        return this;
    }

    @SuppressWarnings( "unchecked" )
    public List<Location> getSpawns( GameMap map ) {
        return this.properties.as(TeamProperty.SPAWNS, List.class);
    }

    public TeamColor getTeamColor() {
        return this.properties.as( TeamProperty.COLOR, TeamColor.class );
    }

    public Team setTeamColor( TeamColor color ) {
        this.properties.set( TeamProperty.COLOR, color );
        return this;
    }

    public Set<GameListener> getListeners() {
        return listeners;
    }

    public void addListener( GameListener listener ) {
        if ( listener != null ) {
            this.listeners.add( listener );
        }
    }

}
