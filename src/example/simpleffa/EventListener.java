package example.simpleffa;

import com.exorath.game.api.GameListener;
import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.gametype.minigame.RepeatingMinigame;
import com.exorath.game.api.team.TeamManager;

/**
 * Created by TOON on 8/23/2015.
 */
public class EventListener implements GameListener {
    /**
     * Finishes the game when there is a winner
     */
    @Override
    public void onPlayerKillPlayer(GamePlayerKillPlayerEvent event) {
        if ( event.getGame().getManager( TeamManager.class ).getTeam().getActivePlayers().size() <= 1 ) {
            RepeatingMinigame game = (RepeatingMinigame) event.getGame();
            game.finish();
        }
    }
}
