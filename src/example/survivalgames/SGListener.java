package example.survivalgames;

import com.exorath.game.api.events.GameStateChangedEvent;
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
    //REPLACED WITH AN ACTION
    @Override
    public void onDeath(PlayerDeathEvent event, Game game, GamePlayer player) {
        SurvivalGames sg = (SurvivalGames) game;
        if (game.isPlaying(player)) {
            GameMessenger.sendStructured(player, "You died, hopefully you win next time!", "This round you earned " + player.getWonHonorPoints() + " honor points!");
        }
    }

    /**
     * When game starts, generate all the chests contents.
     */
    @Override
    public void onGameStateChange(GameStateChangedEvent event) {
        if(!event.getNewState().is(GameState.INGAME))
            return;
        SurvivalGames sg = (SurvivalGames) event.getGame();

        new SGChests(sg); //Generates chest contents in the selected game world.
    }
}
