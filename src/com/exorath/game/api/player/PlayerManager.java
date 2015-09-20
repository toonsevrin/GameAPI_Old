package com.exorath.game.api.player;

import java.util.*;
import java.util.stream.Collectors;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Manager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.GameSpawn;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.lib.JoinLeave;
import org.bukkit.entity.Player;

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

    public void login(UUID id){
        GamePlayer player = new GamePlayer(id);
        player.setGame(game);
        players.add(player);
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
        return (int) players.size();
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
