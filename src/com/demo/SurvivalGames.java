package com.demo;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.gametypes.RepeatingMinigame;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.team.FreeForAllTeam;
import com.exorath.game.api.team.TeamManager;

/**
 * Created by too on 27/05/2015.
 * An example gamemode
 */
public class SurvivalGames extends RepeatingMinigame{
    public SurvivalGames(){
        setupProperties();
        setupLobby();
        setupTeams();

        startGame();
    }
    private void setupProperties(){
        this.setName("Survival Games");
        this.setDescription("Tributes fight to the death in the dangerous arena.");
    }
    private void setupLobby(){
        getLobby().enable();
        getLobby().setSpawnLocation(0, 60, 0);
        //TODO: Add kit npc's to lobby
    }
    private void setupTeams(){
        getTeamManager().addTeam(new FreeForAllTeam());
    }
}
