package com.exorath.game.api;

import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.teams.TeamManager;

/**
 * Created by too on 23/05/2015.
 * Gamemode, RepeatingMinigame and Minigame will extend this.
 */

public abstract class Game {
    
    public static final String DEFAULT_GAME_NAME = "Game";
    
    private Properties properties = new Properties();
    private TeamManager teamManager = new TeamManager();
    private Lobby lobby = new Lobby();
    
    public Game() {
        //this.init();
    }
    
    /**
     * Basically the constructor of the Game object. This could be overwritten by Game's children.
     */
    protected void init() {
        this.properties = new Properties();
        this.lobby = new Lobby();
        this.teamManager = new TeamManager();
    }
    
    public Lobby getLobby() {
        return this.lobby;
    }
    
    public TeamManager getTeamManager() {
        return this.teamManager;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    protected void setLobby( Lobby lobby ) {
        this.lobby = lobby;
    }
    
    protected void setTeamManager( TeamManager teamManager ) {
        this.teamManager = teamManager;
    }
    
    protected void setProperties( Properties properties ) {
        this.properties = properties;
    }
    
    public void setName( String name ) {
        this.properties.set( GameProperty.NAME, name );
    }
    
    public String getName( String name ) {
        return this.properties.as( GameProperty.NAME, String.class );
    }
}
