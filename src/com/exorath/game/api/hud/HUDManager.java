package com.exorath.game.api.hud;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.hud.bossBar.BossBarAPI;
import org.bukkit.Bukkit;

/**
 * Created by TOON on 8/11/2015.
 */
public class HUDManager {
    private BossBarAPI bossBarAPI;
    public HUDManager(){
        bossBarAPI = new BossBarAPI();
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());

    }
}
