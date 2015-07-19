package com.exorath.game.api;

import java.io.File;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.GameAPI;
import com.exorath.game.api.action.Actions;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.kit.KitManager;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.player.Players;
import com.exorath.game.api.spectate.SpectateManager;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.lib.util.FileUtils;
import com.google.common.collect.Sets;

/**
 * The class representing a single Game instance.
 * Gamemode, RepeatingMinigame and Minigame extend this.
 * 
 * @author toon
 * @author Nick Robson
 */

public abstract class Game {
    
    public static enum StopCause {
        VICTORY,
        DRAW,
        TIME_UP;
    }
    
    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "Default game description";
    
    private final Set<GameListener> listeners = Sets.newHashSet();
    
    private Lobby lobby = new Lobby();
    private Properties properties = new Properties();
    private TeamManager teamManager = new TeamManager( this );
    private KitManager kitManager = new KitManager( this );
    private SpectateManager spectateManager = new SpectateManager( this );
    private Players players = new Players( this );
    private Actions actions = new Actions();
    private GameState state = GameState.WAITING;
    private String world = null;
    
    public Game() {}
    
    public GameScheduler getScheduler() {
        return GameScheduler.INSTANCE;
    }
    
    public World getGameWorld() {
        return Bukkit.getWorld( this.world );
    }
    
    public void setGameWorld( World world ) {
        this.world = world.getName();
    }
    
    public GameState getState() {
        return this.state;
    }
    
    public Lobby getLobby() {
        return this.lobby;
    }
    
    public TeamManager getTeamManager() {
        return this.teamManager;
    }
    
    public KitManager getKitManager() {
        return this.kitManager;
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    public Actions getActions() {
        return this.actions;
    }
    
    public Players getPlayers() {
        return this.players;
    }
    
    public void setState( GameState state ) {
        GameState old = this.state;
        this.state = state;
        GameStateChangedEvent event = new GameStateChangedEvent( this, old, state );
        for ( GameListener listener : this.listeners ) {
            listener.onGameStateChange( event );
        }
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
    
    public SpectateManager getSpectateManager() {
        return this.spectateManager;
    }
    
    public boolean isStarted() {
        return this.getState().is( GameState.INGAME, GameState.FINISHING, GameState.RESETTING, GameState.RESTARTING );
    }
    
    protected void startGame() {
        this.setState( GameState.STARTING );
    }
    
    public FileConfiguration getConfig( String filename ) {
        File file = new File( GameAPI.getInstance().getDataFolder( this ), filename + ( filename.endsWith( ".yml" ) ? "" : ".yml" ) );
        FileUtils.createIfMissing( file, FileUtils.FileType.FILE );
        return YamlConfiguration.loadConfiguration( file );
    }
    
}
