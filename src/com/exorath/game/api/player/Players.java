package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.Map;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameState;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.GameSpawn;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;

/**
 * @author Nick Robson
 */
public class Players {

    private final Game game;

    private final Map<String, PlayerState> playerStates = new HashMap<>();

    public Players(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public PlayerState getPlayerState(GamePlayer player) {
        PlayerState state = this.playerStates.get(player.getUUID().toString());
        if (state == null) {
            state = PlayerState.UNKNOWN;
            this.playerStates.put(player.getUUID().toString(), state);
        }
        return state;
    }

    public int getPlayingAmount() {
        int amount = 0;
        for (String id : playerStates.keySet()) {
            if (playerStates.get(id) == PlayerState.PLAYING)
                amount++;
        }
        return amount;
    }

    public boolean isPlaying(GamePlayer player) {
        return this.getPlayerState(player) == PlayerState.PLAYING;
    }

    public void setState(GamePlayer player, PlayerState state) {
        this.playerStates.put(player.getUUID().toString(), state);
    }

    public void add(GamePlayer player, PlayerState state) {
        this.setState(player, state);
    }

    public void remove(GamePlayer player) {
        this.setState(player, PlayerState.REMOVED);
    }

    public void join(GamePlayer p) {
        PlayerState state;
        if (getGame().getState().is(GameState.WAITING, GameState.STARTING))
            state = PlayerState.PLAYING;
            else
            state = PlayerState.SPECTATING;
        add(p, state);

        TeamManager teams = getGame().getManager(TeamManager.class);
        if (teams != null) {
            Team team = teams.getTeam(p);
            if (team != null)
                team.addPlayer(p);
        }

        MapManager maps = getGame().getManager(MapManager.class);
        if (maps != null) {
            GameMap map = maps.getCurrent();
            if (map == null)
                return;
            GameSpawn spawn = map.findSpawn(p);
            if (p.isOnline() && spawn != null)
                p.getBukkitPlayer().teleport(spawn.getBukkitLocation());
        }
    }

}
