package com.exorath.game.api.spectate;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.Manager;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerState;
import com.exorath.game.api.player.PlayerManager;

/**
 * @author Nick Robson
 */
public class SpectateManager implements Manager {

    private final Game game;

    public SpectateManager(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isSpectating(GamePlayer player) {
        return player.getState() == PlayerState.SPECTATING;
    }

    public void setSpectating(GamePlayer player, boolean spectating) {
        if (spectating)
            addSpectator(player);
        else
            removeSpectator(player);
    }

    public void addSpectator(GamePlayer player) {
        player.setState(PlayerState.SPECTATING);

        Player p = player.getBukkitPlayer();
        if (p != null)
            p.setGameMode(GameMode.SPECTATOR);
    }

    public void removeSpectator(GamePlayer player) {
        Player p = player.getBukkitPlayer();
        if (p != null)
            p.setGameMode(game.getProperties().as(GameProperty.DEFAULT_GAMEMODE, GameMode.class));
    }

}
