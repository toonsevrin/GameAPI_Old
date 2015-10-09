package com.exorath.game.api.hud;

import com.exorath.game.api.hud.locations.ActionBar;
import com.exorath.game.api.hud.locations.BossBar;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.hud.locations.Title;
import com.exorath.game.api.hud.locations.scoreboard.Scoreboard;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 8/27/2015.
 */
public class HUD {

    private GamePlayer player;

    private ActionBar actionBar;
    private BossBar bossBar;
    private Subtitle subtitle;
    private Title title;
    private Scoreboard scoreboard;

    public HUD(GamePlayer player) {
        this.player = player;
        this.actionBar = new ActionBar(player);
        this.bossBar = new BossBar(player);
        this.subtitle = new Subtitle(player);
        this.title = new Title(player);
        this.scoreboard = new Scoreboard(player);
    }

    public ActionBar getActionBar() {
        return actionBar;
    }

    public Title getTitle() {
        return title;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public GamePlayer getPlayer() {
        return player;
    }

}
