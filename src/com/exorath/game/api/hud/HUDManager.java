package com.exorath.game.api.hud;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.hud.locations.ActionBar;
import com.exorath.game.api.hud.locations.BossBar;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.hud.locations.Title;
import com.exorath.game.api.hud.locations.scoreboard.Scoreboard;
import com.exorath.game.api.hud.locations.scoreboard.ScoreboardText;
import com.exorath.game.api.player.GamePlayer;
import org.bukkit.Bukkit;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Manager;
import com.exorath.game.lib.hud.bossbar.BossBarAPI;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Toon Sevrin on 8/11/2015.
 */
public class HUDManager implements Manager {
    private Game game;
    private BossBarAPI bossBarAPI = new BossBarAPI();
    private PublicHUD publicHUD;

    public HUDManager(Game game) {
        this.game = game;
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());
        publicHUD = new PublicHUD();
    }
    public PublicHUD getPublicHUD(){
        return publicHUD;
    }

    public class PublicHUD implements GameListener {
        private HashMap<Class<HUDLocation>, HashMap<String, HUDText>> keys = new HashMap<>();

        public PublicHUD(){
            for(Class<HUDLocation> loc : HUDLocation.LOCATIONS)
                keys.put(loc, new HashMap<>());
            game.addListener(this);
        }

        @Override
        public void onJoin(PlayerJoinEvent event, Game game, GamePlayer player) {
            if(HUDManager.this.game != game)
                return;
            keys.get(ActionBar.class).entrySet().forEach(e -> player.getHud().getActionBar().addText(e.getKey(), e.getValue().clone()));
            keys.get(Title.class).entrySet().forEach(e -> player.getHud().getTitle().addText(e.getKey(), e.getValue().clone()));
            keys.get(Subtitle.class).entrySet().forEach(e -> player.getHud().getSubtitle().addText(e.getKey(), e.getValue().clone()));
            keys.get(BossBar.class).entrySet().forEach(e -> player.getHud().getBossBar().addText(e.getKey(), e.getValue().clone()));
            keys.get(Scoreboard.class).entrySet().forEach(e -> player.getHud().getScoreboard().addText(e.getKey(), (ScoreboardText) e.getValue().clone()));
        }

        /* Add texts */
        public void addActionBar(String key, HUDText text){
            if(keys.get(ActionBar.class).containsKey(key)) return;
            keys.get(ActionBar.class).put(key, text);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getActionBar().addText(key, text.clone()));
        }
        public void addTitle(String key, HUDText text){
            if(keys.get(Title.class).containsKey(key)) return;
            keys.get(Title.class).put(key, text);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getTitle().addText(key, text.clone()));
        }
        public void addSubtitle(String key, HUDText text){
            if(keys.get(Subtitle.class).containsKey(key)) return;
            keys.get(Subtitle.class).put(key, text);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getSubtitle().addText(key, text.clone()));
        }
        public void addBossBar(String key, HUDText text){
            if(keys.get(BossBar.class).containsKey(key)) return;
            keys.get(BossBar.class).put(key, text);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getBossBar().addText(key, text.clone()));
        }
        public void addScoreboard(String key, ScoreboardText text){
            if(keys.get(Scoreboard.class).containsKey(key)) return;
            keys.get(Scoreboard.class).put(key, text);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getScoreboard().addText(key, text.cloneSB()));
        }
        /* Remove texts */
        public void removeActionBar(String key){
            if(!keys.get(ActionBar.class).containsKey(key)) return;
            keys.get(ActionBar.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getActionBar().removeText(key));
        }
        public void removeTitle(String key){
            if(!keys.get(Title.class).containsKey(key)) return;
            keys.get(Title.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getTitle().removeText(key));
        }
        public void removeSubtitle(String key){
            if(!keys.get(Subtitle.class).containsKey(key)) return;
            keys.get(Subtitle.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getSubtitle().removeText(key));
        }
        public void removeBossBar(String key){
            if(!keys.get(BossBar.class).containsKey(key)) return;
            keys.get(BossBar.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getBossBar().removeText(key));
        }
        public void removeScoreboard(String key){
            if(!keys.get(Scoreboard.class).containsKey(key)) return;
            keys.get(Scoreboard.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getScoreboard().removeText(key));
        }
        /* Get texts */
        public boolean containsActionBar(String key){
           return keys.get(ActionBar.class).containsKey(key);
        }
        public boolean containsTitle(String key){
            return keys.get(Title.class).containsKey(key);
        }
        public boolean containsSubtitle(String key){
            return keys.get(Subtitle.class).containsKey(key);
        }
        public boolean containsBossBar(String key){
            return keys.get(BossBar.class).containsKey(key);
        }
        public boolean containsScoreboard(String key){
            return keys.get(Scoreboard.class).containsKey(key);
        }
        /* Update text */
        public void updateActionBar(String key, String text){
            game.getPlayers().getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null).forEach(gp -> gp.getHud().getActionBar().getText(key).setText(text));
        }
        public void updateTitle(String key, String text){
            game.getPlayers().getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null).forEach(gp -> gp.getHud().getTitle().getText(key).setText(text));
        }
        public void updateSubtitle(String key, String text) {
            game.getPlayers().getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null).forEach(gp -> gp.getHud().getSubtitle().getText(key).setText(text));
        }
        public void updateBossBar(String key, String text){
            game.getPlayers().getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null).forEach(gp -> gp.getHud().getBossBar().getText(key).setText(text));
        }
        public void updateScoreboard(String key, String text){
            game.getPlayers().getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null).forEach(gp -> gp.getHud().getScoreboard().getText(key).setText(text));
        }

    }
}
