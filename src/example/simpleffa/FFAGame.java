package example.simpleffa;

import com.exorath.game.api.GameProperty;
import com.exorath.game.api.action.DieAction;
import com.exorath.game.api.gametype.minigame.RepeatingMinigame;
import com.exorath.game.api.team.FreeForAllTeam;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;

/**
 * Created by TOON on 8/23/2015.
 * Random test to see what should be implemented of core
 */
public class FFAGame extends RepeatingMinigame {
    public FFAGame() {
        setName("Exorath DeathMatch");
        setDescription("Kill all other players in this free for all game to win.");
        getProperties().set(GameProperty.MAX_DURATION, 60);//TODO: Implement
        getProperties().set(GameProperty.ALLOW_SPECTATING, true); //TODO: Check if implemented

        setupTeams();
        setupMaps();
        setupLobby();

        getActions().setDieAction(new DieAction.Spectate());
    }

    public void setupTeams() {
        Team team = new FreeForAllTeam();
        team.setName("Players");
        team.setMinTeamSize(2); //TODO: Check if implemented
        team.setMaxTeamSize(16);//TODO: Check if implemented
        TeamManager.getInstance().addTeam(team);
    }

    /**
     * TODO: Confirm how Nick implemented maps
     */
    public void setupMaps() {
        getMapManager.addMap("mapName1");
        getMapManager.addMap("mapName2");
        getMapManager.addMap("mapName3");
        getMapManager.setMapCycling(MapCycler.VOTING);
    }

    public void setupLobby() {
        //Not required
    }

    //TODO: Find out why this is mandatory lol
    @Override
    public void finish() {

    }
}
