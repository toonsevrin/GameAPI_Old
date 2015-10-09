package com.exorath.game.api.voting;

import org.apache.commons.lang3.Validate;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;

/**
 * @author Nick Robson
 */
public class VotingManager implements Manager {

    private final Game game;
    private VoteSession session;

    public VotingManager(Game game) {
        Validate.notNull(game);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isOngoing() {
        return session != null && session.open;
    }

    public VoteSession getSession() {
        return session;
    }

    public void setSession(VoteSession session) {
        if (isOngoing())
            throw new IllegalArgumentException("Current session is ongoing; cannot start another");
        this.session = session;
    }

}
