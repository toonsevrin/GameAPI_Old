package example.survivalgames;

import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.behaviour.HungerBehaviour;
import com.exorath.game.api.behaviour.LeaveBehaviour;
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
        this.setDescription( "Tributes fight to the death in the arena." );
        
        this.getTeamManager().addTeam( new FreeForAllTeam() );
        
        this.getProperties().set( BasePlayerProperty.HUNGER, HungerBehaviour.DEFAULT );
        
        this.setLeaveBehaviour( LeaveBehaviour.REMOVE );
        
        this.addListener( new SGListener() );
    }
    
    public void die( GamePlayer player ) {
        // TODO
    }
    
}
