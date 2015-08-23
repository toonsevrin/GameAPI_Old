package example.survivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.exorath.game.api.GameProperty;
import com.exorath.game.api.action.DieAction;
import com.exorath.game.api.action.GameEndAction;
import com.exorath.game.api.action.HungerAction;
import com.exorath.game.api.action.JoinAction;
import com.exorath.game.api.action.QuitAction;
import com.exorath.game.api.gametype.RepeatingMinigame;
import com.exorath.game.api.message.GameMessenger;
import com.exorath.game.api.npc.types.KitSelector;
import com.exorath.game.api.npc.types.SpectatorNPC;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.team.FreeForAllTeam;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamProperty;
import com.yoshigenius.lib.util.GameUtil;

/**
 * Created by too on 27/05/2015.
 * An example gamemode
 */
public class SurvivalGames extends RepeatingMinigame {
    private List<Location> spawns = new ArrayList<Location>();
    
    public SurvivalGames() {
        this.setupGameProperties();
        this.setupLobby();
        this.setupSpawns();
        this.setupTeams();
        this.addListener( new SGListener() ); //Adds a custom event listener (See SGListener class).
    }
    
    /**
     * Sets the base properties and behaviour in this game up
     */
    private void setupGameProperties() {
        
        /*Game properties*/
        this.setName( "Survival Games" ); //Name of the gamemode
        this.setDescription( "Tributes fight to the death in the arena." ); //Description of the gamemode
        this.getProperties().set( RepeatingMinigame.REPEAT_DELAY, 60 ); //Minimum amount of seconds between two games
        this.getProperties().set( GameProperty.MAX_DURATION, 900 ); //Set the max game duration to 15 minutes, after this time game will be terminated.
        this.getProperties().set( GameProperty.ALLOW_SPECTATING, true ); //Set the max game duration to 15 minutes, after this time game will be terminated.
        
        /*Actions*/
        this.getActions().setDieAction( new DieAction.Spectate() ); //This is to avoid having to write basic actions again on each gamemode.
        this.getActions().setJoinAction( new JoinAction.SpectateIngame() ); //This is to avoid having to write basic actions again on each gamemode.
        this.getActions().setQuitAction( new QuitAction.LeaveGame() );
<<<<<<< HEAD
        this.getActions().setGameEndAction( new GameEndAction.SendToServer( "hub" ) ); //Needs to change
=======
        this.getActions().setGameEndAction( new GameEndAction.SendToServer( "hub" ) ); //This is to avoid having to write basic actions again on each gamemode.
        this.getActions().setHungerAction( new HungerAction.Default() );
>>>>>>> 3c6cb170422f96b558c798bb0d62b44511000675
        
    }
    
    /**
     * Sets the lobby in this game up
     */
    private void setupLobby() {
        this.getLobby().enable(); //Enable the lobby
        this.getLobby().setSpawnLocation( 0, 60, 0 ); //Spawn location in the lobby
        
        this.getLobby().addNPC( new SpectatorNPC(), new Vector( 0, 60, 10 ) ); //When this npc gets right clicked, you become a spectator.
        
        this.setupKits();
    }
    
    /**
     * Setup the player spawn points inGame
     */
    private void setupSpawns() {
        int[][] locs = new int[][] { //TODO: Use the config for this.
        { -1, 60, -2 }, { -1, 60, -1 }, { -1, 60, 0 }, { -1, 60, 1 },
                { 0, 60, -2 }, { 0, 60, -1 }, { 0, 60, 0 }, { 0, 60, 1 },
                { 1, 60, -2 }, { 1, 60, -1 }, { 1, 60, 0 }, { 1, 60, 1 },
                { 2, 60, -2 }, { 2, 60, -1 }, { 2, 60, 0 }, { 2, 60, 1 },
        };
        for ( int[] coords : locs ) {
            this.spawns.add( new Location( this.getGameWorld(), coords[ 0 ], coords[ 1 ], coords[ 2 ] ) );
        }
    }
    
    /**
     * Sets the teams in this game up
     */
    private void setupTeams() {
        FreeForAllTeam team = new FreeForAllTeam(); //The base team where all players will be put into
        team.getProperties().set( TeamProperty.SPAWNS, this.spawns ); //Set the spawn points of the players. setupSpawns() generates these spawns.
        team.setMinTeamSize( 8 ); //Set the minimum team size before countdown.
        team.setMaxTeamSize( 16 ); //Set the maximum team size.
        team.setFriendlyFire( true ); // Turn friendly fire on, since tributes should be able to kill eachother!
        
        this.getTeamManager().addTeam( team );
    }
    
    /**
     * Sets the kits in this game up, also creates some NPCs in the lobby.
     */
    private void setupKits() {
        SGKits.WarriorKit warriorKit = new SGKits.WarriorKit(); //Create the warrior kit
        SGKits.ArcherKit archerKit = new SGKits.ArcherKit(); //Create the ArcherKit
        
        this.getKitManager().add( warriorKit ); //Add the warrior kit to the available kits
        this.getKitManager().add( archerKit ); //Add the archer kit to the available kits
        
        KitSelector selector = new KitSelector(); //Create a kit selector npc, it will open a kit selection gui on click
        
        KitSelector warriorSelector = new KitSelector( warriorKit ); //Create a warrior kit selector npc, on right click the kit will be selected
        KitSelector archerSelector = new KitSelector( archerKit ); //Create an archer kit selector npc, on right click the kit will be selected
        
        this.getLobby().addNPC( selector, new Vector( 0, 60, 5 ) ); //Add the kit selector to the lobby
        this.getLobby().addNPC( warriorSelector, new Vector( -5, 60, 6 ) ); //Add the warrior kit selector to the lobby
        this.getLobby().addNPC( archerSelector, new Vector( 5, 60, 6 ) ); //Add the archer kit selector to the lobby
    }
    
    //game functions
    protected void start() {
        new SGChests( this ); //Generates chest contents in the selected game world.
        
        this.scheduleEndGrace(); // Ends the grace period (After 30 seconds)
        this.scheduleStandoff(); // Teleports all players to center of map (After 10 minutes)
    }
    
    protected void stop( StopCause cause ) {
        Team team = this.getTeamManager().getTeam();
        for ( GamePlayer player : team.getPlayers() ) {
            if ( !player.isOnline() ) {
                continue;
            }
            if ( cause == StopCause.TIME_UP ) { // If the time is up, it means that the game tied.
                if ( player.isAlive( this ) ) { // Send the players which are still alive a victory reward and message
                    GameMessenger.sendStructured( this, player, "player.onTie.alive" );
                    player.addHonorPoints( 150 );
                } else { // Send the losers a message and a smaller reward
                    GameMessenger.sendStructured( this, player, "player.onTie.dead" );
                    player.addHonorPoints( 50 );
                }
            }
            if ( cause == StopCause.VICTORY ) {
                if ( player.isAlive( this ) ) { // Send the victor a big reward and victory message
                    GameMessenger.sendStructured( this, player, "player.onVictory.alive" );
                    player.addHonorPoints( 250 );
                } else { // Send the losers a message and a smaller reward
                    GameMessenger.sendStructured( this, player, "player.onVictory.dead" );
                    player.addHonorPoints( 50 );
                }
            }
        }
    }
    
    protected void die( GamePlayer player ) {
        GameMessenger.sendStructured( this, player, "player.onDead" );
    }
    
    /**
     * Enable pvp after 30 seconds
     */
    protected void scheduleEndGrace() {
        this.getScheduler().runTaskLater( ( ) -> {
            GameMessenger.sendInfo( SurvivalGames.this, "Grace period has ran out." );
            SurvivalGames.this.getTeamManager().getTeam().setFriendlyFire( true );
        }, 30 * 20 );
    }
    
    /**
     * Teleport all players back to middle after 10 minutes for a big end fight
     */
    protected void scheduleStandoff() {
        int[] standoffMessageTime = new int[] { 60, 30, 20, 10, 5, 4, 3, 2, 1 }; //Times before standoff to send a scheduled message
        for ( int time : standoffMessageTime ) { //Schedule a countdown message
            this.scheduleStandoffMessage( time );
        }
        
        this.getScheduler().runTaskLater( ( ) -> {
            GameMessenger.sendInfo( SurvivalGames.this, "Standoff started! Players are teleported to the center." );
            int cycle = 0;
            for ( GamePlayer player : SurvivalGames.this.getTeamManager().getTeam().getPlayers() ) { //Teleport all active players back to a spawn location.
                player.getBukkitPlayer().teleport( SurvivalGames.this.getTeamManager().getTeam().getSpawns().get( cycle ) );
                cycle = GameUtil.cycle( cycle, SurvivalGames.this.getTeamManager().getTeam().getSpawns().size() - 1 );
            }
        }, 20 * 60 * 10 );
    }
    
    private void scheduleStandoffMessage( int time ) { // Send a countdown message
        this.getScheduler().runTaskLater( ( ) -> GameMessenger.sendInfo( SurvivalGames.this, "Standoff in " + time + " seconds" ),
                20 * 60 * 10 - 20 * time );
    }
    
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        
    }
    
}
