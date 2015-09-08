package com.exorath.game;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProvider;
import com.exorath.game.api.config.ConfigurationManager;
import com.exorath.game.api.database.SQLManager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.nms.NMSProvider;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yoshigenius.lib.util.GameUtil;

/**
 * The main class.
 */
public class GameAPI extends JavaPlugin {

    public static final Version CURRENT_VERSION = Version.from("GameAPI", "0.0.1", 1, 0); // API Version 0 means in Development. Change for Alpha/Beta.

    private static SQLManager sqlManager;

    private static Map<UUID, GamePlayer> players = Maps.newHashMap();
    private static Map<UUID, Game> games = Maps.newHashMap();
    private static String gameProvider;
    public static final GsonBuilder GSON_BUILDER;
    public static final Gson GSON;

    static {
        GSON_BUILDER = new GsonBuilder();
        GSON = GSON_BUILDER.setPrettyPrinting().create();
    }

    public static void registerGameProvider(GameProvider plugin) {
        if(gameProvider != null)
            throw new IllegalStateException("A GameProvider was already registered.");
        gameProvider = plugin.getName();
        getGame();
    }

    public static Game getGame() {
        if (games.isEmpty() && gameProvider != null) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(gameProvider);
            if (plugin instanceof GameProvider) {
                Game g = ((GameProvider) plugin).create();
                if (g != null) {
                    games.put(g.getGameID(), g);
                }
            }
        }
        GameAPI.printConsole("Games: " + games.size());
        return games.size() == 1 ? games.values().stream().findAny().get() : null;//If more then 1 game return null for the moment
    }

    public static Game getGame(UUID uuid) {
        return uuid == null ? null : games.get(uuid);
    }

    public static GamePlayer getPlayer(UUID uuid) {
        return players.computeIfAbsent(uuid, u -> new GamePlayer(u));
    }

    public static GamePlayer getPlayer(Player player) {
        return players.computeIfAbsent(player.getUniqueId(), u -> new GamePlayer(u));
    }

    protected static void refreshOnlinePlayers() {
        players.entrySet().stream().filter(e -> !e.getValue().isOnline()).forEach(e -> players.remove(e.getKey()));
        Bukkit.getOnlinePlayers().stream().forEach(p -> getPlayer(p));
    }

    public static Set<GamePlayer> getOnlinePlayers() {
        refreshOnlinePlayers();
        return Sets.newHashSet(players.values());
    }

    private FileConfiguration versionsConfig;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new GameAPIListener(), this);

        File databaseConfigFile = GameAPI.getConfigurationManager().getConfigFile(this, "database");

        GameAPI.getConfigurationManager().saveResource(this, "configs/database", databaseConfigFile, false);

        FileConfiguration databaseConfig = GameAPI.getConfigurationManager().getConfig(databaseConfigFile);

        GameAPI.sqlManager = new SQLManager(databaseConfig.getString("host"), databaseConfig.getInt("port"),
                databaseConfig.getString("database"), databaseConfig.getString("username"),
                databaseConfig.getString("password"));

        String serverPackage = this.getServer().getClass().getPackage().getName();
        String versionPackage = serverPackage.substring(serverPackage.lastIndexOf('.'));
        try {
            Class<? extends NMSProvider> c = Class.forName(NMSProvider.class.getPackage().getName() + versionPackage + ".NMSProviderImpl")
                    .asSubclass(NMSProvider.class);
            NMSProvider provider = c.newInstance();
            NMS.set(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.versionsConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "versions.yml"));

        refreshOnlinePlayers();
        GameMap.loadWorlds();

    }

    @Override
    public void onDisable() {

    }

    @Override
    public File getFile() {
        return super.getFile();
    }

    /**
     * Prints an error to the console
     *
     * @param error message you want to print.
     */
    public static void error(String error) {
        GameAPI.getInstance().getLogger().severe(error);
    }

    public static void printConsole(String message) {
        GameAPI.getInstance().getLogger().info(message);
    }

    public static GameAPI getInstance() {
        return JavaPlugin.getPlugin(GameAPI.class);
    }

    public static SQLManager getSQLManager() {
        return GameAPI.sqlManager;
    }

    public static ConfigurationManager getConfigurationManager() {
        return ConfigurationManager.INSTANCE;
    }

    public FileConfiguration getVersionsConfig() {
        return this.versionsConfig;
    }

    public void saveVersionsConfig() {

    }

    public static void sendPlayerToServer(Player player, String server) {
        if (player != null && server != null) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            GameUtil.sendPluginMessage(player, "BungeeCord", out.toByteArray());
        }
    }

    public static void sendPlayerToServer(GamePlayer player, String server) {
        if (player != null && player.isOnline()) {
            GameAPI.sendPlayerToServer(player.getBukkitPlayer(), server);
        }
    }

    public static void sendPlayersToServer(String server, Collection<? extends Player> players) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        byte[] bytes = out.toByteArray();
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(GameAPI.getInstance(), "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(GameAPI.getInstance(), "BungeeCord");
        }
        for (Player player : players) {
            player.sendPluginMessage(GameAPI.getInstance(), "BungeeCord", bytes);
        }
    }

    public static void sendGamePlayersToServer(String server, Collection<? extends GamePlayer> players) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        byte[] bytes = out.toByteArray();
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(GameAPI.getInstance(), "BungeeCord")) {
            Bukkit.getMessenger().registerOutgoingPluginChannel(GameAPI.getInstance(), "BungeeCord");
        }
        for (GamePlayer player : players) {
            if (player.isOnline())
                player.getBukkitPlayer().sendPluginMessage(GameAPI.getInstance(), "BungeeCord", bytes);
        }
    }

    public File getDataFolder(Game game) {
        return new File(this.getDataFolder(), game.getName().toLowerCase().replaceAll(" ", "_"));
    }

}
