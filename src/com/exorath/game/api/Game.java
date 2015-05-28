package com.exorath.game.api;

import com.exorath.game.GameAPI;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.teams.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;


/**
 * Created by too on 23/05/2015.
 * Gamemode, RepeatingMinigame and Minigame will extend this.
 */

public abstract class Game implements Listener {

    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "A fun to play game!";

    private Properties properties = new Properties();
    private TeamManager teamManager = new TeamManager();
    private Lobby lobby = new Lobby();

    private boolean running = false;

    public Game() {
        //register event handler for this game
        Bukkit.getPluginManager().registerEvents(this, GameAPI.getInstance());
        //this.init();

    }

    protected void init() {
        this.properties = new Properties();
        this.lobby = new Lobby();
        this.teamManager = new TeamManager();
    }
    public void startGame(){
        if(running) return;
        running = true;
    }
    public void stopGame() {
        running = false;
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
    public void setDescription( String description ) {
        this.properties.set( GameProperty.DESCRIPTION, description );
    }

    public String getDescription( String description ) {
        return this.properties.as( GameProperty.DESCRIPTION, String.class );
    }
    public boolean isStarted(){
        return running;
    }
}
