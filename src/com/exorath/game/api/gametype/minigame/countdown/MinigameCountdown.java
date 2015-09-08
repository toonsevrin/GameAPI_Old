package com.exorath.game.api.gametype.minigame.countdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import org.bukkit.Sound;

import com.exorath.game.api.gametype.minigame.Minigame;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by TOON on 9/2/2015.
 * TODO: Tons of stuff!! This class will manage the visual countdown when you start a minigame.
 */
public class MinigameCountdown {
    private Minigame game;

    private static final int BLACK_CHARS = 15;
    private static final int GREEN_CHARS = 6;
    private static final int LENGTH = BLACK_CHARS + GREEN_CHARS;
    private static final char CHAR = '➤';

    private boolean countingDown = false;

    private int currentFrame = 0;
    private List<CountdownFrame> frames = new ArrayList<>();

    public MinigameCountdown(Minigame game){
        this.game = game;
        setupFrames();
    }
    //** MinigameStateManager functions **//
    public void start(){
        if(countingDown)
            return;
        countingDown = true;
        new CountdownTask().runTaskTimer(GameAPI.getInstance(), 0, game.getProperties().as(Minigame.START_DELAY, Integer.class) / LENGTH);
    }
    public void stop(){
        if(!countingDown)
            return;
        countingDown = false;
        finish();
    }
    //** Frame functions **//
    protected void finish(){
        frames.forEach(f -> f.finish());
    }
    protected void startGame(){
        game.getStateManager().start();
    }
    //** Setup **//
    private void setupFrames(){
        for(int i = 0; i <= LENGTH; i++)
            frames.add(getArrows(i));
        frames.add(getFinalCountdown(ChatColor.RED, 3));
        frames.add(getFinalCountdown(ChatColor.GOLD, 2));
        frames.add(getFinalCountdown(ChatColor.GREEN, 1));
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.GREEN, LENGTH / 2 - 4, CHAR) + " BEGIN " + getArrow(ChatColor.RED, LENGTH / 2 - 3, CHAR), 20, Sound.NOTE_PLING, 2, 10));
        frames.add(new FinishFrame(this));
    }
    private CountdownFrame getArrows(int frame){//TODO: Add percentage and precedingarrows calculation
        StringBuilder sb = new StringBuilder();
        float percentage = frame == 0? 0 : 1f/(LENGTH) * frame;
        int precedingArrows = (int) (percentage * BLACK_CHARS);

        getArrow(sb, ChatColor.BLACK, precedingArrows, CHAR);
        getArrow(sb, ChatColor.GREEN, GREEN_CHARS, CHAR);
        getArrow(sb, ChatColor.BLACK, BLACK_CHARS - precedingArrows, CHAR);
        return new SubtitleFrame(this, sb.toString(), game.getProperties().as(Minigame.START_DELAY, Integer.class) / LENGTH);
    }
    private void getArrow(StringBuilder sb, ChatColor color, int amount, char c){
        if(amount == 0)
            return;
        sb.append(color);
        for(int i = 0; i < amount;i++)
            sb.append(c);
    }
    private String getArrow(ChatColor color, int amount, char c){
        if(amount == 0)
            return "";
        StringBuilder sb = new StringBuilder().append(color);
        for(int i = 0; i < amount;i++)
            sb.append(c);
        return sb.toString();
    }
    private CountdownFrame getFinalCountdown(ChatColor color, int number){
        String arrows = getArrow(color, LENGTH/2 - 2, CHAR);
        return new SoundCountdownFrame(this, arrows + " " + number + " " + arrows, 20, Sound.NOTE_PLING, 1, 10);
    }
    //** Countdown task **//
    private class CountdownTask extends BukkitRunnable{
        @Override
        public void run() {
            if(currentFrame == frames.size() || !countingDown){
                cancel();
                finish();
                return;
            }
            GameAPI.getOnlinePlayers().forEach(p -> frames.get(currentFrame).display(p));
            currentFrame++;
        }
    }
}
