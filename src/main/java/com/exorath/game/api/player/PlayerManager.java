package com.exorath.game.api.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;

/**
 * @author Nick Robson
 */
public class PlayerManager implements Manager {

    private final Game game;

    private Set<GamePlayer> players = new HashSet<>();

    public PlayerManager(Game game) {
        this.game = game;
    }
    //** Join & Leave **//

    public GamePlayer login(UUID id) {
        GamePlayer player = new GamePlayer(id);
        player.setGame(game);
        players.add(player);
        return player;
    }

    public void leave(GamePlayer player) {
        players.remove(player);
    }

    //** Game **//
    public Game getGame() {
        return game;
    }

    //** Counting **//
    public int getPlayerCount() {
        return players.size();
    }

    public int getIngamePlayerCount() {
        return (int) players.stream().filter(p -> p.getState() == PlayerState.PLAYING).count();
    }

    //** Getting players **//
    public Set<GamePlayer> getPlayers() {
        return players;
    }

    //** Tests **//
    public boolean isPlaying(GamePlayer player) {
        return player.getState() == PlayerState.PLAYING;
    }

}
