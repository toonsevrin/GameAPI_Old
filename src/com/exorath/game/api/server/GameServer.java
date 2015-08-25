package com.exorath.game.api.server;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Sets;

public class GameServer {
    //TODO: Ask ourselves if we need this class!
    public static Set<GamePlayer> getOnlinePlayers() {
        Set<GamePlayer> s = Sets.newHashSet();
        for (Player p : Bukkit.getOnlinePlayers())
            s.add(GamePlayer.fromUUID(p.getUniqueId()));
        return s;
    }

}
