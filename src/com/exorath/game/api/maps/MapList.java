package com.exorath.game.api.maps;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MapList {

    private List<GameMap> maps = new LinkedList<>();
    private int index = 0;
    private GameMap current;

    public MapList() {

    }

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

    public GameMap getCurrent() {
        return current == null ? nextMap( MapSelection.RANDOM ) : current;
    }

    public GameMap nextMap( MapSelection selection ) {
        switch ( selection ) {
            case CYCLE:
                current = maps.get( ++index );
            case RANDOM:
                current = maps.get( new Random().nextInt( maps.size() ) );
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
