package com.exorath.game.api.maps;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.exorath.game.GameAPI;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.api.Properties;
import com.google.common.collect.Maps;

/**
 * @author Nick Robson
 */
public class GameMap {

    private static final Map<String, GameMap> ALL = Maps.newHashMap();

    public static List<GameMap> getFoundMaps() {
        return ALL.values().stream().collect(Collectors.toList());
    }

    public static void loadAll() {
        for (World world : Bukkit.getWorlds())
            try {
                get(world);
            } catch (IllegalArgumentException ignored) {
                // thrown in new GameMap(World) when there's no game data file
            }
    }

    public static GameMap get(World world) {
        if (world == null) {
            GameAPI.error("Tried to get a map from a null world.");
            return null;
        }
        ALL.computeIfAbsent(world.getName(), s -> new GameMap(world));
        return ALL.get(world.getName());
    }

    private final String worldName;
    private final Properties properties = new Properties();
    private final Map<String, Spawns> spawns = Maps.newHashMap();
    private final Map<String, Object> customData = Maps.newHashMap();

    private GameMap(World world) {
        File gamedata = new File(world.getWorldFolder(), "exorath.yml");
        Validate.isTrue(gamedata.exists(), world.getName() + " does not support GameAPI");
        FileConfiguration config = YamlConfiguration.loadConfiguration(gamedata);

        worldName = world.getName();

        properties.set(MapProperty.NAME, config.getString("name"));
        properties.set(MapProperty.VERSION, config.getString("version"));
        properties.set(MapProperty.CREATOR, config.getString("creator"));
        properties.set(MapProperty.DESCRIPTION, config.getString("description"));

        if (config.isConfigurationSection("spawns")) {
            ConfigurationSection cs = config.getConfigurationSection("spawns");
            for (String key : cs.getKeys(false))
                if (cs.isList(key)) {
                    int id = 0;
                    for (String sp : cs.getStringList(key)) {
                        String[] data = sp.split(";");
                        Location loc = new Location(world, Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]),
                                Float.parseFloat(data[3]), Float.parseFloat(data[4]));
                        Spawn spawn = new Spawn(id++, loc);
                        getSpawns(key, true).addSpawn(spawn);
                    }
                }
        }

        if (config.isConfigurationSection("custom"))
            customData.putAll(config.getConfigurationSection("custom").getValues(true));

    }

    public Spawns getSpawns(String team) {
        return getSpawns(team, false);
    }

    public Spawns getSpawns(String team, boolean create) {
        if (team == null)
            team = "global";
        if (create) {
            Spawns sp = new Spawns();
            spawns.put(team, sp);
            return sp;
        }
        return spawns.get(team);
    }

    public String getWorldName() {
        return worldName;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public Properties getProperties() {
        return properties;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public String getName() {
        return getProperties().as(MapProperty.NAME, String.class);
    }

    public void reset() {
        for (Spawns spawns : this.spawns.values())
            spawns.reset();
    }

}
