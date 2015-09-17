package com.exorath.game.lib;

import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/17/2015.
 */
public interface JoinLeave {
    /**
     * Should be ran when the player joins a game. Bukkit player loaded.
     * @param player GamePlayer that joined a game.
     */
    void join(GamePlayer player);

    /**
     * Should be ran when a player leaves a game.
     * @param player GamePlayer that left a game.
     */
    void leave(GamePlayer player);
}
