package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

/**
 * @author Nick Robson
 */
public class PlayerManager implements Manager {

    private final Game game;

    private final Map<String, PlayerState> playerStates = new HashMap<>();

    public PlayerManager(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public PlayerState getPlayerState(GamePlayer player) {
        PlayerState state = playerStates.get(player.getUUID().toString());
        if (state == null) {
            state = PlayerState.UNKNOWN;
            playerStates.put(player.getUUID().toString(), state);
        }
        return state;
    }

    public int getPlayerCount() {
        int amount = 0;
        for (String id : playerStates.keySet())
            if (playerStates.get(id) == PlayerState.PLAYING)
                amount++;
        return amount;
    }

    public Set<GamePlayer> getAllPlayers() {
        return playerStates.keySet().stream().map(s -> GameAPI.getPlayer(UUID.fromString(s))).collect(Collectors.toSet());
    }

    public Set<GamePlayer> getOnlinePlayers() {
        return getAllPlayers().stream().filter(p -> p.isOnline()).collect(Collectors.toSet());
    }

    public Set<GamePlayer> getPlayers(boolean onlineOnly) {
        return onlineOnly ? getOnlinePlayers() : getAllPlayers();
    }

    public boolean isPlaying(GamePlayer player) {
        return getPlayerState(player) == PlayerState.PLAYING;
    }

    public void setState(GamePlayer player, PlayerState state) {
        playerStates.put(player.getUUID().toString(), state);
    }

    public void add(GamePlayer player, PlayerState state) {
        setState(player, state);
    }

    public void remove(GamePlayer player) {
        setState(player, PlayerState.REMOVED);
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
