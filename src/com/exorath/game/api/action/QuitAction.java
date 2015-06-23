package com.exorath.game.api.action;

import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class QuitAction {
    
    public abstract void onQuit( PlayerQuitEvent event, GamePlayer player, Game game );
    
    public abstract void onQuit( PlayerKickEvent event, GamePlayer player, Game game );
    
}
