package com.exorath.game.api;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.exorath.game.api.gamestates.GameState;
import com.exorath.game.api.gamestates.StateManager;
import com.exorath.game.api.hud.HUDManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.GameAPI;
import com.exorath.game.api.action.Actions;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.lobby.Lobby;
import com.exorath.game.api.player.Players;
import com.exorath.game.api.spectate.SpectateManager;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.lib.util.FileUtils;
import com.google.common.collect.Sets;
import org.bukkit.plugin.Plugin;

/**
 * The class representing a single Game instance.
 * Gamemode, RepeatingMinigame and Minigame extend this.
 * TODO: Add the map manager
 *
 * @author Toon Sevrin
 * @author Nick Robson
 */

public abstract class Game {
    public static final String DEFAULT_GAME_NAME = "Game";
    public static final String DEFAULT_GAME_DESCRIPTION = "Default game description";

    public static enum StopCause {
        VICTORY,
        DRAW,
        TIME_UP;
    }

    private Plugin host;

    private final Set<GameListener> listeners = Sets.newHashSet();
    private Lobby lobby = new Lobby();
    private Properties properties = new Properties();

    private final Set<Manager> managers = new HashSet<>();
    private Players players = new Players(this);
    private Actions actions = new Actions();
    private String world = null;

    private UUID gameID;

    public Game() {
        GameAPI.getInstance().setGame(this);
        gameID = UUID.randomUUID();

        addManager(new TeamManager(this));
        addManager(new SpectateManager(this));
        addManager(new StateManager(this));
        addManager(new HUDManager());
    }

    /* Plugin Host */
    protected void setHost(GamePlugin host){
        this.host = host;
    }
    public Plugin getHost() {
        return host;
    }

    /* Game Map */
    //TODO: Step away from the bukkit World
    public World getGameWorld() {
        return Bukkit.getWorld(this.world);
    }
    public void setGameWorld(World world) {
        this.world = world.getName();
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

    /* Game ID */
    public UUID getGameID() {
        return gameID;
    }
    public void setGameID(UUID gameID) {
        this.gameID = gameID;
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
