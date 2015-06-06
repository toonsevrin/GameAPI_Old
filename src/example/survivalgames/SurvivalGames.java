package example.survivalgames;

import com.exorath.game.api.gametype.RepeatingMinigame;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.team.FreeForAllTeam;

/**
 * Created by too on 27/05/2015.
 * An example gamemode
 */
public class SurvivalGames extends RepeatingMinigame {
    
    public SurvivalGames() {
        this.setName( "Survival Games" );
        this.setDescription( "Tributes fight to the death in the dangerous arena." );
        
        this.getLobby().enable();
        this.getLobby().setSpawnLocation( 0, 60, 0 );
        
        this.getTeamManager().addTeam( new FreeForAllTeam() );
        this.addListener( new SGListener() );
    }
    
    public void die( GamePlayer player ) {
        // TODO
    }
    
}
