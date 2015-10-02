package com.exorath.game.api.type.minigame;

import java.util.Set;

import org.bukkit.ChatColor;

import com.exorath.game.GameAPI;
import com.exorath.game.api.GameRunnable;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Manager;
import com.exorath.game.api.StopCause;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.effects.FlickerEffect;
import com.exorath.game.api.hud.locations.scoreboard.ScoreboardText;
import com.exorath.game.api.message.GameMessenger;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerManager;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;
import com.exorath.game.api.type.minigame.countdown.MinigameCountdown;
import com.exorath.game.lib.JoinLeave;
import com.google.common.collect.Sets;

/**
 * Created by Toon on 9/1/2015.
 * Not functional yet
 */
public class MinigameStateManager implements Manager, JoinLeave {

    private Minigame minigame;
    private MinigameCountdown countdown;

    public MinigameStateManager(Minigame minigame) {
        this.minigame = minigame;
        minigame.setState(GameState.WAITING);
        setupHUD();
        countdown = new MinigameCountdown(minigame);
    }

    @Override
    public void join(GamePlayer player) {
        checkStart();
    }

    @Override
    public void leave(GamePlayer player) {
        checkStop();
    }

    private void setupHUD() {
        minigame.getManager(HUDManager.class).getPublicHUD().setScoreboardTitle(ChatColor.BOLD + "EXORATH");
        minigame.getManager(HUDManager.class).getPublicHUD().setScoreboardEffect(new FlickerEffect(20, ChatColor.GOLD));
        ScoreboardText txt = new ScoreboardText(ChatColor.BOLD + "Currently under dev.", HUDPriority.HIGHEST);
        txt.setEffect(new FlickerEffect(4, ChatColor.BLACK));
        minigame.getManager(HUDManager.class).getPublicHUD().addScoreboard("gapi_advert", txt);
    }

    //Run this when a player joins
    public void checkStart() {
        if (minigame.getState() != GameState.WAITING)
            return;
        Set<Team> needMore = Sets.newHashSet();
        for (Team team : minigame.getManager(TeamManager.class).getTeams())
            //TODO: Check if min amount of players are in this team
            if (team.getPlayers().size() < team.getMinTeamSize())
                needMore.add(team);
        if (needMore.size() > 0) {
            for (Team t : needMore)
                GameMessenger.sendInfo(minigame, "Team " + t.getName() + " needs more players: " + t.getPlayers().size() + "/" + t.getMinTeamSize());
            return;
        }
        int min = minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
        int players = minigame.getManager(PlayerManager.class).getPlayerCount();
        if (countdown.isCountingDown())
            GameAPI.printConsole("Player joined: " + players + "/" + min);
        else if (minigame.hasMinPlayers()) {
            countdown.start();
            GameMessenger.sendInfo(minigame,
                    "Starting in " + minigame.getProperties().as(Minigame.START_DELAY, Integer.class) / 20 + "s. Prepare yourself!");
        } else
            GameMessenger.sendInfo(minigame, "More players required: " + players + "/" + min);
    }

    //Run this when a player leaves
    public void checkStop() {
        if (minigame.getState() == GameState.WAITING) {
            int min = minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
            int players = minigame.getManager(PlayerManager.class).getPlayerCount();
            if (players <= min) {
                countdown.stop();

                minigame.getManager(HUDManager.class).getPublicHUD().updateScoreboard("gapi_advert",
                        ChatColor.RED + ChatColor.BOLD.toString() + "Countdown cancelled. Minimum: " + players + "/" + min);
            }
        } else if (minigame.getState() == GameState.INGAME)
            if (minigame.getOnlinePlayers().isEmpty())
                stop(StopCause.NO_PLAYERS);
            else if (!minigame.getManager(TeamManager.class).hasPlayersPlaying())
                stop(StopCause.NO_PLAYERS);
    }

    //** State Loop [WAITING -> STARTING -> INGAME -> FINISHING -> RESETTING] **//
    //* Start: *//
    public void start() {
        if (minigame.getState() != GameState.WAITING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.STARTING);
        countdown.stop();
        minigame.setState(GameState.STARTING);
        minigame.spawnPlayers();
        setIngame();
    }

    protected void setIngame() {
        if (minigame.getState() != GameState.STARTING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.INGAME);
        minigame.setState(GameState.INGAME);
        //Force end game if max duration is reached
        int delay = minigame.getProperties().as(Minigame.MAX_DURATION, Integer.class);
        if (delay != 0)
            new EndTask().runTaskLater(GameAPI.getInstance(), delay);

    }

    public void stop(StopCause cause) {
        if (minigame.getState() != GameState.INGAME)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.FINISHING);
        minigame.setState(GameState.FINISHING);
        minigame.reward();
        setResetting();

    }
    public void setResetting() {
        if (minigame.getState() != GameState.FINISHING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.RESETTING);
        minigame.setState(GameState.RESETTING);
        minigame.reset();
        setWaiting();
    }

    public void setWaiting() {
        if (minigame.getState() != GameState.RESETTING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.WAITING);
        minigame.setState(GameState.WAITING);
        checkStart();
    }

    private class EndTask extends GameRunnable {

        public EndTask() {
            super(minigame);
        }

        @Override
        public void _run() {
            if (minigame.getState() == GameState.INGAME)
                minigame.getStateManager().stop(StopCause.TIME_UP);
        }
    }
}
