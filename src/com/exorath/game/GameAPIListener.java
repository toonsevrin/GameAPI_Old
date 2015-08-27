package com.exorath.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
import com.exorath.game.api.GameProvider;
import com.exorath.game.api.events.GamePlayerKillPlayerEvent;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;

/**
 * @author Nick Robson
 */
public class GameAPIListener implements Listener {

    @EventHandler
    public void onPluginLoad( PluginEnableEvent event ) {
        Plugin plg = event.getPlugin();
        if ( plg instanceof GameProvider ) {
            GameAPI.registerGameProvider( (GameProvider) plg );
            GameAPI.getInstance().getLogger().info( "Detected GameAPI provider: " + plg.getName() );
        }
    }

    @EventHandler
    public void onLogin( PlayerLoginEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onLogin( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onLogin( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onLogin( event, game, gp ) );
    }

    @EventHandler
    public void onJoin( PlayerJoinEvent event ) {
        GameAPI.refreshOnlinePlayers();

        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );

        Game g = GameAPI.getGame();
        if ( g != null ) {
            gp.join( g );
        }

        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onJoin( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onJoin( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onJoin( event, game, gp ) );
    }

    @EventHandler
    public void onQuit( PlayerQuitEvent event ) {
        GameAPI.refreshOnlinePlayers();

        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onQuit( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onQuit( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onQuit( event, game, gp ) );
    }

    @EventHandler
    public void onKick( PlayerKickEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onKick( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onKick( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onKick( event, game, gp ) );
    }

    @EventHandler
    public void onChat( AsyncPlayerChatEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onChat( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onChat( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onChat( event, game, gp ) );
    }

    @EventHandler
    public void onDeath( PlayerDeathEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getEntity() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onDeath( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onDeath( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onDeath( event, game, gp ) );

        if ( event.getEntity().getKiller() != null && game != null && gp != null ) {
            GamePlayer killer = GameAPI.getPlayer( event.getEntity().getKiller() );
            if ( killer != null ) {
                GamePlayerKillPlayerEvent gpke = new GamePlayerKillPlayerEvent( game, gp, killer );
                game.getListeners().forEach( l -> l.onPlayerKillPlayer( gpke ) );
                if ( team != null )
                    team.getListeners().forEach( l -> l.onPlayerKillPlayer( gpke ) );
                gp.getListeners().forEach( l -> l.onPlayerKillPlayer( gpke ) );
            }
        }
    }

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onRespawn( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onRespawn( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onRespawn( event, game, gp ) );
    }

    @EventHandler
    public void onInteract( PlayerInteractEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onInteract( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onInteract( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onInteract( event, game, gp ) );
    }

    @EventHandler
    public void onDropItem( PlayerDropItemEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onDropItem( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onDropItem( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onDropItem( event, game, gp ) );
    }

    @EventHandler
    public void onPickupItem( PlayerPickupItemEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onPickupItem( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onPickupItem( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onPickupItem( event, game, gp ) );
    }

    @EventHandler
    public void onFish( PlayerFishEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onFish( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onFish( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onFish( event, game, gp ) );
    }

    @EventHandler
    public void onExpChange( PlayerExpChangeEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onExpChange( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onExpChange( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onExpChange( event, game, gp ) );
    }

    @EventHandler
    public void onGamemodeChange( PlayerGameModeChangeEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onGamemodeChange( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onGamemodeChange( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onGamemodeChange( event, game, gp ) );
    }

    @EventHandler
    public void onInteractEntity( PlayerInteractEntityEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onInteractEntity( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onInteractEntity( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onInteractEntity( event, game, gp ) );
    }

    @EventHandler
    public void onInteractAtEntity( PlayerInteractAtEntityEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onInteractAtEntity( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onInteractAtEntity( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onInteractAtEntity( event, game, gp ) );
    }

    @EventHandler
    public void onWorldChange( PlayerChangedWorldEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onWorldChange( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onWorldChange( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onWorldChange( event, game, gp ) );
    }

    @EventHandler
    public void onLevelChange( PlayerLevelChangeEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onLevelChange( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onLevelChange( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onLevelChange( event, game, gp ) );
    }

    @EventHandler
    public void onMove( PlayerMoveEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onMove( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onMove( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onMove( event, game, gp ) );
    }

    @EventHandler
    public void onVelocity( PlayerVelocityEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onVelocity( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onVelocity( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onVelocity( event, game, gp ) );
    }

    @EventHandler
    public void onPortal( PlayerPortalEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onPortal( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onPortal( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onPortal( event, game, gp ) );
    }

    @EventHandler
    public void onShear( PlayerShearEntityEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onShear( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onShear( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onShear( event, game, gp ) );
    }

    @EventHandler
    public void onTeleport( PlayerTeleportEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onTeleport( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onTeleport( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onTeleport( event, game, gp ) );
    }

    @EventHandler
    public void onToggleFlight( PlayerToggleFlightEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onToggleFlight( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onToggleFlight( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onToggleFlight( event, game, gp ) );
    }

    @EventHandler
    public void onToggleSneak( PlayerToggleSneakEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onToggleSneak( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onToggleSneak( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onToggleSneak( event, game, gp ) );
    }

    @EventHandler
    public void onToggleSprint( PlayerToggleSprintEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onToggleSprint( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onToggleSprint( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onToggleSprint( event, game, gp ) );
    }

    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onBlockBreak( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onBlockBreak( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onBlockBreak( event, game, gp ) );
    }

    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onBlockPlace( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onBlockPlace( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onBlockPlace( event, game, gp ) );
    }

    @EventHandler
    public void onSignChange( SignChangeEvent event ) {
        GamePlayer gp = GameAPI.getPlayer( event.getPlayer() );
        Game game = gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onSignChange( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onSignChange( event, game, gp ) );
        gp.getListeners().forEach( l -> l.onSignChange( event, game, gp ) );
    }

    @EventHandler
    public void onEntityDamage( EntityDamageEvent event ) {
        GamePlayer gp = event.getEntity() instanceof Player ? GameAPI.getPlayer( (Player) event.getEntity() ) : null;
        if ( gp == null )
            return;
        Game game = gp == null ? null : gp.getGame();

        TeamManager teams = game == null ? null : game.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( gp );

        if ( game != null )
            game.getListeners().forEach( l -> l.onEntityDamage( event, game, gp ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onEntityDamage( event, game, gp ) );
        if ( gp != null )
            gp.getListeners().forEach( l -> l.onEntityDamage( event, game, gp ) );
    }

    @EventHandler
    public void onEntityDamageByEntity( EntityDamageByEntityEvent event ) {
        GamePlayer dam = event.getDamager() instanceof Player ? GameAPI.getPlayer( (Player) event.getDamager() ) : null;
        GamePlayer ent = event.getEntity() instanceof Player ? GameAPI.getPlayer( (Player) event.getEntity() ) : null;
        Game damgame = dam == null ? null : dam.getGame();
        Game entgame = ent == null ? null : ent.getGame();

        // ensure same game
        if ( damgame != entgame && damgame != null ) {
            event.setCancelled( true );
            return;
        }

        TeamManager teams = damgame == null ? null : damgame.getManager( TeamManager.class );
        Team team = teams == null ? null : teams.getTeam( dam );

        if ( damgame != null )
            damgame.getListeners().forEach( l -> l.onEntityDamageByEntity( event, damgame, dam, ent ) );
        if ( team != null )
            team.getListeners().forEach( l -> l.onEntityDamageByEntity( event, damgame, dam, ent ) );
        dam.getListeners().forEach( l -> l.onEntityDamageByEntity( event, damgame, dam, ent ) );
        ent.getListeners().forEach( l -> l.onEntityDamageByEntity( event, damgame, dam, ent ) );
    }

}
