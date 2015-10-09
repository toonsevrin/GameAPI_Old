package com.exorath.game;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProvider;
import com.exorath.game.api.config.ConfigurationManager;
import com.exorath.game.api.database.SQLManager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.nms.NMSProvider;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.api.type.minigame.kit.Kit;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import me.nickrobson.lib.util.GameUtil;

/**
 * The main class.
 */
public class GameAPI extends JavaPlugin implements Listener {

    public static final Version CURRENT_VERSION = Version.from("GameAPI", "0.0.1", 1, 0);// API Version 0 means in Development. Change for Alpha/Beta.

    private static SQLManager sqlManager;
    private static Set<Game> games = new HashSet<>();
    private static String gameProvider;
    public static final GsonBuilder GSON_BUILDER;
    public static final Gson GSON;

    static {
        GSON_BUILDER = new GsonBuilder();
        GSON = GSON_BUILDER.setPrettyPrinting().create();
    }

    public static void registerGameProvider(GameProvider plugin) {
        if (gameProvider != null)
            throw new IllegalStateException("A GameProvider was already registered.");
        gameProvider = plugin.getName();
        getGame();
    }

    public static Game getGame() {
        if (games.isEmpty() && gameProvider != null) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(gameProvider);
            if (plugin instanceof GameProvider) {
                Game game = ((GameProvider) plugin).create();
                game.setHost((GameProvider) plugin);
                if (game != null)
                    games.add(game);
            }
        }
        System.out.println("Size: " + games.size());
        return games.size() == 1 ? games.stream().findAny().get() : null;//If more then 1 game return null for the moment
    }

    public static Game getGame(UUID uuid) {
        return uuid == null ? null : games.stream().filter(g -> g.getGameID().equals(uuid)).findAny().orElse(null);
    }

    public static GamePlayer getPlayer(UUID uuid) {
        return getOnlinePlayers().stream().filter(p -> p.getUUID().equals(uuid)).findAny().orElse(null);
    }

    public static Set<GamePlayer> getOnlinePlayers() {
        Set<GamePlayer> players = new HashSet<>();
        games.forEach(g -> players.addAll(g.getOnlinePlayers()));
        return players;
    }

    public static GamePlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    private FileConfiguration versionsConfig;

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new GameAPIListener(), this);

        File databaseConfigFile = GameAPI.getConfigurationManager().getConfigFile(this, "database");

        GameAPI.getConfigurationManager().saveResource(this, "configs/database", databaseConfigFile, false);

        FileConfiguration databaseConfig = GameAPI.getConfigurationManager().getConfig(databaseConfigFile);

        GameAPI.sqlManager = new SQLManager(databaseConfig.getString("host"), databaseConfig.getInt("port"),
                databaseConfig.getString("database"), databaseConfig.getString("username"),
                databaseConfig.getString("password"));

        String serverPackage = getServer().getClass().getPackage().getName();
        String versionPackage = serverPackage.substring(serverPackage.lastIndexOf('.'));
        try {
            Class<? extends NMSProvider> c = Class
                    .forName(NMSProvider.class.getPackage().getName() + versionPackage + ".NMSProviderImpl")
                    .asSubclass(NMSProvider.class);
            NMSProvider provider = c.newInstance();
            NMS.set(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }

        versionsConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "versions.yml"));

        GameMap.loadAll();

        getServer().getScheduler().runTaskLater(this, new Runnable() {

            @Override
            public void run() {
                Game game = getGame();
                if (game == null)
                    return;
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p == null)
                        continue;
                    GamePlayer gp = game.getManager(PlayerManager.class).login(p.getUniqueId());
                    gp.join();
                    game.join(gp);
                }
            }

        }, 5);

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (GamePlayer player : getOnlinePlayers()) {
                Kit kit = player.getKit();
                Player p = player.getBukkitPlayer();
                if (p != null && kit != null)
                    kit.getPotionEffects().forEach((type, level) -> {
                        p.addPotionEffect(new PotionEffect(type, 160, level, true));
                    });
            }
        } , 100, 100);

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
     * @param error
     *            message you want to print.
     */
    public static void error(String error) {
        error = error.replace("\n", "\n[GameAPI] [ERROR] ");
        System.err.println("[GameAPI] [ERROR] " + error);
    }

    public static void printConsole(String message) {
        message = message.replace("\n", "\n[GameAPI] [INFO] ");
        System.out.println("[GameAPI] [INFO] " + message);
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
        return versionsConfig;
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
        if (player != null && player.isOnline())
            GameAPI.sendPlayerToServer(player.getBukkitPlayer(), server);
    }

    public static void sendPlayersToServer(String server, Collection<? extends Player> players) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        byte[] bytes = out.toByteArray();
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(GameAPI.getInstance(), "BungeeCord"))
            Bukkit.getMessenger().registerOutgoingPluginChannel(GameAPI.getInstance(), "BungeeCord");
        for (Player player : players)
            player.sendPluginMessage(GameAPI.getInstance(), "BungeeCord", bytes);
    }

    public static void sendGamePlayersToServer(String server, Collection<? extends GamePlayer> players) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        byte[] bytes = out.toByteArray();
        if (!Bukkit.getMessenger().isOutgoingChannelRegistered(GameAPI.getInstance(), "BungeeCord"))
            Bukkit.getMessenger().registerOutgoingPluginChannel(GameAPI.getInstance(), "BungeeCord");
        for (GamePlayer player : players)
            if (player.isOnline())
                player.getBukkitPlayer().sendPluginMessage(GameAPI.getInstance(), "BungeeCord", bytes);
    }

    public File getDataFolder(Game game) {
        return new File(this.getDataFolder(), game.getName().toLowerCase().replaceAll(" ", "_"));
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().isOp())
            return;
        String msg = event.getMessage();
        if (msg.startsWith("/"))
            msg = msg.substring(1);
        if (msg.startsWith("stop")) {
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Sending you to the hub.");
            sendPlayersToServer("Hub", Bukkit.getOnlinePlayers());
            Bukkit.shutdown();
        }
    }

}
