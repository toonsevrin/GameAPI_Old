package com.exorath.game.api.voting;

/**
 * @author Nick Robson
 */
public class Vote {

    VoteSession session;
    int option;
    int weight;

    public Vote(VoteSession session, int option, int weight) {
        this.session = session;
        this.option = option;
    }

    public VoteSession getSession() {
        return session;
    }

    public int getOption() {
        return option;
    }

    public int getWeight() {
        return weight;
    }

}
