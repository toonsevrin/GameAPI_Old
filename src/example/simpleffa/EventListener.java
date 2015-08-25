package example.simpleffa;

import com.exorath.game.api.GameListener;
import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.gametype.minigame.RepeatingMinigame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by TOON on 8/23/2015.
 */
public class EventListener implements GameListener {
    /**
     * Finishes the game when there is a winner
     */
    @Override
    public void onPlayerKillPlayer(GamePlayerKillPlayerEvent event) {
        if(event.getGame().getTeamManager().getTeam().getActivePlayers().size() <= 1){
            RepeatingMinigame game = (RepeatingMinigame) event.getGame();
            game.finish();
        }
    }
}
