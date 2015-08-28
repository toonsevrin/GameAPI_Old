package com.exorath.game.api;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.GameAPI;
import com.exorath.game.api.action.Actions;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.gametype.minigame.kit.KitManager;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.player.Players;
import com.exorath.game.api.spectate.SpectateManager;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.lib.util.FileUtils;
import com.google.common.collect.Sets;

/**
 * The class representing a single Game instance.
 * Gamemode, RepeatingMinigame and Minigame extend this.
 *
 * @author Toon Sevrin
 * @author Nick Robson
 */

public abstract class Game {
    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "Default game description";

    private GameProvider host;

    private final UUID gameID;
    private final Set<GameListener> listeners = Sets.newHashSet();
    private final Set<Manager> managers = new HashSet<>();

    private Lobby lobby = new Lobby();
    private Properties properties = new Properties();
    private Players players = new Players(this);
    private Actions actions = new Actions();
    private GameState state;

    public Game() {
        this.gameID = UUID.randomUUID();
        addManager( new TeamManager( this ) );
        addManager( new SpectateManager( this ) );
        addManager( new KitManager( this ) );
        addManager( new MapManager( this ) );
        addManager( new HUDManager() );
    }

    /* Game ID */
    public final UUID getGameID() {
        return gameID;
    }

    /* Plugin Host */
    public GameProvider getHost() {
        return host;
    }

    protected void setHost( GameProvider host ) {
        this.host = host;
    }

    public GameState getState() {
        return this.state;
    }

    public void setState( GameState state ) {
        setState( state, true );
    }

    public void setState( GameState state, boolean callEvent ) {
        GameState old = this.state;
        this.state = state;
        if ( callEvent ) {
            GameStateChangedEvent event = new GameStateChangedEvent( this, old, state );
            for ( GameListener listener : getListeners() )
                listener.onGameStateChange( event );
        }
    }

    /* Lobby */
    public Lobby getLobby() {
        return this.lobby;
    }

    protected void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /* Managers */
    public Set<Manager> getManagers() {
        return managers;
    }

    public void addManager(Manager manager) {
        managers.add(manager);
    }

    /**
     * Gets the acting manager instance of the provided class.
     *
     * @param clazz
     *            The class of which the manager is intended to extend.
     * @return The manager is there is one of this type, or null if no manager of this type is set.
     */
    @SuppressWarnings( "unchecked" )
    public <T extends Manager> T getManager( Class<T> clazz ) {
        for ( Manager manager : managers ) {
            if ( clazz.isAssignableFrom( manager.getClass() ) ) {
                return (T) manager;
            }
        }
        return null;
    }

    /* Properties */
    public Properties getProperties() {
        return this.properties;
    }

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getName() {
        return this.properties.as(GameProperty.NAME, String.class);
    }

    public void setName(String name) {
        this.properties.set(GameProperty.NAME, name);
    }

    public String getDescription() {
        return this.properties.as(GameProperty.DESCRIPTION, String.class);
    }

    public void setDescription(String description) {
        this.properties.set(GameProperty.DESCRIPTION, description);
    }

    /* Actions */
    public Actions getActions() {
        return this.actions;
    }

    /* Players */
    public Players getPlayers() {
        return this.players;
    }

    public int getPlayerCount() {
        return this.players.getPlayingAmount();
    }

    /* Listeners */
    protected void addListener(GameListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    public Set<GameListener> getListeners() {
        return listeners;
    }

    /* Configs */
    //** TODO: Take a look at the configuration manager (Is this required?) **//
    public FileConfiguration getConfig(String filename) {
        File file = new File(GameAPI.getInstance().getDataFolder(this), filename + (filename.endsWith(".yml") ? "" : ".yml"));
        FileUtils.createIfMissing(file, FileUtils.FileType.FILE);
        return YamlConfiguration.loadConfiguration(file);
    }

}
