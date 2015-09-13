package com.exorath.game.api.voting.maps;

import java.util.List;
import java.util.stream.Collectors;

import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.voting.VoteOption;
import com.exorath.game.api.voting.VoteSession;

/**
 * @author Nick Robson
 */
public class MapVoteSession extends VoteSession {

    public MapVoteSession(String title, List<GameMap> options) {
        super(title, options.stream().map(m -> new MapVoteOption(m)).collect(Collectors.toList()));
    }

    public GameMap getWinningMap() {
        VoteOption opt = getWinner();
        return opt == null ? null : ((MapVoteOption) opt).getMap();
    }

}
