package com.exorath.game.api;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.exorath.game.lib.JoinLeave;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.GameAPI;
import com.exorath.game.api.action.Actions;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.lib.util.FileUtils;
import com.google.common.collect.Sets;

/**
 * The class representing a single Game instance.
 * Gamemode and RepeatingMinigame extend this.
 *
 * @author Toon Sevrin
 * @author Nick Robson
 */

public abstract class Game implements JoinLeave{

    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "Default game description";

    private GameProvider host;

    private final UUID gameID;
    private final Set<GameListener> listeners = Sets.newHashSet();
    private final Set<Manager> managers = new HashSet<>();

    private Lobby lobby = new Lobby();
    private Properties properties = new Properties();
    private Actions actions = new Actions();
    private GameState state;

    public Game() {
        gameID = UUID.randomUUID();
        addManager(new PlayerManager(this));
        Manager[] baseManagers = new Manager[] { new HUDManager(this), new MapManager(this), new PlayerManager(this)};
        Arrays.asList(baseManagers).forEach(manager -> addManager(manager));
    }
    //** Join & Leave **//
    @Override
    public void join(GamePlayer player) {
        for(Manager manager : managers)
            if(manager instanceof JoinLeave)
                ((JoinLeave) manager).join(player);
        if(lobby != null)
            lobby.join(player);
    }

    @Override
    public void leave(GamePlayer player) {
        for(Manager manager : managers)
            if(manager instanceof JoinLeave)
                ((JoinLeave) manager).leave(player);
        if(lobby != null)
            lobby.leave(player);
    }
    /* Game ID */
    public final UUID getGameID() {
        return gameID;
    }

    /* Plugin Host */
    public GameProvider getHost() {
        return host;
    }

    public void setHost(GameProvider host) {
        if (this.host == null)
            this.host = host;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        setState(state, true);

        GameAPI.printConsole("Game state set to " + state.toString());
    }

    public void setState(GameState state, boolean callEvent) {
        GameState old = this.state;
        this.state = state;
        if (callEvent) {
            GameStateChangedEvent event = new GameStateChangedEvent(this, old, state);
            for (GameListener listener : getListeners())
                listener.onGameStateChange(event);
        }
    }

    /* Lobby */
    public Lobby getLobby() {
        return lobby;
    }

    protected void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /* Managers */
    public Set<Manager> getManagers() {
        return managers;
    }

    public void addManager(Manager manager) {
        Class<?> managerClass = manager.getClass();
        if (!Arrays.asList(managerClass.getInterfaces()).contains(Manager.class))
            return;
        for (Manager m : managers)// check if there's a manager of that type already!
            if (managerClass.isAssignableFrom(m.getClass()))
                return;
        managers.add(manager);
    }

    /**
     * Gets the acting manager instance of the provided class.
     *
     * @param clazz
     *            The class of which the manager is intended to extend.
     * @return The manager is there is one of this type, or null if no manager
     *         of this type is set. Null if
     */
    @SuppressWarnings("unchecked")
    public <T extends Manager> T getManager(Class<T> clazz) {
        if (clazz == Manager.class)
            return null;
        for (Manager manager : managers)
            if (clazz.isAssignableFrom(manager.getClass()))
                return (T) manager;
        return null;
    }

    /* Properties */
    public Properties getProperties() {
        return properties;
    }

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getName() {
        return properties.as(GameProperty.NAME, String.class);
    }

    public void setName(String name) {
        properties.set(GameProperty.NAME, name);
    }

    public String getDescription() {
        return properties.as(GameProperty.DESCRIPTION, String.class);
    }

    public void setDescription(String description) {
        properties.set(GameProperty.DESCRIPTION, description);
    }

    public Set<GamePlayer> getOnlinePlayers() {
        return getManager(PlayerManager.class).getPlayers();
    }

    /* Actions */
    public Actions getActions() {
        return actions;
    }

    /* Listeners */
    public void addListener(GameListener listener) {
        if (listener != null)
            listeners.add(listener);
    }

    public Set<GameListener> getListeners() {
        return listeners;
    }

    /* Configs */
    //** TODO: Take a look at the configuration manager (Is this required?) **//
    public FileConfiguration getConfig(String filename) {
        File file = new File(GameAPI.getInstance().getDataFolder(this),
                filename + (filename.endsWith(".yml") ? "" : ".yml"));
        FileUtils.createIfMissing(file, FileUtils.FileType.FILE);
        return YamlConfiguration.loadConfiguration(file);
    }
}
