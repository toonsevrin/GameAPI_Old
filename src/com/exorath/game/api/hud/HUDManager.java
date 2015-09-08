package com.exorath.game.api.hud;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Manager;
import com.exorath.game.lib.hud.bossbar.BossBarAPI;

import org.bukkit.Bukkit;

/**
 * Created by Toon Sevrin on 8/11/2015.
 */
public class HUDManager implements Manager {

    public static HUDManager instance;
    private BossBarAPI bossBarAPI;

    public HUDManager() {
        instance = this;

        bossBarAPI = new BossBarAPI();
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());
    }
}
