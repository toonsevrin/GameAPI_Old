package com.exorath.game.api.maps;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.google.common.collect.Lists;

/**
 * @author Nick Robson
 */
public class MapManager implements Manager {

    private final Game game;
    private final List<GameMap> maps = Lists.newArrayList();

    public MapManager(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public List<GameMap> getMaps() {
        return maps;
    }

    public void addMap(GameMap map) {
        if (!maps.contains(map))
            maps.add(map);
    }

    public void addMap(World world) {
        addMap(GameMap.get(world));
    }

    public void addMap(String world) {
        addMap(Bukkit.getWorld(world));
    }

}
