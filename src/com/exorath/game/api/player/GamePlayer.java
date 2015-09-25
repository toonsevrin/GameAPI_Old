package com.exorath.game.api.player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Properties;
import com.exorath.game.api.database.SQLData;
import com.exorath.game.api.hud.HUD;
import com.exorath.game.api.menu.Menu;
import com.exorath.game.lib.Rank;

@SuppressWarnings("unused")
/**
 * GamePlayer class.
 *
 * @author Nick Robson
 * @author Toon Sevrin
 */
public final class GamePlayer {

    private UUID uuid;

    private Game game;
    private Rank rank = Rank.NONE;
    private int coins = 0, credits = 0, wonCoins = 0;
    private Properties properties = new Properties();
    private Menu menu;

    private SQLData sqlData;
    private SQLData gSqlData;
    private UUID gameUID;

    private PlayerState state = PlayerState.UNKNOWN;

    private Set<GameListener> listeners = new HashSet<>();

    private HUD hud;

    public GamePlayer(UUID id) {
        uuid = id;
        gSqlData = new SQLData(GameAPI.getInstance(), "players", id, false);

        hud = new HUD(this);
        hud.getTitle().setOtherLocation(hud.getSubtitle());
        hud.getSubtitle().setOtherLocation(hud.getTitle());
    }

    public GamePlayer(Player player) {
        this(player.getUniqueId());
    }

    public Properties getProperties() {
        return properties;
    }

    protected void setProperties(Properties properties) {
        this.properties = properties;
    }

    public UUID getUUID() {
        return uuid;
    }

    public OfflinePlayer getOfflinePlayer() {
        Player p = Bukkit.getPlayer(uuid);
        return p != null ? p : Bukkit.getOfflinePlayer(uuid);
    }

    public boolean isOnline() {
        OfflinePlayer offline = getOfflinePlayer();
        return offline == null ? false : offline.isOnline();
    }

    public Player getBukkitPlayer() {
        OfflinePlayer player = getOfflinePlayer();
        if (player != null && player.isOnline())
            return player.getPlayer();
        return null;
    }

    public boolean isAlive() {
        return state == PlayerState.PLAYING;
    }

    //** Rank Methods *//
    public Rank getRank() {
        if (getApiSqlData().contains("rank"))
            return Rank.valueOf(getApiSqlData().getString("rank"));
        return Rank.NONE;
    }

    public void setRank(Rank rank) {
        getApiSqlData().setString("rank", rank.toString());
    }

    //** Currency Methods **//
    public int getCredits() {
        return gSqlData.getInt("credits", 0);
    }

    public void addCredits(int credits) {
        gSqlData.setInt("credits", gSqlData.getInt("credits", 0) + credits);
    }

    public void removeCredits(int credits) {
        gSqlData.setInt("credits", gSqlData.getInt("credits", 0) - credits);
    }

    public boolean hasCredits(int credits) {
        return gSqlData.getInt("credits", 0) >= credits;
    }

    public void addCoins(int coins) {
        wonCoins += coins;
        gSqlData.setInt("coins", gSqlData.getInt("coins", 0) + coins);
    }

    public void removeCoins(int coins) {
        wonCoins -= Math.min(coins, this.coins);
        gSqlData.setInt("coins", gSqlData.getInt("coins", 0) - coins);
        this.coins -= coins;
    }

    public int getCoins() {
        return gSqlData.getInt("coins", 0);
    }

    public int getCoinsWon() {
        return wonCoins - getCoins();
    }

    public boolean hasCoins(int coins) {
        return gSqlData.getInt("coins", 0) >= coins;
    }

    //** HUD **//
    public HUD getHud() {
        return hud;
    }

    //** Messaging Methods *//
    public void sendMessage(String message) {
        Player p = getBukkitPlayer();
        if (p != null)
            p.sendMessage(message);
    }

    public void sendMessage(String format, Object... params) {
        Player p = getBukkitPlayer();
        if (p != null)
            p.sendMessage(String.format(format, params));
    }

    //** Player State Methods *//
    public PlayerState getState() {
        if (state == null) {
            state = PlayerState.UNKNOWN;
        }
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    //** Menu Methods *//
    public boolean openMenu(Menu menu) {
        this.menu = menu;
        Player p = getBukkitPlayer();
        if (p != null && p.getOpenInventory() == null) {//  we don't want to aggressively open a menu if they shouldn't be opening...
            Inventory inv = Bukkit.createInventory(null, menu.getSize());
            menu.dump(inv);
            p.openInventory(inv);
            return true;
        }
        return false;
    }

    public boolean closeMenu() {
        Player p = getBukkitPlayer();
        if (p != null) {
            if (menu != null) {
                p.closeInventory();
                menu = null;
            } else
                return false;
        } else {
            menu = null;
            return false;
        }
        return true;
    }

    public Menu getCurrentMenu() {
        return menu;
    }

    //** SQLData Methods**//
    public SQLData getSqlData() {
        return sqlData;
    }

    public SQLData getApiSqlData() {
        return gSqlData;
    }

    public Game getGame() {
        if (game == null)
            game = GameAPI.getGame();
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void join() {
        gameUID = game.getGameID();
    }

    public Set<GameListener> getListeners() {
        return listeners;
    }

    public void addListener(GameListener listener) {
        if (listener != null)
            listeners.add(listener);
    }

}
