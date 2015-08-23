package com.exorath.game.lib.hud.bossBar;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.hud.bossBar.nms.FakeDragon;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

/**
 * Created by TOON on 8/11/2015.
 */
public class BossBarAPI implements Listener {
    private static HashMap<UUID, FakeDragon> players = new HashMap<UUID, FakeDragon>();

    public void onDisable() {
        for (Player player : GameAPI.getInstance().getServer().getOnlinePlayers()) {
            quit(player);
        }

        players.clear();

    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void PlayerLoggout(PlayerQuitEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        quit(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        handleTeleport(event.getPlayer(), event.getTo().clone());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerRespawnEvent event) {
        handleTeleport(event.getPlayer(), event.getRespawnLocation().clone());
    }
    private void handleTeleport(final Player player, final Location loc) {

        if (!hasBar(player))
            return;

        Bukkit.getScheduler().runTaskLater(GameAPI.getInstance(), new Runnable() {

            @Override
            public void run() {
                // Check if the player still has a dragon after the two ticks! ;)
                if (!hasBar(player))
                    return;

                FakeDragon oldDragon = getDragon(player, "");

                float health = oldDragon.health;
                String message = oldDragon.name;

                Util.sendPacket(player, getDragon(player, "").getDestroyPacket());

                players.remove(player.getUniqueId());

                FakeDragon dragon = addDragon(player, loc, message);
                dragon.health = health;

                sendDragon(dragon, player);
            }

        }, 2L);
    }
    private void quit(Player player) {
        removeBar(player);
    }
    /**
     * Set a message for the given player.<br>
     * It will remain there until the player logs off or another plugin overrides it.<br>
     * This method will show a full health bar and will cancel any running timers.
     *
     * @param player
     *            The player who should see the given message.
     * @param message
     *            The message shown to the player.<br>
     *            Due to limitations in Minecraft this message cannot be longer than 64 characters.<br>
     *            It will be cut to that size automatically.
     */
    public static void setMessage(Player player, String message) {
        FakeDragon dragon = getDragon(player, message);

        dragon.name = cleanMessage(message);
        dragon.health = dragon.getMaxHealth();

        sendDragon(dragon, player);
    }

    /**
     * Checks whether the given player has a bar.
     *
     * @param player
     *            The player who should be checked.
     * @return True, if the player has a bar, False otherwise.
     */
    public static boolean hasBar(Player player) {
        return players.get(player.getUniqueId()) != null;
    }

    /**
     * Removes the bar from the given player.<br>
     * If the player has no bar, this method does nothing.
     *
     * @param player
     *            The player whose bar should be removed.
     */
    public static void removeBar(Player player) {
        if (!hasBar(player))
            return;

        Util.sendPacket(player, getDragon(player, "").getDestroyPacket());

        players.remove(player.getUniqueId());
    }

    /**
     * Modifies the health of an existing bar.<br>
     * If the player has no bar, this method does nothing.
     *
     * @param player
     *            The player whose bar should be modified.
     * @param percent
     *            The percentage of the health bar filled.<br>
     *            This value must be between 0F and 100F (inclusive).
     */
    public static void setHealth(Player player, float percent) {
        if (!hasBar(player))
            return;

        FakeDragon dragon = getDragon(player, "");
        dragon.health = (percent / 100f) * dragon.getMaxHealth();

        if (percent == 0) {
            removeBar(player);
        } else {
            sendDragon(dragon, player);
        }
    }

    /**
     * Get the health of an existing bar.
     *
     * @param player
     *            The player whose bar's health should be returned.
     * @return The current absolute health of the bar.<br>
     *         If the player has no bar, this method returns -1.
     */
    public static float getHealth(Player player) {
        if (!hasBar(player))
            return -1;

        return getDragon(player, "").health;
    }

    private static String cleanMessage(String message) {
        if (message.length() > 64)
            message = message.substring(0, 63);

        return message;
    }

    private static void sendDragon(FakeDragon dragon, Player player) {
        Util.sendPacket(player, dragon.getMetaPacket(dragon.getWatcher()));
        Util.sendPacket(player, dragon.getTeleportPacket(getDragonLocation(player.getLocation())));
    }

    private static FakeDragon getDragon(Player player, String message) {
        if (hasBar(player)) {
            return players.get(player.getUniqueId());
        } else
            return addDragon(player, cleanMessage(message));
    }

    private static FakeDragon addDragon(Player player, String message) {
        FakeDragon dragon = Util.newDragon(message, getDragonLocation(player.getLocation()));

        Util.sendPacket(player, dragon.getSpawnPacket());

        players.put(player.getUniqueId(), dragon);

        return dragon;
    }

    private static FakeDragon addDragon(Player player, Location loc, String message) {
        FakeDragon dragon = Util.newDragon(message, getDragonLocation(loc));

        Util.sendPacket(player, dragon.getSpawnPacket());

        players.put(player.getUniqueId(), dragon);

        return dragon;
    }

    private static Location getDragonLocation(Location loc) {
        if (Util.isBelowGround) {
            loc.subtract(0, 300, 0);
            return loc;
        }

        float pitch = loc.getPitch();

        if (pitch >= 55) {
            loc.add(0, -300, 0);
        } else if (pitch <= -55) {
            loc.add(0, 300, 0);
        } else {
            loc = loc.getBlock().getRelative(getDirection(loc), GameAPI.getInstance().getServer().getViewDistance() * 16).getLocation();
        }

        return loc;
    }

    private static BlockFace getDirection(Location loc) {
        float dir = Math.round(loc.getYaw() / 90);
        if (dir == -4 || dir == 0 || dir == 4)
            return BlockFace.SOUTH;
        if (dir == -1 || dir == 3)
            return BlockFace.EAST;
        if (dir == -2 || dir == 2)
            return BlockFace.NORTH;
        if (dir == -3 || dir == 1)
            return BlockFace.WEST;
        return null;
    }
}