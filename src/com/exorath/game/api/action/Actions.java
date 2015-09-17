package com.exorath.game.api.action;

/**
 * @Issues Toon Sevrin TODO: Make sure these "actions" are called, btw am I not
 *         the designer of the action system? :(
 * @author Nick Robson
 */
public class Actions {
    //TODO: It might be better to just remove these :P
    private DieAction die = new DieAction.Spectate();
    private GameEndAction gameEnd = new GameEndAction.SendToServer("hub");
    private HungerAction hunger = new HungerAction.Default();


    public DieAction getDieAction() {
        return this.die;
    }

    public Actions setDieAction(DieAction action) {
        this.die = action;
        return this;
    }

    public GameEndAction getGameEndAction() {
        return this.gameEnd;
    }

    public Actions setGameEndAction(GameEndAction action) {
        this.gameEnd = action;
        return this;
    }

    public HungerAction getHungerAction() {
        return this.hunger;
    }

    public Actions setHungerAction(HungerAction action) {
        this.hunger = action;
        return this;
    }

}
