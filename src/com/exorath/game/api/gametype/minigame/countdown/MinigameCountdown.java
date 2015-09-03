package com.exorath.game.api.gametype.minigame.countdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Sound;

import com.exorath.game.api.gametype.minigame.Minigame;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;

import net.md_5.bungee.api.ChatColor;

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

    private List<CountdownFrame> frames = new ArrayList<>();

    private HashMap<GamePlayer, HUDText> subTitles = new HashMap<>();
    public MinigameCountdown(Minigame game){
        this.game = game;
        for(int i = 0; i <= LENGTH; i++){
            frames.add(getArrows(i));
        }
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.RED, LENGTH/2 - 2, CHAR) + " 3 " + getArrow(ChatColor.RED, LENGTH / 2 - 1, CHAR), 20, Sound.NOTE_PLING, 1, 10));
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.GOLD, LENGTH/2 - 2, CHAR) + " 2 " + getArrow(ChatColor.RED, LENGTH/2 - 1, CHAR), 20, Sound.NOTE_PLING, 1, 10));
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.GREEN, LENGTH/2 - 2, CHAR) + " 1 " + getArrow(ChatColor.RED, LENGTH/2 - 1, CHAR), 20, Sound.NOTE_PLING, 2, 10));
        frames.add(new SoundCountdownFrame(this, getArrow(ChatColor.GREEN, LENGTH/2 - 4, CHAR) + " BEGIN " + getArrow(ChatColor.RED, LENGTH/2 - 3, CHAR), 20, Sound.NOTE_PLING, 2, 10));
        frames.add(new FinishFrame(this));
    }
    protected void finish(){
        frames.forEach(f -> f.finish());
        frames.clear();
    }

    private CountdownFrame getArrows(int frame){
        StringBuilder sb = new StringBuilder();
        float percentage = 0;
        int precedingArrows = 0;

        getArrow(sb, ChatColor.BLACK, precedingArrows, CHAR);
        getArrow(sb, ChatColor.GREEN, GREEN_CHARS, CHAR);
        getArrow(sb, ChatColor.BLACK, BLACK_CHARS - precedingArrows, CHAR);
        return new SubtitleFrame(this, sb.toString(), game.getProperties().as(Minigame.START_DELAY, Integer.class) / LENGTH);
    }
    private void getArrow(StringBuilder sb, ChatColor color, int amount, char c){
        if(amount == 0)
            return;
        sb.append(color);
        for(int i = 0; i < amount;i++){
            sb.append(c);
        }
    }
    private String getArrow(ChatColor color, int amount, char c){
        if(amount == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append(color);
        for(int i = 0; i < amount;i++){
            sb.append(c);
        }
        return sb.toString();
    }

    public HashMap<GamePlayer, HUDText> getSubTitles() {
        return subTitles;
    }
}
