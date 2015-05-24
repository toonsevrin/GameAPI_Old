package com.exorath.game.api.teams;

/**
 * Created by too on 24/05/2015.
 * Placeholder team until teams are created, this is for minimizing code amount
 */
public class DefaultTeam extends Team{
    private static final String DEFAULT_TEAM_NAME = "Default team";
    public DefaultTeam(){
        getProperties().set(TeamProperty.NAME, DEFAULT_TEAM_NAME);
        getProperties().set(TeamProperty.INTERACT, true);
    }
}
