package com.exorath.game.api.voting;

/**
 * @author Nick Robson
 */
public class Vote {

    VoteSession session;
    int option;

    public Vote( VoteSession session, int option ) {
        this.session = session;
        this.option = option;
    }

    public VoteSession getSession() {
        return session;
    }

    public int getOption() {
        return option;
    }

}
