package com.exorath.game.api.action;

import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;

/**
 * @author Nick Robson
 * @author Toon Sevrin
 */
public abstract class QuitAction {

    public abstract void onQuit(PlayerQuitEvent event, GamePlayer player, Game game);

    public abstract void onQuit(PlayerKickEvent event, GamePlayer player, Game game);

    public static class LeaveGame extends QuitAction {

        @Override
        public void onQuit(PlayerQuitEvent event, GamePlayer player, Game game) {
            remove(game, player);
        }

        @Override
        public void onQuit(PlayerKickEvent event, GamePlayer player, Game game) {
            remove(game, player);
        }

        private void remove(Game game, GamePlayer player) {
            game.getManager(PlayerManager.class).remove(player);
        }

    }

}
