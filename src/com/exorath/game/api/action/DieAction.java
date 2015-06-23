package com.exorath.game.api.action;

import org.bukkit.event.entity.PlayerDeathEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class DieAction {
    
    public abstract void onDie( PlayerDeathEvent event, GamePlayer player, Game game );
    
}
