package com.exorath.game;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.GameProvider;
import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.lobby.LobbyProperty;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.api.player.PlayerProperty;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.api.team.TeamProperty;

/**
 * @author Nick Robson
 */
public class GameAPIListener implements Listener {

    static Game getGame(GamePlayer gp) {
        if (gp == null)
            throw new NullPointerException("GamePlayer cannot be null");
        Game game = gp.getGame();
        if (game == null)
            throw new NullPointerException("GameAPI needs a plugin provider.");
        return game;
    }

    @EventHandler
    public void onPluginLoad(PluginEnableEvent event) {
        Plugin plg = event.getPlugin();
        if (plg instanceof GameProvider) {
            GameAPI.registerGameProvider((GameProvider) plg);
            GameAPI.getInstance().getLogger().info("Detected GameAPI provider: " + plg.getName());
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Game game = GameAPI.getGame();
        game.getManager(PlayerManager.class).login(event.getPlayer().getUniqueId());
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onLogin(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onLogin(event, game, gp));
        gp.getListeners().forEach(l -> l.onLogin(event, game, gp));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);
        gp.join();
        game.join(gp);
        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onJoin(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onJoin(event, game, gp));
        gp.getListeners().forEach(l -> l.onJoin(event, game, gp));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        if (gp == null || gp.getGame() == null)
            return;
        Game game = gp.getGame();
        game.leave(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onQuit(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onQuit(event, game, gp));
        gp.getListeners().forEach(l -> l.onQuit(event, game, gp));

        game.getManager(PlayerManager.class).leave(gp);
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onKick(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onKick(event, game, gp));
        gp.getListeners().forEach(l -> l.onKick(event, game, gp));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.CHAT, Boolean.class))
            event.setCancelled(true);
        game.getListeners().forEach(l -> l.onChat(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.CHAT))
                if (!team.getProperties().as(TeamProperty.CHAT, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onChat(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.CHAT))
            if (!gp.getProperties().as(PlayerProperty.CHAT, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onChat(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.CHAT, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getEntity());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onDeath(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onDeath(event, game, gp));
        gp.getListeners().forEach(l -> l.onDeath(event, game, gp));

        if (event.getEntity().getKiller() != null && game != null && gp != null) {
            GamePlayer killer = GameAPI.getPlayer(event.getEntity().getKiller());
            if (killer != null) {
                GamePlayerKillPlayerEvent gpke = new GamePlayerKillPlayerEvent(game, gp, killer);
                game.getListeners().forEach(l -> l.onPlayerKillPlayer(gpke));
                if (team != null)
                    team.getListeners().forEach(l -> l.onPlayerKillPlayer(gpke));
                gp.getListeners().forEach(l -> l.onPlayerKillPlayer(gpke));
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onRespawn(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onRespawn(event, game, gp));
        gp.getListeners().forEach(l -> l.onRespawn(event, game, gp));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.INTERACT, Boolean.class))
            event.setCancelled(true);

        game.getListeners().forEach(l -> l.onInteract(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.INTERACT))
                if (!team.getProperties().as(TeamProperty.INTERACT, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onInteract(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.INTERACT))
            if (!gp.getProperties().as(PlayerProperty.INTERACT, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onInteract(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.INTERACT, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.DROP_ITEMS, Boolean.class))
            event.setCancelled(true);

        game.getListeners().forEach(l -> l.onDropItem(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.DROP_ITEMS))
                if (!team.getProperties().as(TeamProperty.DROP_ITEMS, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onDropItem(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.DROP_ITEMS))
            if (!gp.getProperties().as(PlayerProperty.DROP_ITEMS, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onDropItem(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.DROP_ITEMS, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onPickupItem(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onPickupItem(event, game, gp));
        gp.getListeners().forEach(l -> l.onPickupItem(event, game, gp));
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onFish(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onFish(event, game, gp));
        gp.getListeners().forEach(l -> l.onFish(event, game, gp));
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onExpChange(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onExpChange(event, game, gp));
        gp.getListeners().forEach(l -> l.onExpChange(event, game, gp));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        GamePlayer gp = GameAPI.getPlayer((Player) event.getEntity());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.HUNGER, Boolean.class))
            event.setCancelled(true);

        game.getListeners().forEach(l -> l.onFoodLevelChange(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.HUNGER))
                if (!team.getProperties().as(TeamProperty.HUNGER, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onFoodLevelChange(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.HUNGER))
            if (!gp.getProperties().as(PlayerProperty.HUNGER, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onFoodLevelChange(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.HUNGER, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onGamemodeChange(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onGamemodeChange(event, game, gp));
        gp.getListeners().forEach(l -> l.onGamemodeChange(event, game, gp));
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onInteractEntity(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onInteractEntity(event, game, gp));
        gp.getListeners().forEach(l -> l.onInteractEntity(event, game, gp));
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onInteractAtEntity(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onInteractAtEntity(event, game, gp));
        gp.getListeners().forEach(l -> l.onInteractAtEntity(event, game, gp));
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onWorldChange(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onWorldChange(event, game, gp));
        gp.getListeners().forEach(l -> l.onWorldChange(event, game, gp));
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onLevelChange(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onLevelChange(event, game, gp));
        gp.getListeners().forEach(l -> l.onLevelChange(event, game, gp));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onMove(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onMove(event, game, gp));
        gp.getListeners().forEach(l -> l.onMove(event, game, gp));
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onVelocity(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onVelocity(event, game, gp));
        gp.getListeners().forEach(l -> l.onVelocity(event, game, gp));
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onPortal(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onPortal(event, game, gp));
        gp.getListeners().forEach(l -> l.onPortal(event, game, gp));
    }

    @EventHandler
    public void onShear(PlayerShearEntityEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onShear(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onShear(event, game, gp));
        gp.getListeners().forEach(l -> l.onShear(event, game, gp));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onTeleport(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onTeleport(event, game, gp));
        gp.getListeners().forEach(l -> l.onTeleport(event, game, gp));
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onToggleFlight(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onToggleFlight(event, game, gp));
        gp.getListeners().forEach(l -> l.onToggleFlight(event, game, gp));
    }

    @EventHandler
    public void onToggleSneak(PlayerToggleSneakEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onToggleSneak(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onToggleSneak(event, game, gp));
        gp.getListeners().forEach(l -> l.onToggleSneak(event, game, gp));
    }

    @EventHandler
    public void onToggleSprint(PlayerToggleSprintEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onToggleSprint(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onToggleSprint(event, game, gp));
        gp.getListeners().forEach(l -> l.onToggleSprint(event, game, gp));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.BLOCK_BREAK, Boolean.class))
            event.setCancelled(true);

        game.getListeners().forEach(l -> l.onBlockBreak(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.BLOCK_BREAK))
                if (!team.getProperties().as(TeamProperty.BLOCK_BREAK, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onBlockBreak(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.BLOCK_BREAK))
            if (!gp.getProperties().as(PlayerProperty.BLOCK_BREAK, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onBlockBreak(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.PVP, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.BLOCK_PLACE, Boolean.class))
            event.setCancelled(true);
        game.getListeners().forEach(l -> l.onBlockPlace(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.BLOCK_PLACE))
                if (!team.getProperties().as(TeamProperty.BLOCK_PLACE, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onBlockPlace(event, game, gp));
        }
        if (gp.getProperties().has(PlayerProperty.BLOCK_PLACE))
            if (!gp.getProperties().as(PlayerProperty.BLOCK_PLACE, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onBlockPlace(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.PVP, Boolean.class))
                event.setCancelled(true);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        GamePlayer gp = GameAPI.getPlayer(event.getPlayer());
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        game.getListeners().forEach(l -> l.onSignChange(event, game, gp));
        if (team != null)
            team.getListeners().forEach(l -> l.onSignChange(event, game, gp));
        gp.getListeners().forEach(l -> l.onSignChange(event, game, gp));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        GamePlayer gp = event.getEntity() instanceof Player ? GameAPI.getPlayer((Player) event.getEntity()) : null;
        if (gp == null)
            return;
        Game game = getGame(gp);

        TeamManager teams = game.getManager(TeamManager.class);
        Team team = teams == null ? null : teams.getTeam(gp);

        if (!game.getProperties().as(GameProperty.DAMAGE_RECEIVE, Boolean.class))
            event.setCancelled(true);
        game.getListeners().forEach(l -> l.onPlayerDamage(event, game, gp));

        if (team != null) {
            if (team.getProperties().has(TeamProperty.DAMAGE_RECEIVE))
                if (!team.getProperties().as(TeamProperty.BLOCK_BREAK, Boolean.class))
                    event.setCancelled(true);

            team.getListeners().forEach(l -> l.onPlayerDamage(event, game, gp));
        }

        if (gp.getProperties().has(PlayerProperty.DAMAGE_RECEIVE))
            if (!gp.getProperties().as(PlayerProperty.DAMAGE_RECEIVE, Boolean.class))
                event.setCancelled(true);
        gp.getListeners().forEach(l -> l.onPlayerDamage(event, game, gp));

        if (isLobby(game, gp.getBukkitPlayer().getWorld()))
            if (!game.getLobby().getProperties().as(LobbyProperty.DAMAGE_RECEIVE, Boolean.class))
                event.setCancelled(true);

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        GamePlayer attacker = event.getDamager() instanceof Player ? GameAPI.getPlayer((Player) event.getDamager()) : null;
        GamePlayer defender = event.getEntity() instanceof Player ? GameAPI.getPlayer((Player) event.getEntity()) : null;
        Game attackerGame = attacker == null ? null : attacker.getGame();
        Game defenderGame = defender == null ? null : defender.getGame();
        if (attackerGame != defenderGame && (attackerGame != null || defenderGame != null)) {// ensure same game
            event.setCancelled(true);
            return;
        }
        if (attacker == null && defender == null)
            return;
        TeamManager teams = attackerGame == null ? null : attackerGame.getManager(TeamManager.class);
        Team attackerTeam = teams == null ? null : teams.getTeam(attacker);
        TeamManager teams2 = defenderGame == null ? null : defenderGame.getManager(TeamManager.class);
        Team defenderTeam = teams2 == null ? null : teams2.getTeam(defender);
        if (attacker == null) {//Entity damages Player
            if (defenderGame != null) {
                if (!defenderGame.getProperties().as(GameProperty.DAMAGE_BY_ENTITY, Boolean.class))
                    event.setCancelled(true);
                defenderGame.getListeners().forEach(l -> l.onPlayerDamageByEntity(event, defenderGame, event.getDamager(), defender));
            }
            if (defenderTeam != null) {
                if (defenderTeam.getProperties().has(TeamProperty.DAMAGE_BY_ENTITY))
                    if (!defenderTeam.getProperties().as(TeamProperty.DAMAGE_BY_ENTITY, Boolean.class))
                        event.setCancelled(true);
                defenderTeam.getListeners().forEach(l -> l.onPlayerDamageByEntity(event, defenderGame, event.getDamager(), defender));
            }
            if (defender.getProperties().has(PlayerProperty.DAMAGE_BY_ENTITY))
                if (!defender.getProperties().as(PlayerProperty.DAMAGE_BY_ENTITY, Boolean.class))
                    event.setCancelled(true);
            defender.getListeners().forEach(l -> l.onPlayerDamageByEntity(event, defenderGame, event.getDamager(), defender));

            if (isLobby(defenderGame, defender.getBukkitPlayer().getWorld()))
                if (!defenderGame.getLobby().getProperties().as(LobbyProperty.DAMAGE_BY_ENTITY, Boolean.class))
                    event.setCancelled(true);
        } else if (defender == null) {//player damages Entity
            if (attackerGame != null) {
                if (!attackerGame.getProperties().as(GameProperty.DAMAGE_ENTITY, Boolean.class))
                    event.setCancelled(true);
                attackerGame.getListeners().forEach(l -> l.onEntityDamageByPlayer(event, attackerGame, attacker, event.getEntity()));
            }
            if (attackerTeam != null) {
                if (attackerTeam.getProperties().has(TeamProperty.DAMAGE_ENTITY))
                    if (!attackerTeam.getProperties().as(TeamProperty.DAMAGE_ENTITY, Boolean.class))
                        event.setCancelled(true);
                attackerTeam.getListeners().forEach(l -> l.onEntityDamageByPlayer(event, attackerGame, attacker, event.getEntity()));
            }
            if (attacker.getProperties().has(PlayerProperty.DAMAGE_ENTITY))
                if (!attacker.getProperties().as(PlayerProperty.DAMAGE_ENTITY, Boolean.class))
                    event.setCancelled(true);
            attacker.getListeners().forEach(l -> l.onEntityDamageByPlayer(event, attackerGame, attacker, event.getEntity()));

            if (isLobby(attackerGame, attacker.getBukkitPlayer().getWorld()))
                if (!attackerGame.getLobby().getProperties().as(LobbyProperty.DAMAGE_ENTITY, Boolean.class))
                    event.setCancelled(true);
        } else {//player damages Player
            if (attackerGame != null) {
                if (!attackerGame.getProperties().as(GameProperty.PVP, Boolean.class))
                    event.setCancelled(true);
                attackerGame.getListeners().forEach(l -> l.onPlayerDamageByPlayer(event, attackerGame, attacker, defender));
            }
            if (attackerTeam != null) {
                if (attackerTeam.getProperties().has(TeamProperty.PVP))
                    if (!attackerTeam.getProperties().as(TeamProperty.PVP, Boolean.class))
                        event.setCancelled(true);
                if (attackerTeam == defenderTeam && attackerTeam.getProperties().has(TeamProperty.FRIENDLY_FIRE))
                    if (!attackerTeam.getProperties().as(TeamProperty.FRIENDLY_FIRE, Boolean.class))
                        event.setCancelled(true);
                attackerTeam.getListeners().forEach(l -> l.onPlayerDamageByPlayer(event, attackerGame, attacker, defender));
            }

            if (attacker.getProperties().has(PlayerProperty.PVP))
                if (!defender.getProperties().as(PlayerProperty.PVP, Boolean.class))
                    event.setCancelled(true);
            attacker.getListeners().forEach(l -> l.onPlayerDamageByPlayer(event, attackerGame, attacker, defender));

            if (isLobby(attackerGame, attacker.getBukkitPlayer().getWorld()))
                if (!attackerGame.getLobby().getProperties().as(LobbyProperty.PVP, Boolean.class))
                    event.setCancelled(true);
        }
    }

    private boolean isLobby(Game game, World world) {
        if (game.getLobby() == null)
            return false;
        if (game.getLobby().getWorld().equals(world))
            return true;
        return false;
    }

}
