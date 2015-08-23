package com.exorath.game.api.maps;

import java.util.LinkedList;
import java.util.List;

public class MapList {

    private List<GameMap> maps = new LinkedList<>();

    public List<GameMap> getMaps() {
        return this.maps;
    }

    public MapList addMap( GameMap map ) {
        this.maps.add( map );
        return this;
    }

    public MapList addMap( int index, GameMap map ) {
        this.maps.add( index, map );
        return this;
    }

}
