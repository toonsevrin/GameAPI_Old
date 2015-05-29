package com.exorath.game.api.team;

import java.util.HashSet;
import java.util.Set;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.yoshigenius.lib.util.GameUtil;

/**
 * Created by too on 23/05/2015.
 * Manager instantiated in Game class. Manages the creation and implementation of teams.
 */
public class TeamManager {
    
    private Set<Team> teams = new HashSet<Team>();
    private boolean defaultTeam = true;
    private Game game;
    
    public TeamManager( Game game ) {
        this.game = game;
        this.teams.add( new DefaultTeam() );
    }
    
    /**
     * Add team to player teams.
     * 
     * @param team
     *            team to add to the teams
     */
    public void addTeam( Team team ) {
        this.deleteDefaultTeam();
        this.teams.add( team );
    }
    
    /**
     * Remove team from player teams.
     * 
     * @param team
     *            team to remove from the teams
     */
    public void removeTeam( Team team ) {
        if ( this.teams.contains( team ) ) {
            this.teams.remove( team );
        }
    }
    
    /**
     * @return the teams that are added in the TeamManager.
     */
    public Set<Team> getTeams() {
        return this.teams;
    }
    
    /**
     * if there is one team and its a DefaultTeam remove it. DefaultTeam only exists while no other
     * teams are added.
     */
    private void deleteDefaultTeam() {
        if ( !this.defaultTeam ) {
            return;
        }
        this.teams.clear();
        this.defaultTeam = false;
    }
    
    public void startGame() {
        for ( Team team : this.teams ) {
            int spawn = 0;
            for ( GamePlayer player : team.getPlayers() ) {
                spawn = GameUtil.cycle( spawn, team.getSpawns().size() - 1 );
                
            }
        }
    }
}
