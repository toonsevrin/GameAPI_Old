package example.survivalgames;

import org.bukkit.event.entity.PlayerDeathEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.GameState;
import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.message.GameMessenger;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerState;

/**
 * @author Nick Robson
 */
public class SGListener implements GameListener {
    
    //REPLACED WITH AN ACTION
    @Override
    public void onDeath( PlayerDeathEvent event, Game game, GamePlayer player ) {
        SurvivalGames sg = (SurvivalGames) game;
        if ( player.getState( game ) == PlayerState.PLAYING ) {
            GameMessenger.sendStructured( game, player, "You died, hopefully you win next time!",
                    "This round you earned " + player.getWonHonorPoints() + " honor points!" );
        }
    }
    
    /**
     * Reward a player with 70 honor points when he kills another player
     */
    @Override
    public void onPlayerKillPlayer( GamePlayerKillPlayerEvent event ) {
        GamePlayer killer = event.getKiller();
        GameMessenger.sendStructured( event.getGame(), killer, "player.onKill" );
        killer.addHonorPoints( 70 );
    }
    
    /**
     * When game starts, generate all the chests contents.
     */
    @Override
    public void onGameStateChange( GameStateChangedEvent event ) {
        final SurvivalGames sg = (SurvivalGames) event.getGame();
        if ( event.getNewState().is( GameState.INGAME ) ) {
            sg.start();
        } else if ( event.getNewState().is( GameState.FINISHING ) ) {
            sg.stop( event.getStopCause() );
        }
    }
}
