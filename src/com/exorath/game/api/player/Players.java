package com.exorath.game.api.player;

import java.util.HashMap;
import java.util.Map;
import com.exorath.game.api.Game;

/**
 * @author Nick Robson
 */
public class Players {

    private final Game game;

    private final Map<String, PlayerState> playerStates = new HashMap<>();

    public Players( Game game ) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public PlayerState getPlayerState(GamePlayer player) {
        PlayerState state = this.playerStates.get( player.getUUID().toString() );
        if (state == null) {
            state = PlayerState.UNKNOWN;
            this.playerStates.put( player.getUUID().toString(), state );
        }
        return state;
    }

    public int getPlayingAmount() {
        int amount = 0;
        for ( String id : playerStates.keySet() ) {
            if (playerStates.get(id) == PlayerState.PLAYING)
                amount++;
        }
        return amount;
    }

    public boolean isPlaying(GamePlayer player) {
        return this.getPlayerState(player) == PlayerState.PLAYING;
    }

    public void setState(GamePlayer player, PlayerState state) {
        this.playerStates.put( player.getUUID().toString(), state );
    }

    public void add(GamePlayer player, PlayerState state) {
        this.setState(player, state);
    }

    public void remove(GamePlayer player) {
        this.setState(player, PlayerState.REMOVED);
    }

}
