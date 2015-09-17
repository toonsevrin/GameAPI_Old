package com.exorath.game.api.maps;

import java.util.Set;
import java.util.stream.Collectors;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.JoinLeave;

/**
 * @author Nick Robson
 * TODO: Make an overwritten MapManager for the minigame with MapList behaviour. Join method also needs to be adjusted to that
 */
public class MapManager implements Manager, JoinLeave {

    private final Game game;
    private MapSelection selection = MapSelection.RANDOM;
    private MapList maps = new MapList();

    public MapManager(Game game) {
        this.game = game;
    }

    //** Join & Leave **//
    @Override
    public void join(GamePlayer player) {
        GameMap map = maps.getCurrent();
        if (map == null)
            return;
        GameSpawn spawn = map.findSpawn(player);
        if (player.isOnline() && spawn != null)
            player.getBukkitPlayer().teleport(spawn.getBukkitLocation());
    }

    @Override
    public void leave(GamePlayer player) {

    }
    //** Getters **//
    public Game getGame() {
        return game;
    }
    public Set<GameMap> getApplicableMaps() {
        return GameMap.worlds.values().stream().filter(m -> m.getGameName().equals(getGame().getName()))
                .collect(Collectors.toSet());
    }
    //* Selection **//
    public MapSelection getSelection() {
        return selection;
    }

    public void setSelection(MapSelection selection) {
        this.selection = selection;
    }

    //** MapList: TODO: Only add this in minigame **//
    public MapList getMapList() {
        return maps;
    }
    public void addMap(GameMap map) {
        maps.addMap(map);
    }
    public boolean addMap(String map) {
        GameMap gm = GameMap.get(map);
        if (gm == null)
            return false;
        maps.addMap(gm);
        return true;
    }
    public GameMap getCurrent() {
        return getMapList().getCurrent();
    }
    public void nextMap() {
        getMapList().nextMap(selection);
    }

}
