package com.exorath.game.api.team;

import com.exorath.game.api.BasePlayerProperty;

/**
 * Created by too on 24/05/2015.
 * Placeholder team until teams are created, this is for minimizing code amount
 */
public class FreeForAllTeam extends Team {

    private static final String DEFAULT_TEAM_NAME = "Free for All Team";

    public FreeForAllTeam() {
        this.getProperties().set(TeamProperty.NAME, FreeForAllTeam.DEFAULT_TEAM_NAME);
        this.getProperties().set(BasePlayerProperty.PVP, true);
    }

}
