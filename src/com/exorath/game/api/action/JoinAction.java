package com.exorath.game.api.action;

import com.exorath.game.api.gamestates.GameState;
import com.exorath.game.api.gamestates.StateManager;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * This action adds base behaviour to the player join event
 * @author Nick Robson
 * @author Toon Sevrin
 */
public abstract class JoinAction {
    public abstract void onJoin(PlayerJoinEvent event, GamePlayer player, Game game);

    public static class SpectateIngame extends JoinAction {
        private static final GameState[] SPECTATE_STATES = new GameState[]{GameState.INGAME, GameState.FINISHING, GameState.STARTING, GameState.RESTARTING};

        @Override
        public void onJoin(PlayerJoinEvent event, GamePlayer player, Game game) {
            if(!ArrayUtils.contains(SPECTATE_STATES, StateManager.getInstance().getState()))
                return;
            //TODO: Set the GamePlayer in spectator mode
        }
    }

}
