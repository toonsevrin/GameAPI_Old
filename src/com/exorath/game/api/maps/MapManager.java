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
    private MapSelection selection = MapSelection.RANDOM;
    private MapList maps = new MapList();

    public MapManager( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Set<GameMap> getApplicableMaps() {
        return GameMap.worlds.values().stream().filter( m -> m.getGameName().equals( getGame().getName() ) ).collect( Collectors.toSet() );
    }

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
