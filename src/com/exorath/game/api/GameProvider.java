package com.exorath.game.api;

import com.exorath.game.GameAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by TOON on 8/23/2015.
 */
public abstract class GameProvider extends JavaPlugin {
    @Override
    public void onEnable(){
        Game game = create();
        if(game == null){
            throw new NullPointerException("Game cannot be null in gameprovider");
        }
        System.out.println("Game created with name " + game.getName());
        System.out.println("Description: " + game.getDescription());
        GameAPI.registerGameProvider(this);

    }


    public abstract Game create();

}
