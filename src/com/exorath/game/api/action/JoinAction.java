package com.exorath.game.api.action;

import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class JoinAction {
    
    public abstract void onJoin( PlayerJoinEvent event, GamePlayer player, Game game );
    
}
