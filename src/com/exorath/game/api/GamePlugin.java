package com.exorath.game.api;

import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.GameAPI;

/**
 * Created by TOON on 8/23/2015.
 */
public abstract class GamePlugin extends JavaPlugin{
    public GamePlugin(Game game){
        game.setHost(this);
        GameAPI.getInstance().setGame(game);
    }
}
