package example.survivalgames;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.GameState;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public class SGListener implements GameListener {
    
    @Override
    public void onDeath( PlayerDeathEvent event, Game game, GamePlayer player ) {
        if ( game instanceof SurvivalGames ) {
            SurvivalGames sg = (SurvivalGames) game;
            if ( game.isPlaying( player ) ) {
                sg.die( player );
            }
        }
    }
    
    @Override
    public void onJoin( PlayerJoinEvent event, Game game, GamePlayer player ) {
        if ( game instanceof SurvivalGames ) {
            SurvivalGames sg = (SurvivalGames) game;
            if ( game.getState().is( GameState.INGAME, GameState.FINISHING ) ) {
                sg.getSpectateManager().addSpectator( player );
            } else {
                sg.getLobby().teleport( player );
            }
        }
    }
    
    @Override
    public void onForceLeave( Game game, GamePlayer player ) {
        if ( game instanceof SurvivalGames ) {
            SurvivalGames sg = (SurvivalGames) game;
            if ( game.isPlaying( player ) ) {
                sg.die( player );
            }
        }
    }
    
}
