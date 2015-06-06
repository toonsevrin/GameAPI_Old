package example.survivalgames;

import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.lib.util.GameUtil;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.GameState;
import com.exorath.game.api.player.GamePlayer;

import java.util.UUID;

/**
 * @author Nick Robson
 */
public class SGListener implements GameListener {
    //REPLACED WITH AN ACTION
    @Override
    public void onDeath(PlayerDeathEvent event, Game game, GamePlayer player) {
        SurvivalGames sg = (SurvivalGames) game;
        if (game.isPlaying(player)) {
            GameMessenger.sendStructured(player, "You died, hopefully you win next time!", "This round you earned " + player.getWonHonorPoints() + " honor points!");
        }
    }

    /**
     * Reward a player with 70 honor points when he kills another player
     */
    @Override
    public void onPlayerKillPlayer(PlayerKillPlayerEvent event){
        GamePlayer killer = event.getKiller();
        GameMessenger.sendStructured(killer, "player.onKill");
        killer.addHonorPoints(70);
    }

    /**
     * When game starts, generate all the chests contents.
     */
    @Override
    public void onGameStateChange(GameStateChangedEvent event) {
        if (event.getNewState().is(GameState.INGAME)) {
            final SurvivalGames sg = (SurvivalGames) event.getGame();
            sg.start();
        }else if (event.getNewState().is(GameState.FINISHING)) {
            final SurvivalGames sg = (SurvivalGames) event.getGame();
            sg.stop(event.getStopCause());
        }
    }
}
