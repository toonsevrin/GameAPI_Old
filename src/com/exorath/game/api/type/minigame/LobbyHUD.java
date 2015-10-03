package com.exorath.game.api.type.minigame;

import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.locations.scoreboard.ScoreboardText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.lib.JoinLeave;
import org.bukkit.ChatColor;

/**
 * Created by TOON on 10/3/2015.
 */
public class LobbyHUD implements JoinLeave{
    private static final String PREFIX = "exorath.lobby.key";
    private static final String[] KEYS = {PREFIX + 1, PREFIX + 2, PREFIX + 3, PREFIX + 4, PREFIX + 5, PREFIX + 6, PREFIX + 7, PREFIX + 8, PREFIX + 9, PREFIX +10, PREFIX + 11, PREFIX + 12};

    private Minigame game;
    private HUDManager.PublicHUD publicHUD;
    public LobbyHUD(Minigame game){
        this.game = game;
        publicHUD = game.getManager(HUDManager.class).getPublicHUD();
    }
    public void addHUD(){
        publicHUD.addScoreboard(KEYS[0], new ScoreboardText(" ", HUDPriority.GAME_API.get(11)), true);
        publicHUD.addScoreboard(KEYS[1], new ScoreboardText(getGameString(), HUDPriority.GAME_API.get(10)), true);
        publicHUD.addScoreboard(KEYS[2], new ScoreboardText(getGameNameString(), HUDPriority.GAME_API.get(9)), true);
        publicHUD.addScoreboard(KEYS[3], new ScoreboardText(getPlayersString(), HUDPriority.GAME_API.get(8)), true);
        publicHUD.addScoreboard(KEYS[4], new ScoreboardText(" ", HUDPriority.GAME_API.get(7)), true);
        publicHUD.addScoreboard(KEYS[5], new ScoreboardText(getMapsString(), HUDPriority.GAME_API.get(6)), true);
        publicHUD.addScoreboard(KEYS[6], new ScoreboardText(getPreviousMapString(), HUDPriority.GAME_API.get(5)), true);
        publicHUD.addScoreboard(KEYS[7], new ScoreboardText(getNextMapString(), HUDPriority.GAME_API.get(4)), true);
        publicHUD.addScoreboard(KEYS[8], new ScoreboardText(" ", HUDPriority.GAME_API.get(3)), true);
        publicHUD.addScoreboard(KEYS[9], new ScoreboardText(getStatsString(), HUDPriority.GAME_API.get(2)), true);
        publicHUD.addScoreboard(KEYS[10], new ScoreboardText(getCoinsString(), HUDPriority.GAME_API.get(1)), true);
        publicHUD.addScoreboard(KEYS[11], new ScoreboardText(getCreditsString(), HUDPriority.GAME_API.get(0)), true);
    }

    @Override
    public void join(GamePlayer player) {

    }

    @Override
    public void leave(GamePlayer player) {

    }
    //Game
    private String getGameString(){
        return ChatColor.YELLOW + "GAME";
    }
    private String getGameNameString(){
        return ChatColor.WHITE + "Name: " + ChatColor.GREEN + game.getName();
    }
    private String getPlayersString(){
        return ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getManager(PlayerManager.class).getPlayers().size();
    }
    //Maps
    private String getMapsString(){
        return ChatColor.YELLOW + "MAPS";
    }

    private String getPreviousMapString(){
        return ChatColor.WHITE + "Previous: " + ChatColor.GREEN + "Map Name";
    }
    private String getNextMapString() {
        return ChatColor.WHITE + "Next: " + ChatColor.GREEN + "Map Name";
    }
    //Stats
    private String getStatsString(){
        return ChatColor.YELLOW + "STATS";
    }

    private String getCoinsString(){
        return ChatColor.WHITE + "Coins: " + ChatColor.GREEN + 0;
    }
    private String getCreditsString() {
        return ChatColor.WHITE + "Credits: " + ChatColor.GREEN + 0;
    }

}
