package com.exorath.game.api.teams;

/**
 * Created by too on 24/05/2015.
 * Placeholder team until teams are created, this is for minimizing code amount
 */
public class FreeForAllTeam extends Team{
    private static final String DEFAULT_TEAM_NAME = "Free for All Team";
    public FreeForAllTeam(){
        getProperties().set(TeamProperty.NAME, DEFAULT_TEAM_NAME);
        getProperties().set(TeamProperty.PVP, true);
    }
}
