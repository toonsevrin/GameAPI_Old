package com.exorath.game.api;

import com.exorath.game.GameAPI;
import com.exorath.game.api.gamestates.StateManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by TOON on 8/23/2015.
 */
public abstract class GamePlugin extends JavaPlugin{
    public GamePlugin(Game game){
        game.setHost(this);
        GameAPI.getInstance().setGame(game);
    }
}
