package com.exorath.game.api.maps;

import com.exorath.game.GameAPI;
import com.exorath.game.api.gametype.minigame.maps.MapSelection;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapList {

    private List<GameMap> maps = new LinkedList<>();

    public MapList() {

    }

    public List<GameMap> getMaps() {
        return this.maps;
    }

    public MapList addMap(GameMap map) {
        this.maps.add(map);
        return this;
    }

    public MapList addMap(int index, GameMap map) {
        this.maps.add(index, map);
        return this;
    }
}
