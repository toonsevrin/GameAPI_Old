package com.exorath.game.api.hud;

import org.bukkit.Bukkit;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Manager;
import com.exorath.game.lib.hud.bossbar.BossBarAPI;

/**
 * Created by Toon Sevrin on 8/11/2015.
 */
public class HUDManager implements Manager {

    private BossBarAPI bossBarAPI = new BossBarAPI();

    public HUDManager() {
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());
    }

}
