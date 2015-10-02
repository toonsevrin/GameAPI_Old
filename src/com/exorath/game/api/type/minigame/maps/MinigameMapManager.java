package com.exorath.game.api.type.minigame.maps;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.MapManager;
import com.google.common.collect.Lists;

/**
 * Created by TOON on 9/23/2015.
 */
public class MinigameMapManager implements Manager {

    private Game game;
    private MapManager mapManager;

    //Map Selection
    private MapSelection selection = MapSelection.RANDOM;
    private GameMap current;
    private int index = 0;

    public MinigameMapManager(Game game) {
        this.game = game;
        mapManager = game.getManager(MapManager.class);
    }

    //** Selection **//
    public MapSelection getSelection() {
        return selection;
    }

    public void setSelection(MapSelection selection) {
        this.selection = selection;
    }

    //** Map rotation **//
    public GameMap getCurrent() {
        return current == null ? nextMap() : current;
    }

    public GameMap nextMap() {
        return nextMap(selection);
    }

    public GameMap nextMap(MapSelection selection) {
        if (game.getManager(MapManager.class).getMaps().size() == 0) {
            GameAPI.error("Map size == 0: Please add a map!");
            return null;
        }
        switch (selection) {
            case CYCLE:
                current = mapManager.getMaps().get(index == mapManager.getMaps().size() ? (index = 0) : index++);
            case RANDOM:
                List<Integer> indices = Lists.newArrayList();
                IntStream.range(0, mapManager.getMaps().size()).forEach(i -> {
                    if (current == null || i != index)
                        indices.add(i);
                });
                int ind = indices.get(new Random().nextInt(indices.size()));
                current = mapManager.getMaps().get(ind);
                index = ind;
            case SAME:
                current.reset();
            case VOTE:
                // TODO: Call vote with callback to set map. Something will need to be done about the return type...
                break;
            default:
                break;
        }
        return current;
    }
}
