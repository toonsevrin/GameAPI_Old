package com.exorath.game.api.hud;

import java.util.*;

import com.exorath.game.api.hud.effects.HUDEffect;
import com.exorath.game.lib.JoinLeave;
import org.bukkit.Bukkit;
import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.exorath.game.api.hud.locations.ActionBar;
import com.exorath.game.api.hud.locations.BossBar;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.hud.locations.Title;
import com.exorath.game.api.hud.locations.scoreboard.Scoreboard;
import com.exorath.game.api.hud.locations.scoreboard.ScoreboardText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.lib.hud.bossbar.BossBarAPI;

/**
 * Created by Toon Sevrin on 8/11/2015.
 */
public class HUDManager implements Manager, JoinLeave {

    private Game game;
    private BossBarAPI bossBarAPI = new BossBarAPI();
    private PublicHUD publicHUD;

    public HUDManager(Game game) {
        this.game = game;
        Bukkit.getServer().getPluginManager().registerEvents(bossBarAPI, GameAPI.getInstance());
        publicHUD = new PublicHUD();
    }

    //** Join & Leave **//

    @Override
    public void join(GamePlayer player) {
        publicHUD.join(player);
    }

    @Override
    public void leave(GamePlayer player) {
        publicHUD.leave(player);
    }

    //** Getters **//
    public PublicHUD getPublicHUD() {
        return publicHUD;
    }

    //** Public HUD **//
    public class PublicHUD implements JoinLeave {

        private String sbTitle = null;
        private HUDEffect sbEffect = null;
        private HashMap<Class<? extends HUDLocation>, HashMap<String, HUDText>> keys = new HashMap<>();
        private HashMap<Class<? extends HUDLocation>, List<String>> removeOnChange = new HashMap<>();

        public PublicHUD() {
            for (Class<? extends HUDLocation> loc : HUDLocation.LOCATIONS) {
                keys.put(loc, new HashMap<>());
                removeOnChange.put(loc, new ArrayList<>());
            }
        }

        //** Join & Leave **//
        //TODO: The bug is here
        @Override
        public void join(GamePlayer player) {
            keys.get(ActionBar.class).forEach((key, text) -> player.getHud().getActionBar().addText(key, text.clone()));
            keys.get(Title.class).forEach((key, text) -> player.getHud().getTitle().addText(key, text.clone()));
            keys.get(Subtitle.class).forEach((key, text) -> player.getHud().getSubtitle().addText(key, text.clone()));
            keys.get(BossBar.class).forEach((key, text) -> player.getHud().getBossBar().addText(key, text.clone()));
            keys.get(Scoreboard.class).forEach((key, text) -> player.getHud().getScoreboard().addText(key, ((ScoreboardText) text).cloneSB()));
            if (sbTitle != null)
                player.getHud().getScoreboard().getTitle().setText(sbTitle);
            if (sbEffect != null)
                player.getHud().getScoreboard().getTitle().setEffect(sbEffect);
        }

        @Override
        public void leave(GamePlayer player) {

        }

        public void onStateChange() {
            for (Class<? extends HUDLocation> loc : removeOnChange.keySet()) {
                if (loc.isAssignableFrom(ActionBar.class))
                    removeOnChange.get(ActionBar.class).forEach(key -> removeActionBar(key));
                else if (loc.isAssignableFrom(Title.class))
                    removeOnChange.get(Title.class).forEach(key -> removeTitle(key));
                else if (loc.isAssignableFrom(Subtitle.class))
                    removeOnChange.get(Subtitle.class).forEach(key -> removeSubtitle(key));
                else if (loc.isAssignableFrom(BossBar.class))
                    removeOnChange.get(BossBar.class).forEach(key -> removeBossBar(key));
                else if (loc.isAssignableFrom(Scoreboard.class))
                    removeOnChange.get(Scoreboard.class).forEach(key -> removeScoreboard(key));
            }
            removeOnChange.values().forEach(list -> list.clear());
        }

        /* Add texts */
        public void addActionBar(String key, HUDText text) {
            addActionBar(key, text, false);
        }

        public void addActionBar(String key, HUDText text, boolean removeOnStateChange) {
            if (keys.get(ActionBar.class).containsKey(key))
                return;
            if (removeOnStateChange)
                removeOnChange.get(ActionBar.class).add(key);
            keys.get(ActionBar.class).put(key, text);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getActionBar().addText(key, text.clone()));
        }

        public void addTitle(String key, HUDText text) {
            addTitle(key, text, false);
        }

        public void addTitle(String key, HUDText text, boolean removeOnStateChange) {
            if (keys.get(Title.class).containsKey(key))
                return;
            if (removeOnStateChange)
                removeOnChange.get(Title.class).add(key);
            keys.get(Title.class).put(key, text);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getTitle().addText(key, text.clone()));
        }

        public void addSubtitle(String key, HUDText text) {
            addSubtitle(key, text, false);
        }

        public void addSubtitle(String key, HUDText text, boolean removeOnStateChange) {
            if (keys.get(Subtitle.class).containsKey(key))
                return;
            if (removeOnStateChange)
                removeOnChange.get(Subtitle.class).add(key);
            keys.get(Subtitle.class).put(key, text);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getSubtitle().addText(key, text.clone()));
        }

        public void addBossBar(String key, HUDText text) {
            addBossBar(key, text, false);
        }

        public void addBossBar(String key, HUDText text, boolean removeOnStateChange) {
            if (keys.get(BossBar.class).containsKey(key))
                return;
            if (removeOnStateChange)
                removeOnChange.get(BossBar.class).add(key);
            keys.get(BossBar.class).put(key, text);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getBossBar().addText(key, text.clone()));
        }

        public void addScoreboard(String key, ScoreboardText text) {
            addScoreboard(key, text, false);
        }

        public void addScoreboard(String key, ScoreboardText text, boolean removeOnStateChange) {
            if (keys.get(Scoreboard.class).containsKey(key))
                return;
            if (removeOnStateChange)
                removeOnChange.get(Scoreboard.class).add(key);
            keys.get(Scoreboard.class).put(key, text);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getScoreboard().addText(key, text.cloneSB()));
        }

        public void setScoreboardTitle(String sbTitle) {
            this.sbTitle = sbTitle;
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getScoreboard().getTitle().setText(sbTitle));
        }

        public void setScoreboardEffect(HUDEffect sbEffect) {
            this.sbEffect = sbEffect;
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getScoreboard().getTitle().setEffect(sbEffect));
        }

        /* Remove texts */
        public void removeActionBar(String key) {
            if (!keys.get(ActionBar.class).containsKey(key))
                return;
            keys.get(ActionBar.class).remove(key);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getActionBar().removeText(key));
        }

        public void removeTitle(String key) {
            if (!keys.get(Title.class).containsKey(key))
                return;
            keys.get(Title.class).remove(key);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getTitle().removeText(key));
        }

        public void removeSubtitle(String key) {
            if (!keys.get(Subtitle.class).containsKey(key))
                return;
            keys.get(Subtitle.class).remove(key);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getSubtitle().removeText(key));
        }

        public void removeBossBar(String key) {
            if (!keys.get(BossBar.class).containsKey(key))
                return;
            keys.get(BossBar.class).remove(key);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getBossBar().removeText(key));
        }

        public void removeScoreboard(String key) {
            if (!keys.get(Scoreboard.class).containsKey(key))
                return;
            keys.get(Scoreboard.class).remove(key);
            game.getManager(PlayerManager.class).getPlayers().forEach(gp -> gp.getHud().getScoreboard().removeText(key));
        }

        /* Get texts */
        public boolean containsActionBar(String key) {
            return keys.get(ActionBar.class).containsKey(key);
        }

        public boolean containsTitle(String key) {
            return keys.get(Title.class).containsKey(key);
        }

        public boolean containsSubtitle(String key) {
            return keys.get(Subtitle.class).containsKey(key);
        }

        public boolean containsBossBar(String key) {
            return keys.get(BossBar.class).containsKey(key);
        }

        public boolean containsScoreboard(String key) {
            return keys.get(Scoreboard.class).containsKey(key);
        }

        /* Update text */
        public void updateActionBar(String key, String text) {
            game.getManager(PlayerManager.class).getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null)
                    .filter(gp -> gp.getHud().getActionBar().containsText(key))
                    .forEach(gp -> gp.getHud().getActionBar().getText(key).setText(text));
        }

        public void updateTitle(String key, String text) {
            game.getManager(PlayerManager.class).getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null)
                    .filter(gp -> gp.getHud().getTitle().containsText(key))
                    .forEach(gp -> gp.getHud().getTitle().getText(key).setText(text));
        }

        public void updateSubtitle(String key, String text) {
            game.getManager(PlayerManager.class).getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null)
                    .filter(gp -> gp.getHud().getSubtitle().containsText(key))
                    .forEach(gp -> gp.getHud().getSubtitle().getText(key).setText(text));
        }

        public void updateBossBar(String key, String text) {
            game.getManager(PlayerManager.class).getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null)
                    .filter(gp -> gp.getHud().getBossBar().containsText(key))
                    .forEach(gp -> gp.getHud().getBossBar().getText(key).setText(text));
        }

        public void updateScoreboard(String key, String text) {
            game.getManager(PlayerManager.class).getPlayers().stream().filter(gp -> gp.getBukkitPlayer() != null)
                    .filter(gp -> gp.getHud().getScoreboard().containsText(key))
                    .forEach(gp -> gp.getHud().getScoreboard().getText(key).setText(text));
        }

    }
}
