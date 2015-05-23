package com.exorath.gameAPI.game;

import com.exorath.gameAPI.game.lobby.Lobby;
import com.exorath.gameAPI.game.teams.TeamManager;
import com.exorath.gameAPI.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Gamemode, RepeatingMinigame and Minigame will extend this.
 */

public abstract class Game {
    private Properties properties;

    private Lobby lobby;

    private TeamManager teamManager;

    public Game() {
        init();
    }

    /**
     * Basically the constructor of the Game object. This could be overwritten by Game's children.
     */
    protected void init(){
        properties = new Properties();
        lobby = new Lobby();
        teamManager = new TeamManager();
    }

    public Lobby getLobby() {
        return lobby;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }
    public Properties getProperties(){
        return properties;
    }
    protected void setLobby(Lobby lobby){
        this.lobby = lobby;
    }
    protected void setTeamManager(TeamManager teamManager){
        this.teamManager = teamManager;
    }
    protected void setProperties(Properties properties){
        this.properties = properties;
    }
}
