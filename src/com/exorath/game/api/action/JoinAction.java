package com.exorath.game.api.action;

import com.exorath.game.GameAPI;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.player.PlayerJoinEvent;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameState;
import com.exorath.game.api.player.GamePlayer;

/**
 * This action adds base behaviour to the player join event
 * Implemented!
 *
 * @author Nick Robson
 * @author Toon Sevrin
 */
public abstract class JoinAction {

    public abstract void onJoin(PlayerJoinEvent event, GamePlayer player, Game game);


}
