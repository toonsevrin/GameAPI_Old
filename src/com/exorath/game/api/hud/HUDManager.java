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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Toon Sevrin on 8/11/2015.
 */
public class HUDManager implements Manager {

    private BossBarAPI bossBarAPI = new BossBarAPI();
    private HashMap<Class<HUDLocation>, Set<String>> keys = new HashMap<>();

    public HUDManager() {
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());
        for(Class<HUDLocation> loc : HUDLocation.LOCATIONS)
            keys.put(loc, new HashSet<>());
    }

    private class PublicHUD implements GameListener {
        @Override
        public void onJoin(PlayerJoinEvent event, Game game, GamePlayer player) {
            // TODO: onJoin add hudTexts to player hud
        }

        /* Add texts */
        public void addActionBarText(Game game, String key, HUDText text){
            if(keys.get(ActionBar.class).contains(key)) return;
            keys.get(ActionBar.class).add(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getActionBar().addText(key, text.clone()));
        }
        public void addTitle(Game game, String key, HUDText text){
            if(keys.get(Title.class).contains(key)) return;
            keys.get(Title.class).add(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getTitle().addText(key, text.clone()));
        }
        public void addSubtitle(Game game, String key, HUDText text){
            if(keys.get(Subtitle.class).contains(key)) return;
            keys.get(Subtitle.class).add(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getSubtitle().addText(key, text.clone()));
        }
        public void addBossBar(Game game, String key, HUDText text){
            if(keys.get(BossBar.class).contains(key)) return;
            keys.get(BossBar.class).add(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getBossBar().addText(key, text.clone()));
        }
        public void addScoreboard(Game game, String key, ScoreboardText text){
            if(keys.get(Scoreboard.class).contains(key)) return;
            keys.get(Scoreboard.class).add(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getScoreboard().addText(key, text.cloneSB()));
        }
        /* Remove texts */
        public void removeActionBar(Game game, String key){
            if(!keys.get(ActionBar.class).contains(key)) return;
            keys.get(ActionBar.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getActionBar().removeText(key));
        }
        public void removeTitle(Game game, String key){
            if(!keys.get(Title.class).contains(key)) return;
            keys.get(Title.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getTitle().removeText(key));
        }
        public void removeSubtitle(Game game, String key){
            if(!keys.get(Subtitle.class).contains(key)) return;
            keys.get(Subtitle.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getSubtitle().removeText(key));
        }
        public void removeBossBar(Game game, String key){
            if(!keys.get(BossBar.class).contains(key)) return;
            keys.get(BossBar.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getBossBar().removeText(key));
        }
        public void removeScoreboard(Game game, String key){
            if(!keys.get(Scoreboard.class).contains(key)) return;
            keys.get(Scoreboard.class).remove(key);
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getScoreboard().removeText(key));
        }
        /* Get texts */
        public Collection<HUDText> getActionbarTexts(Game game, String key){
            HashSet<HUDText> texts = new HashSet<>();
            game.getPlayers().getPlayers().forEach(gp -> texts.add(gp.getHud().getActionBar().getText(key)));
            return texts;
        }
        public Collection<HUDText> getTitle(Game game, String key){
            HashSet<HUDText> texts = new HashSet<>();
            game.getPlayers().getPlayers().forEach(gp -> texts.add(gp.getHud().getTitle().getText(key)));
            return texts;
        }
        public Collection<HUDText> getSubtitle(Game game, String key){
            HashSet<HUDText> texts = new HashSet<>();
            game.getPlayers().getPlayers().forEach(gp -> texts.add(gp.getHud().getSubtitle().getText(key)));
            return texts;
        }
        public Collection<HUDText> getBossBar(Game game, String key){
            HashSet<HUDText> texts = new HashSet<>();
            game.getPlayers().getPlayers().forEach(gp -> texts.add(gp.getHud().getBossBar().getText(key)));
            return texts;
        }
        public Collection<HUDText> getScoreboard(Game game, String key){
            HashSet<HUDText> texts = new HashSet<>();
            game.getPlayers().getPlayers().forEach(gp -> texts.add(gp.getHud().getScoreboard().getText(key)));
            return texts;
        }
        /* Update text */
        public void updateActionBar(Game game, String key, String text){
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getActionBar().getText(key).setText(text));
        }
        public void updateTitle(Game game, String key, String text){
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getTitle().getText(key).setText(text));
        }
        public void updateSubtitle(Game game, String key, String text){
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getSubtitle().getText(key).setText(text));
        }
        public void updateBossBar(Game game, String key, String text){
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getBossBar().getText(key).setText(text));
        }
        public void updateScoreboard(Game game, String key, String text){
            game.getPlayers().getPlayers().forEach(gp -> gp.getHud().getScoreboard().getText(key).setText(text));
        }




    }
}
