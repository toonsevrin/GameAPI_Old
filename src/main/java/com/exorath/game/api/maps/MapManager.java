package com.exorath.game.api.maps;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.exorath.game.GameAPI;
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
        Validate.notNull(map, "Cannot add null GameMap to MapManager");
        if (!maps.contains(map)) {
            maps.add(map);
            GameAPI.printConsole("Map " + map.getName() + " (V" + map.getProperties().as(MapProperty.VERSION, String.class) + ") by "
                    + map.getProperties().as(MapProperty.CREATOR, String.class) + " added.");
        }
    }

    public void addMap(World world) {
        addMap(GameMap.get(world));
    }

    public void addMap(String world) {
        addMap(Bukkit.createWorld(new WorldCreator(world)));
    }

}
