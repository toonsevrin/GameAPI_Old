package com.exorath.game.api.type.minigame;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.SoundInfo;
import com.exorath.game.api.countdown.CountdownFrame;
import com.exorath.game.api.countdown.CountdownFrameBuilder;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by TOON on 9/2/2015.
 * TODO: Tons of stuff!! This class will manage the visual countdown when you
 * start a minigame.
 */
public class MinigameCountdown {

    private Minigame game;

    protected static final int BLACK_CHARS = 15;
    protected static final int GREEN_CHARS = 6;
    protected static final int LENGTH = MinigameCountdown.BLACK_CHARS + MinigameCountdown.GREEN_CHARS;
    private static final char CHAR = '➤';

    private boolean countingDown = false;

    private List<CountdownFrame> frames = new ArrayList<>();

    public MinigameCountdown(Minigame game) {
        this.game = game;
        setupFrames();
    }

    //** MinigameStateManager functions **//
    public void start() {
        if (countingDown)
            return;
        countingDown = true;
        new CountdownTask().runTaskTimer(GameAPI.getInstance(), 0, 1);
    }

    public void stop() {
        if (!countingDown)
            return;
        countingDown = false;
        finish();
    }

    //** Frame functions **//
    protected void finish() {
        for (int i = frames.size(); i > 0; i--) {
            final int x = i - 1;
            game.getOnlinePlayers().forEach(p -> frames.get(x).end(p));
        }
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        publicHUD.removeActionBar("gapi_cdbar");
    }

    protected void startGame() {
        game.getStateManager().start();
    }

    //** Setup **//
    private void setupFrames() {
        for (int i = 0; i <= MinigameCountdown.LENGTH; i++)
            frames.add(getArrows(i));
        frames.add(getFinalCountdown(ChatColor.RED, 3));
        frames.add(getFinalCountdown(ChatColor.GOLD, 2));
        frames.add(getFinalCountdown(ChatColor.GREEN, 1));
        StringBuilder startBuilder = new StringBuilder();
        startBuilder.append(getArrow(ChatColor.GREEN, MinigameCountdown.LENGTH / 2 - 2, MinigameCountdown.CHAR)).append(ChatColor.BOLD)
        .append(" START ").append(getArrow(ChatColor.GREEN, MinigameCountdown.LENGTH / 2 - 2, MinigameCountdown.CHAR));
        startBuilder.insert(0, getWhiteSpaces(startBuilder.toString()));
        SoundInfo sound = new SoundInfo(Sound.NOTE_PLING, 3, 10);
        frames.add(CountdownFrameBuilder.newBuilder().sound(sound).subtitle(startBuilder.toString()).title("").delay(20).build());
        frames.add(CountdownFrameBuilder.newBuilder().display(p -> {
            try {
                p.getHud().getTitle().removeText("gapi.countdown.title");
                p.getHud().getSubtitle().removeText("gapi.countdown.subtitle");
                stop();
                game.getStateManager().start();
            } catch (Exception ignored) {
            }
        }).build());
    }

    private CountdownFrame getArrows(int frame) {//TODO: Add percentage and precedingarrows calculation
        StringBuilder sb = new StringBuilder();
        if (frame == 0)
            sb.append(ChatColor.RESET);

        float percentage = frame == 0 ? 0 : 1f / MinigameCountdown.LENGTH * frame;
        int precedingArrows = (int) (percentage * MinigameCountdown.BLACK_CHARS);

        getArrow(sb, ChatColor.BLACK, precedingArrows, MinigameCountdown.CHAR);
        getArrow(sb, ChatColor.GREEN, MinigameCountdown.GREEN_CHARS, MinigameCountdown.CHAR);
        getArrow(sb, ChatColor.BLACK, MinigameCountdown.BLACK_CHARS - precedingArrows, MinigameCountdown.CHAR);
        sb.insert(0, getWhiteSpaces(sb.toString()));
        if (frame == frames.size() - 1)
            sb.insert(0, ChatColor.WHITE);
        return CountdownFrameBuilder.newBuilder().subtitle(sb.toString()).delay((long) getInterval()).build();
    }

    private void getArrow(StringBuilder sb, ChatColor color, int amount, char c) {
        if (amount == 0)
            return;
        sb.append(color);
        for (int i = 0; i < amount; i++)
            sb.append(c);
    }

    private String getArrow(ChatColor color, int amount, char c) {
        if (amount == 0)
            return "";
        StringBuilder sb = new StringBuilder().append(color);
        for (int i = 0; i < amount; i++)
            sb.append(c);
        return sb.toString();
    }

    private CountdownFrame getFinalCountdown(ChatColor color, int number) {
        StringBuilder sb = new StringBuilder();
        getArrow(sb, color, MinigameCountdown.LENGTH, MinigameCountdown.CHAR);
        sb.insert(0, getWhiteSpaces(sb.toString(), 2));
        return CountdownFrameBuilder.newBuilder()
                .title(color.toString() + number)
                .subtitle(sb.toString())
                .sound(new SoundInfo(Sound.NOTE_PLING, 1, 10))
                .delay(20)
                .build();
    }

    //** Countdown task **//
    private class CountdownTask extends BukkitRunnable {

        private int currentFrame = 0;
        private int tick = 0;

        @Override
        public void run() {
            if (!countingDown) {
                stop();
                cancel();
                return;
            }
            if (currentFrame == frames.size()) {
                stop();
                cancel();
                return;
            }
            /* Action bar */
            float remaining = getInterval() * (LENGTH - currentFrame) / 20f + getInterval() / 20f * 4 - tick / 20f;
            String cdText = remaining <= 2 ? ChatColor.GREEN.toString() + ChatColor.BOLD + "Game starting..."
                    : ChatColor.BOLD + "Starting in... " + new DecimalFormat("#.0").format(remaining);
            HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
            if (publicHUD.containsActionBar("gapi_cdbar"))
                publicHUD.updateActionBar("gapi_cdbar", cdText);
            else
                publicHUD.addActionBar("gapi_cdbar", new HUDText(cdText, new HUDPriority(HUDPriority.HIGH)), true);
            /* Frames */
            tick++;
            if (tick < frames.get(currentFrame).getDelay())
                return;
            tick = 0;
            game.getOnlinePlayers().forEach(p -> frames.get(currentFrame).display(p));
            currentFrame++;
        }
    }

    private float getInterval() {
        return game.getProperties().as(Minigame.START_DELAY, Integer.class) / (float) MinigameCountdown.LENGTH;
    }

    private String getWhiteSpaces(String str) {
        return getWhiteSpaces(str, 0);
    }

    private String getWhiteSpaces(String str, int extra) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.RESET);
        for (int i = 0; i < StringUtils.countMatches(str, String.valueOf(ChatColor.COLOR_CHAR)) + extra; i++)
            sb.append("  ");
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public boolean isCountingDown() {
        return countingDown;
    }
}
