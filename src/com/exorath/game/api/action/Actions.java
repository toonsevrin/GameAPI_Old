package com.exorath.game.api.action;

/**
 * @author Nick Robson
 */
public class Actions {
    
    private JoinAction join = new JoinAction.SpectateIngame();
    private QuitAction quit = new QuitAction.LeaveGame();
    private DieAction die = new DieAction.Spectate();
    private GameEndAction gameEnd = new GameEndAction.SendToServer( "hub" );
    
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
    
    public GameEndAction getGameEndAction() {
        return this.gameEnd;
    }
    
    public Actions setGameEndAction( GameEndAction action ) {
        this.gameEnd = action;
        return this;
    }
    
}
