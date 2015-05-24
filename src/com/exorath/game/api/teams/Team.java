package com.exorath.game.api.teams;

import org.bukkit.Bukkit;

import com.exorath.game.api.Properties;

/**
 * Created by too on 23/05/2015.
 * Base object for a Team.
 */
public class Team {
    
    protected static final String DEFAULT_NAME = "team";
    protected static final int DEFAULT_MAX_PLAYER_AMOUNT = Bukkit.getServer().getMaxPlayers();
    private Properties properties = new Properties();
    
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
        this.properties.set( TeamProperty.TEAM_SIZE, amount );
    }
    
    public int getTeamSize() {
        return this.properties.as( TeamProperty.TEAM_SIZE, int.class );
    }

}
