package com.exorath.game.api.gametype.minigame.countdown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.gametype.minigame.Minigame;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by TOON on 9/2/2015.
 * TODO: Tons of stuff!! This class will manage the visual countdown when you start a minigame.
 */
public class MinigameCountdown {
    private Minigame game;

    private static final int BLACK_CHARS = 15;
    private static final int GREEN_CHARS = 6;
    private static final int LENGTH = MinigameCountdown.BLACK_CHARS + MinigameCountdown.GREEN_CHARS;
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
        new CountdownTask().runTaskTimer(GameAPI.getInstance(), 0, game.getProperties().as(Minigame.START_DELAY, Integer.class) / MinigameCountdown.LENGTH);
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
        for(int i = 0; i <= MinigameCountdown.LENGTH; i++)
            frames.add(getArrows(i));
        frames.add(getFinalCountdown(ChatColor.RED, 3));
        frames.add(getFinalCountdown(ChatColor.GOLD, 2));
        frames.add(getFinalCountdown(ChatColor.GREEN, 1));
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.GREEN, MinigameCountdown.LENGTH / 2 - 4, MinigameCountdown.CHAR) + " BEGIN " + getArrow(ChatColor.RED, MinigameCountdown.LENGTH / 2 - 3, MinigameCountdown.CHAR), 20, Sound.NOTE_PLING, 2, 10));
        frames.add(new FinishFrame(this));
    }
    private CountdownFrame getArrows(int frame){//TODO: Add percentage and precedingarrows calculation
        StringBuilder sb = new StringBuilder();
        float percentage = frame == 0? 0 : 1f/MinigameCountdown.LENGTH * frame;
        int precedingArrows = (int) (percentage * MinigameCountdown.BLACK_CHARS);

        getArrow(sb, ChatColor.BLACK, precedingArrows, MinigameCountdown.CHAR);
        getArrow(sb, ChatColor.GREEN, MinigameCountdown.GREEN_CHARS, MinigameCountdown.CHAR);
        getArrow(sb, ChatColor.BLACK, MinigameCountdown.BLACK_CHARS - precedingArrows, MinigameCountdown.CHAR);
        return new SubtitleFrame(this, sb.toString(), game.getProperties().as(Minigame.START_DELAY, Integer.class) / MinigameCountdown.LENGTH);
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
        String arrows = getArrow(color, MinigameCountdown.LENGTH/2 - 2, MinigameCountdown.CHAR);
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
