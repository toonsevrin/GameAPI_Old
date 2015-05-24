package com.exorath.game.api.teams;

import org.bukkit.Bukkit;

/**
 * Created by too on 23/05/2015.
 * Base object for a Team.
 */
public class Team {
    protected static final String DEFAULT_NAME = "team";
    protected static final int DEFAULT_MAX_PLAYER_AMOUNT = Bukkit.getServer().getMaxPlayers();
    private TeamProperties properties;
    public Team(){
        properties = new TeamProperties();
    }
    public TeamProperties getProperties(){
        return properties;
    }
    public void setName(String name){
        properties.set(TeamProperty.NAME.getKey(), name);
    }
    public String getName(){
        return (String) properties.get(TeamProperty.NAME, DEFAULT_NAME);
    }
    public void setPvpEnabled(boolean enabled){
        properties.set(TeamProperty.PVP, enabled);
    }
    public boolean isPvpEnabled() {
        return (boolean) properties.get(TeamProperty.PVP, Boolean.FALSE);
    }
    public void setTeamSize(int amount){
        properties.set(TeamProperty.TEAM_SIZE, amount);
    }
    public int getTeamSize(){
        return (int) properties.get(TeamProperty.TEAM_SIZE, DEFAULT_MAX_PLAYER_AMOUNT);
    }
}
