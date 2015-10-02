package com.exorath.game.api;

import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.events.GameStateChangedEvent;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public interface GameListener {

    public default void onGameStateChange(GameStateChangedEvent event) {
    }

    public default void onLogin(PlayerLoginEvent event, Game game, GamePlayer player) {
    }

    public default void onJoin(PlayerJoinEvent event, Game game, GamePlayer player) {
    }

    public default void onQuit(PlayerQuitEvent event, Game game, GamePlayer player) {
    }

    public default void onKick(PlayerKickEvent event, Game game, GamePlayer player) {
    }

    public default void onChat(AsyncPlayerChatEvent event, Game game, GamePlayer player) {
    }

    public default void onDeath(PlayerDeathEvent event, Game game, GamePlayer player) {
    }

    public default void onRespawn(PlayerRespawnEvent event, Game game, GamePlayer player) {
    }

    public default void onInteract(PlayerInteractEvent event, Game game, GamePlayer player) {
    }

    public default void onDropItem(PlayerDropItemEvent event, Game game, GamePlayer player) {
    }

    public default void onPickupItem(PlayerPickupItemEvent event, Game game, GamePlayer player) {
    }

    public default void onFish(PlayerFishEvent event, Game game, GamePlayer player) {
    }

    public default void onExpChange(PlayerExpChangeEvent event, Game game, GamePlayer player) {
    }
    public default void onFoodLevelChange(FoodLevelChangeEvent event, Game game, GamePlayer player) {
    }
    public default void onGamemodeChange(PlayerGameModeChangeEvent event, Game game, GamePlayer player) {
    }

    public default void onInteractEntity(PlayerInteractEntityEvent event, Game game, GamePlayer player) {
    }

    public default void onInteractAtEntity(PlayerInteractAtEntityEvent event, Game game, GamePlayer player) {
    }

    public default void onWorldChange(PlayerChangedWorldEvent event, Game game, GamePlayer player) {
    }

    public default void onLevelChange(PlayerLevelChangeEvent event, Game game, GamePlayer player) {
    }

    public default void onMove(PlayerMoveEvent event, Game game, GamePlayer player) {
    }

    public default void onVelocity(PlayerVelocityEvent event, Game game, GamePlayer player) {
    }

    public default void onPortal(PlayerPortalEvent event, Game game, GamePlayer player) {
    }

    public default void onShear(PlayerShearEntityEvent event, Game game, GamePlayer player) {
    }

    public default void onTeleport(PlayerTeleportEvent event, Game game, GamePlayer player) {
    }

    public default void onToggleFlight(PlayerToggleFlightEvent event, Game game, GamePlayer player) {
    }

    public default void onToggleSneak(PlayerToggleSneakEvent event, Game game, GamePlayer player) {
    }

    public default void onToggleSprint(PlayerToggleSprintEvent event, Game game, GamePlayer player) {
    }

    public default void onBlockBreak(BlockBreakEvent event, Game game, GamePlayer player) {
    }

    public default void onBlockPlace(BlockPlaceEvent event, Game game, GamePlayer player) {
    }

    public default void onSignChange(SignChangeEvent event, Game game, GamePlayer player) {
    }

    public default void onPlayerKillPlayer(GamePlayerKillPlayerEvent event) {
    }

    /**
     * Called when an {@link EntityDamageEvent} is called.
     *
     * @param event
     *            The handled event.
     * @param player
     *            The player being damaged.
     */
    public default void onPlayerDamage(EntityDamageEvent event, Game game, GamePlayer player) {
    }


    public default void onEntityDamageByPlayer(EntityDamageByEntityEvent event, Game game, GamePlayer attacker,
            Entity defender) {
    }
    public default void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Game game, Entity attacker,
                                               GamePlayer defender) {
    }
    /*
     * Note: If your listening to this method within the team listeners, it will only be send to the attacking team.
     * Note: If your listening to this method within the player listeners, it will only be send to the attacking player.
     */
    public default void onPlayerDamageByPlayer(EntityDamageByEntityEvent event, Game game, GamePlayer attacker,
                                               GamePlayer defender) {
    }

}
