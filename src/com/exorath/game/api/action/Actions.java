package com.exorath.game.api.action;

/**
 * @author Nick Robson
 */
public class Actions {
    
    private JoinAction join;
    private QuitAction quit;
    private DieAction die;
    
    public JoinAction getJoinAction() {
        return this.join;
    }
    
    public Actions setJoinAction( JoinAction action ) {
        this.join = action;
        return this;
    }
    
    public QuitAction getQuitAction() {
        return this.quit;
    }
    
    public Actions setQuitAction( QuitAction action ) {
        this.quit = action;
        return this;
    }
    
    public DieAction getDieAction() {
        return this.die;
    }
    
    public Actions setDieAction( DieAction action ) {
        this.die = action;
        return this;
    }
    
}
