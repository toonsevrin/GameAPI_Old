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

/**
 * @author Nick Robson
 */
public class PlayerManager implements Manager, JoinLeave {

    private final Game game;

    private Set<GamePlayer> players = new HashSet<>();

    public PlayerManager(Game game) {
        this.game = game;
    }
    //** Join & Leave **//
    @Override
    public void join(GamePlayer p) {
        if (getGame().getState().is(GameState.WAITING, GameState.STARTING)) {
            //Goto lobby if enabled -> Goto game world and set playing if not
        }else {
            //Set spectating if enabled -> tp to lobby if not
        }
        add(p);

    }

    @Override
    public void leave(GamePlayer player) {

    }

    public Game getGame() {
        return game;
    }
    //** Counting **//
    public int getPlayerCount() {
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

    //** Adding/Removing **//
    public void add(GamePlayer player) {
        players.add(player);
    }

    public void remove(GamePlayer player) {
        players.remove(player);
    }

}
