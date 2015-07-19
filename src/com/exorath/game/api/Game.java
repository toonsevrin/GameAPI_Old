package com.exorath.game.api;

import java.util.Set;

import com.exorath.game.api.behaviour.LeaveBehaviour;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.player.Players;
import com.exorath.game.api.spectate.SpectateManager;
import com.exorath.game.api.team.TeamManager;
import com.google.common.collect.Sets;

/**
 * The class representing a single Game instance.
 * Gamemode, RepeatingMinigame and Minigame extend this.
 * @author toon
 * @author Nick Robson
 */

public abstract class Game {
    
    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "Default game description";
    
    private Lobby lobby = new Lobby();
    private Properties properties = new Properties();
    private TeamManager teamManager = new TeamManager( this );
    private SpectateManager spectateManager = new SpectateManager( this );
    private Players players = new Players( this );
    
    private final Set<GameListener> listeners = Sets.newHashSet();
    
    private GameState state = GameState.WAITING;
    private LeaveBehaviour leaveBehaviour = LeaveBehaviour.DO_NOTHING;
    
    public Game() {}
    
    public void setState( GameState state ) {
        GameState old = this.state;
        this.state = state;
        GameStateChangedEvent event = new GameStateChangedEvent( this, old, state );
        for ( GameListener listener : this.listeners ) {
            listener.onGameStateChange( event );
        }
    }
    
    public LeaveBehaviour getLeaveBehaviour() {
        return this.leaveBehaviour;
    }
    
    protected void setLeaveBehaviour( LeaveBehaviour behaviour ) {
        this.leaveBehaviour = behaviour;
    }
    
    public Lobby getLobby() {
        return this.lobby;
    }
    
    public GameState getState() {
        return this.state;
    }
    
    public TeamManager getTeamManager() {
        return this.teamManager;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    protected void setLobby( Lobby lobby ) {
        this.lobby = lobby;
    }
    
    protected void setTeamManager( TeamManager teamManager ) {
        this.teamManager = teamManager;
    }
    
    protected void setProperties( Properties properties ) {
        this.properties = properties;
    }
    
    public String getName() {
        return this.properties.as( GameProperty.NAME, String.class );
    }
    
    public void setName( String name ) {
        this.properties.set( GameProperty.NAME, name );
    }
    
    public String getDescription() {
        return this.properties.as( GameProperty.DESCRIPTION, String.class );
    }
    
    public void setDescription( String description ) {
        this.properties.set( GameProperty.DESCRIPTION, description );
    }
    
    protected void addListener( GameListener listener ) {
        if ( listener != null ) {
            this.listeners.add( listener );
        }
    }
    
    public Players getPlayers() {
        return this.players;
    }
    public int getPlayerCount(){return this.players.getPlayingAmount();}
    
    public SpectateManager getSpectateManager() {
        return this.spectateManager;
    }
    
    public boolean isStarted() {
        return this.getState().is( GameState.INGAME, GameState.FINISHING, GameState.RESETTING, GameState.RESTARTING );
    }
    
    protected void startGame() {
        this.setState( GameState.STARTING );
    }
    protected void stopGame() { this.setState(GameState.FINISHING);}
    
}
