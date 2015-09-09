package com.exorath.game.api.team;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import com.exorath.game.GameAPI;
import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Properties;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.GameSpawn;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;

/**
 * Created by Toon Sevrin on 23/05/2015.
 * Base object for a Team.
 */
public class Team {

    protected static final String DEFAULT_NAME = "team";
    protected static final int DEFAULT_MAX_PLAYER_AMOUNT = Bukkit.getServer().getMaxPlayers();

    private Properties properties = new Properties();

    private Set<UUID> players = new HashSet<>(), activePlayers = new HashSet<>();

    private final Set<GameListener> listeners = new HashSet<>();

    //** Player Methods **//
    /* Getters */
    public Set<GamePlayer> getPlayers() {
        return players.stream().map(u -> GameAPI.getPlayer(u)).collect(Collectors.toSet());
    }
    public Set<UUID> getActivePlayers() {
        return activePlayers;
    }
    /* Add - Remove */
    public void addPlayer(GamePlayer player) {
        players.add(player.getUUID());
        activePlayers.add(player.getUUID());
    }
    public void removePlayer(GamePlayer player) {
        if (players.contains(player))
            players.remove(player);
        if (activePlayers.contains(player))
            activePlayers.remove(player);
    }
    public void removeOfflinePlayers() {
        Iterator<UUID> it = this.players.iterator();
        while (it.hasNext()) {
            UUID uuid = it.next();
            if (Bukkit.getPlayer(uuid) == null) {
                it.remove();
                if (activePlayers.contains(uuid))
                    activePlayers.remove(uuid);
            }
        }
    }
    /* Contains & Tests */
    public boolean containsPlayer(GamePlayer player) {
        return players.contains(player);
    }
    public boolean containsActivePlayer(GamePlayer player) {
        return players.contains(player);
    }
    public boolean hasEnoughPlayers(){
        return players.size() >= properties.as(TeamProperty.MIN_SIZE, Integer.class);
    }

    //** Properties **//
    public Properties getProperties() {
        return this.properties;
    }
    public Team setName(String name) {
        this.properties.set(TeamProperty.NAME, name);
        return this;
    }
    public String getName() {
        return this.properties.as(TeamProperty.NAME, String.class);
    }
    public Team setPvpEnabled(boolean enabled) {
        this.properties.set(BasePlayerProperty.PVP, enabled);
        return this;
    }
    public boolean isPvpEnabled() {
        return this.properties.as(BasePlayerProperty.PVP, boolean.class);
    }
    public Team setMaxTeamSize(int amount) {
        this.properties.set(TeamProperty.MAX_SIZE, amount);
        return this;
    }
    public int getMaxTeamSize() {
        return this.properties.as(TeamProperty.MAX_SIZE, int.class);
    }
    public Team setMinTeamSize(int amount) {
        this.properties.set(TeamProperty.MIN_SIZE, amount);
        return this;
    }
    public int getMinTeamSize() {
        return this.properties.as(TeamProperty.MIN_SIZE, int.class);
    }
    public boolean isFriendlyFire() {
        return this.properties.as(TeamProperty.FRIENDLY_FIRE, boolean.class);
    }
    public Team setFriendlyFire(boolean ff) {
        this.properties.set(TeamProperty.FRIENDLY_FIRE, ff);
        return this;
    }
    public TeamColor getTeamColor() {
        return this.properties.as(TeamProperty.COLOR, TeamColor.class);
    }
    public Team setTeamColor(TeamColor color) {
        this.properties.set(TeamProperty.COLOR, color);
        return this;
    }
        //** Spawns **//
    public GameSpawn[] getSpawns(GameMap map) {
        return map.getSpawns(this);
    }
    //** Listeners **//
    public Set<GameListener> getListeners() {
        return listeners;
    }
    public void addListener(GameListener listener) {
        if (listener != null)
            this.listeners.add(listener);
    }

}
