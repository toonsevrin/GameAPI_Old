package com.exorath.game.api.maps;

import com.exorath.game.api.Manager;

/**
 * @author Nick Robson
 */
public class MapManager implements Manager {

    private MapSelection selection = MapSelection.RANDOM;
    private MapList maps = new MapList();

    public MapSelection getSelection() {
        return selection;
    }

    public void setSelection( MapSelection selection ) {
        this.selection = selection;
    }

    public MapList getMapList() {
        return maps;
    }

    public void addMap( GameMap map ) {
        maps.addMap( map );
    }

    public boolean addMap( String map ) {
        GameMap gm = GameMap.get( map );

        if ( gm == null ) {
            return false;
        }
        maps.addMap( gm );
        return true;
    }

    public GameMap getCurrent() {
        return getMapList().getCurrent();
    }

    public void nextMap() {
        getMapList().nextMap( this.selection );
    }

}
