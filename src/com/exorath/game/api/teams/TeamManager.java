package com.exorath.game.api.teams;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by too on 23/05/2015.
 * Manager instantiated in Game class. Manages the creation and implementation of teams.
 */
public class TeamManager {
    private Set<Team> teams = new HashSet<Team>();
    private boolean defaultTeam = true;

    public TeamManager() {
        teams.add(new DefaultTeam());
    }

    /**
     * Add team to player teams.
     * @param team team to add to the teams
     */
    public void addTeam(Team team){
        deleteDefaultTeam();
        teams.add(team);
    }
    /**
     * Remove team from player teams.
     * @param team team to remove from the teams
     */
    public void removeTeam(Team team){
        if(teams.contains(team)) teams.remove(team);
    }

    /**
     *
     * @return the teams that are added in the TeamManager.
     */
    public Set<Team> getTeams(){
        return teams;
    }

    /**
     * if there is one team and its a DefaultTeam remove it. DefaultTeam only exists while no other teams are added.
     */
    private void deleteDefaultTeam(){
       if(!defaultTeam) return;
        teams.clear();
        defaultTeam = false;
    }
}
