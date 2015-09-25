package com.exorath.game.api.maps;

import java.util.Set;
import java.util.stream.Collectors;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;

/**
 * @author Nick Robson
 */
public class MapManager implements Manager {

    private final Game game;
    private MapList maps = new MapList();

    public MapManager(Game game) {
        this.game = game;
    }

    //** Getters **//
    public Game getGame() {
        return game;
    }

    public Set<GameMap> getApplicableMaps() {
        return GameMap.worlds.values().stream().filter(m -> m.getGameName().equals(getGame().getName()))
                .collect(Collectors.toSet());
    }

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
}
