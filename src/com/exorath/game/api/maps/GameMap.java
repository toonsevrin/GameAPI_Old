package com.exorath.game.api.maps;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerState;
import com.exorath.game.api.team.TeamColor;
import com.exorath.game.api.team.TeamManager;
import com.google.common.collect.Lists;
import com.yoshigenius.lib.serializable.Serializer;

public class GameMap {

    public static List<GameMap> getFoundMaps() {
        return Lists.newArrayList(worlds.values());
    }

    // BASIC

    static final java.util.Map<String, GameMap> worlds = new HashMap<>();

    public static GameMap get(String map) {
        for (GameMap m : GameMap.worlds.values())
            if (m.getName().equals(map))
                return m;
        return null;
    }

    public static GameMap get(World world) {
        return GameMap.get(world, true);
    }

    public static GameMap get(World world, boolean load) {
        for (GameMap map : GameMap.worlds.values())
            if (map.getFolder().equals(world.getWorldFolder()))
                return map;
        if (load) {
            GameMap loaded = GameMap.loadFrom(world);
            if (loaded != null)
                return loaded;
        }
        return null;
    }

    public static GameMap loadFrom(World world) {

        GameMap seed;
        if ((seed = GameMap.get(world, false)) != null)
            return seed;

        File gamedata = new File(world.getWorldFolder(), "gamedata.yml");
        if (!gamedata.exists())
            return null;
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(gamedata);

        GameMap map = new GameMap(cfg.getString("game"), cfg.getString("name"), world);

        map.spectatorSpawns = cfg.getStringList("spectators").stream().map(Serializer::deserialize)
                .filter(s -> s instanceof GameSpawn).map(s -> (GameSpawn) s).collect(Collectors.toList());

        ConfigurationSection ss = cfg.getConfigurationSection("spawns");

        for (String key : ss.getKeys(false))
            if (ss.isList(key)) {
                List<GameSpawn> list = ss.getStringList("global").stream().map(Serializer::deserialize)
                        .filter(s -> s instanceof GameSpawn).map(s -> (GameSpawn) s).collect(Collectors.toList());
                if (key.equals("global"))
                    map.global = list;
                else
                    try {
                        TeamColor team = TeamColor.valueOf(key.toUpperCase());
                        if (team != null)
                            map.spawns.put(team, list);
                    } catch (Exception ex) {
                    }
            }

        GameMap.worlds.put(world.getName(), map);

        return map;
    }

    public static void loadWorlds() {
        for (World world : Bukkit.getWorlds())
            loadFrom(world);
    }

    private String name, gameName;
    private File folder;
    private FileConfiguration config;

    public GameMap(String gameName, String name, World world) {
        this(gameName, name, world == null ? null : world.getWorldFolder());
    }

    public GameMap(String gameName, String name, File folder) {
        Validate.notNull(gameName, "Game name cannot be null");
        Validate.notNull(name, "Map name cannot be null");
        Validate.notNull(folder, "Folder cannot be null");
        this.gameName = gameName;
        this.name = name;
        this.folder = folder;
    }

    public String getGameName() {
        return gameName;
    }

    public String getName() {
        return name;
    }

    public File getFolder() {
        return folder;
    }

    public void save() {
        FileConfiguration cfg = getConfig();
        cfg.set("game", getGameName());
        cfg.set("name", getName());
        cfg.set("spectators", spectatorSpawns.stream().map(Serializer::serialize).collect(Collectors.toList()));
        cfg.set("spawns.global", global.stream().map(Serializer::serialize).collect(Collectors.toList()));
        for (Entry<TeamColor, List<GameSpawn>> entry : spawns.entrySet())
            if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().isEmpty())
                cfg.set("spawns." + entry.getKey().name().toLowerCase(),
                        entry.getValue().stream().map(Serializer::serialize).collect(Collectors.toList()));
        saveConfig();
    }

    // ADVANCED

    private List<GameSpawn> global = new LinkedList<>();
    private List<GameSpawn> spectatorSpawns = new LinkedList<>();
    private final Map<TeamColor, List<GameSpawn>> spawns = new HashMap<>();

    // spawns

    private List<GameSpawn> getSpawnsList(TeamColor team) {
        if (team == null)
            return global;
        if (!spawns.containsKey(team))
            spawns.put(team, new LinkedList<>());
        return spawns.get(team);
    }

    public GameSpawn[] getSpawns(TeamColor team) {
        List<GameSpawn> spawns = getSpawnsList(team);
        return spawns == null ? new GameSpawn[0] : spawns.toArray(new GameSpawn[spawns.size()]);
    }

    public GameSpawn getSpawn(int x) {
        return this.getSpawn(null, x);
    }

    public GameSpawn getSpawn(TeamColor team, int x) {
        if (x < 0)
            return null;
        List<GameSpawn> spawns = getSpawnsList(team);
        while (x >= spawns.size())
            // will then say to use 2, which is valid.
            x -= spawns.size();// e.g. if x = 4, spawns = 4, it will say to use 0, which is valid.
        return getSpawns(team)[x];
    }

    public void setSpawn(TeamColor team, int spawn, Location loc) {
        if (spawn < 0)
            return;
        GameSpawn location = new GameSpawn(loc);
        List<GameSpawn> spawns = getSpawnsList(team);
        if (spawn > spawns.size())
            spawn = spawns.size();
        if (spawns.size() > spawn) {
            spawns.remove(spawn);
            spawns.add(spawn, location);
        } else
            spawns.add(location);
    }

    public void addSpawn(TeamColor team, GameSpawn spawn) {
        getSpawnsList(team).add(spawn);
    }

    // spectator spawns

    public GameSpawn[] getSpectatorSpawns() {
        return spectatorSpawns.toArray(new GameSpawn[spectatorSpawns.size()]);
    }

    public void setSpectatorSpawn(int spawn, Location loc) {
        if (spawn < 0)
            return;
        GameSpawn location = new GameSpawn(loc);
        if (spectatorSpawns.size() > spawn) {
            spectatorSpawns.remove(spawn);
            spectatorSpawns.add(spawn, location);
        } else
            spectatorSpawns.add(location);
    }

    public GameSpawn findSpawn(GamePlayer player) {
        Game game = player.getGame();
        if (game != null) {
            PlayerState state = player.getState();
            if (state == PlayerState.PLAYING) {
                TeamManager teams = game.getManager(TeamManager.class);
                TeamColor team = teams == null ? null : teams.getTeam(player).getTeamColor();
                List<GameSpawn> spawns = getSpawnsList(team);
                if (spawns != null)
                    return spawns.get(new Random().nextInt(spawns.size()));
            }
        }
        return null;
    }

    // World

    public World getWorld() {
        for (String worldName : GameMap.worlds.keySet())
            if (GameMap.worlds.get(worldName) == this)
                return Bukkit.getWorld(worldName);
        return null;
    }

    // Configuration

    public FileConfiguration getConfig() {
        if (config != null)
            return config;
        File gamedata = new File(folder, "gamedata.yml");
        if (!gamedata.exists())
            return null;
        return config = YamlConfiguration.loadConfiguration(gamedata);
    }

    public void saveConfig() {
        try {
            getConfig().save(new File(folder, "gamedata.yml"));
        } catch (Exception ignored) {
        }
    }

    public void reset() {
        // TODO: Things like undoing changes to the world.
    }

}
