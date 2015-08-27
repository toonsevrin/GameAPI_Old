package com.exorath.game.api;

import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;

/**
 * Created by Toon Sevrin on 8/23/2015.
 * Runnable that will not run if the game that was currently running has stopped
 */
public abstract class GameRunnable extends BukkitRunnable {

    private UUID gameUID;

    public GameRunnable( Game game ) {
        this.gameUID = game.getGameID();
    }

    @Override
    public void run() {
        Game g = GameAPI.getGame( gameUID );
        if ( g != null && g.getState().is( GameState.WAITING, GameState.STARTING, GameState.INGAME, GameState.FINISHING ) )
            _run();
    }

    public abstract void _run();
}
