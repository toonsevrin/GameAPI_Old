package com.exorath.game.api.teams;

import com.exorath.game.api.players.GPlayer;
import org.bukkit.Bukkit;

import com.exorath.game.api.Properties;

import javax.xml.stream.Location;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by too on 23/05/2015.
 * Base object for a Team.
 */
public class Team {
    
    protected static final String DEFAULT_NAME = "team";
    protected static final int DEFAULT_MAX_PLAYER_AMOUNT = Bukkit.getServer().getMaxPlayers();
    private Properties properties = new Properties();
    private Set<GPlayer> players = new HashSet<GPlayer>();

    public Set<GPlayer> getPlayers(){
        return players;
    }
    public void addPlayer(GPlayer player){
        players.add(player);
    }
    public boolean containsPlayer(GPlayer player){
        if(players.contains(player)) return true;
        return false;
    }
    public void removePlayer(GPlayer player){
        if(!players.contains(player)) return;
        players.remove(player);
    }
    public void startGame(){
        updatePlayers();
        for(GPlayer player : players){
            
        }
    }
    public void updatePlayers(){
        Set<GPlayer> tempPlayers = new HashSet<GPlayer>();
        for(GPlayer player : players){
            if(!player.isOnline()){
                tempPlayers.add(player);
            }
        }
        players.removeAll(tempPlayers);
    }
    public Properties getProperties() {
        return this.properties;
    }
    
    public void setName( String name ) {
        this.properties.set( TeamProperty.NAME, name );
    }
    
    public String getName() {
        return this.properties.as( TeamProperty.NAME, String.class );
    }
    
    public void setPvpEnabled( boolean enabled ) {
        this.properties.set( TeamProperty.PVP, enabled );
    }
    
    public boolean isPvpEnabled() {
        return this.properties.as( TeamProperty.PVP, boolean.class );
    }
    
    public void setTeamSize( int amount ) {
        this.properties.set( TeamProperty.SIZE, amount );
    }
    
    public int getTeamSize() {
        return this.properties.as( TeamProperty.SIZE, int.class );
    }

    public void addSpawnPoint( Location spawn ) {
        getSpawns().add(spawn);
    }

    public void removeSpawnPoint(Location spawn) {
        if(!getSpawns().contains(spawn)) return;
        getSpawns().remove(spawn);
    }
    public List<Location> getSpawns(){return this.properties.as(TeamProperty.SPAWNS, List.class);}


}
