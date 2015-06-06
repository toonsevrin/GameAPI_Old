package example.survivalgames;

import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.behaviour.HungerBehaviour;
import com.exorath.game.api.behaviour.LeaveBehaviour;
import com.exorath.game.api.gametype.RepeatingMinigame;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.team.FreeForAllTeam;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamProperty;
import javafx.geometry.Point3D;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by too on 27/05/2015.
 * An example gamemode
 */
public class SurvivalGames extends RepeatingMinigame {
    private List<Location> spawns = new ArrayList<Location>();

    public SurvivalGames() {
        setupGameProperties();
        setupLobby();
        setupSpawns();
        setupTeams();
        setupGame();
    }

    /**
     * Sets the base properties and behaviour in this game up
     */
    private void setupGameProperties(){
        /*Game properties*/
        this.setName( "Survival Games" ); //Name of the gamemode
        this.setDescription( "Tributes fight to the death in the arena." ); //Description of the gamemode
        this.getProperties().set(RepeatingGameProperty.COOLDOWN_TIME, 60); //Minimum amount of seconds between two games
        this.getProperties().set(GameProperty.MAX_DURATION, 900); //Set the max game duration to 15 minutes, after this time game will be terminated.
        this.getProperties().set(GameProperty.ALLOW_SPECTATING, true); //Set the max game duration to 15 minutes, after this time game will be terminated.

        /*Player properties*/
        this.getProperties().set(BasePlayerProperty.HUNGER, HungerBehaviour.DEFAULT);
        this.setLeaveBehaviour(LeaveBehaviour.REMOVE);

        /*Actions*/
        this.getActions().setDieAction(new GameAction.Spectate()); //This is to avoid having to write basic actions again on each gamemode.
        this.getActions().setJoinAction(new GameAction.SpectateIngame()); //This is to avoid having to write basic actions again on each gamemode.
        this.getActions().setFinishAction(new GameAction.kickSpectators()); //This is to avoid having to write basic actions again on each gamemode.
    }

    /**
     * Sets the lobby in this game up
     */
    private void setupLobby(){
        this.getLobby().enable(); //Enable the lobby
        this.getLobby().setSpawnLocation(0,60,0); //Spawn location in the lobby

        this.getLobby().addNPC(new SpectatorNPC(), 0, 60, 10);//When this npc gets right clicked, you become a spectator.

        setupKits();
    }

    /**
     * Setup the player spawn points inGame
     */
    private void setupSpawns(){
        int[][] locs = new int[][]{ //TODO: Use the config for this.
                {-1,60,-2},{-1,60,-1},{-1,60,0},{-1,60,1},
                {0,60,-2},{0,60,-1},{0,60,0},{0,60,1},
                {1,60,-2},{1,60,-1},{1,60,0},{1,60,1},
                {2,60,-2},{2,60,-1},{2,60,0},{2,60,1},
        };
        for(int[] coords : locs){
            spawns.add(new Location(this.getBaseGameWorld(), coords[0], coords[1], coords[2]));
        }
    }
    /**
     * Sets the teams in this game up
     */
    private void setupTeams(){
        FreeForAllTeam team = new FreeForAllTeam(); //The base team where all players will be put into
        team.getProperties().set(TeamProperty.SPAWNS, spawns); //Set the spawn points of the players. setupSpawns() generates these spawns.
        team.setMinTeamSize(8); //Set the minimum team size before countdown.
        team.setMaxTeamSize(16); //set the maximum team size.

        this.getTeamManager().addTeam(team);
    }
    /**
     * Any other things that have to be setup
     */
    private void setupGame(){
        this.addListener( new SGListener()); //Adds a custom event listener (See SGListener class).
    }

    /**
     * Sets the kits in this game up, also creates some NPCs in the lobby.
     */
    private void setupKits(){
        SGKits.WarriorKit warriorKit = new SGKits.WarriorKit(); //Create the warrior kit
        SGKits.ArcherKit archerKit = new SGKits.ArcherKit(); //Create the ArcherKit

        this.getKitManager().add(warriorKit); //Add the warrior kit to the available kits
        this.getKitManager().add(archerKit); //Add the archer kit to the available kits

        KitSelector selector = new KitSelector(); //Create a kit selector npc, it will open a kit selection gui on click

        KitSelector warriorSelector = new KitSelector(warriorKit); //Create a warrior kit selector npc, on right click the kit will be selected
        KitSelector archerSelector = new KitSelector(archerKit); //Create an archer kit selector npc, on right click the kit will be selected


        this.getLobby().addNPC(selector, new Point3D(0,60,5));//Add the kit selector to the lobby
        this.getLobby().addNPC(warriorSelector, new Point3D(-5,60,6)); //Add the warrior kit selector to the lobby
        this.getLobby().addNPC(archerSelector, new Point3D(5,60,6)); //Add the archer kit selector to the lobby
    }
    //game functions
    protected void die(GamePlayer player){
        Team team = player.getTeam(); //get the players team
        if(team != null) { //Make sure the player has a team
            team.setPlaying(player, false); //Makes sure no events are triggered on this player, but at the end of the game he will still receive rewards.
        }
        getSpectateManager().addSpectator(player); //Make the player a spectator until the end of the game

        GameMessages.sendStructuredMessage(player, "player.onDead");

    }
    protected void leave(GamePlayer player){

    }
}