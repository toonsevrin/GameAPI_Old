package com.exorath.game.api.hud;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.hud.bossBar.BossBarAPI;
import org.bukkit.Bukkit;

/**
 * Created by TOON on 8/11/2015.
 */
public class HUDManager {
    public static final HUDManager INSTANCE = new HUDManager();//Lol this will crash the plugin and I know it. - Toon

    private BossBarAPI bossBarAPI;
    public HUDManager(){
        bossBarAPI = new BossBarAPI();
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());

    }
}
