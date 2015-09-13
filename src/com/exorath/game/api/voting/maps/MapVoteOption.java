package com.exorath.game.api.voting.maps;

import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.voting.VoteOption;

/**
 * @author Nick Robson
 */
public class MapVoteOption extends VoteOption {

    private GameMap map;

    public MapVoteOption(GameMap map) {
        super(map.getName());
    }

    public GameMap getMap() {
        return map;
    }

}
