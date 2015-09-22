package com.exorath.game.api.hud.effects;

import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class ScrollEffect extends IntervalEffect {
    private ChatColor lastColor;
    private boolean colorNext = false;
    private int startIndex = 0;
    private int length = 16;
    private String suffix = " ";

    public ScrollEffect(int interval, int length) {
        this(interval, length, true);
    }
    public ScrollEffect(int interval, int length, boolean space){
        super(interval);
        if(!space)
            suffix = "";
        this.length = length;
    }

    @Override
    public void run() {
        setText(getDisplayText());
        startIndex++;
    }

    @Override
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        char[] chars = getText().toCharArray();
        //Append the last color to the string
        if(chars[startIndex % chars.length] == ChatColor.COLOR_CHAR) {
            lastColor = ChatColor.getByChar(chars[(startIndex + 1) % chars.length]);
        }else if(lastColor != null){
            sb.append(lastColor);
        }
        int start = colorNext ? 1 : 0;
        //append all chars from text
        for (int i = 0 + start; i < length; i++) {
            int index =(startIndex + i) % (chars.length);
            sb.append(chars[index]);
            if(index + 1 == chars.length && i != length - 1)
                sb.append(suffix);
        }

        //Update whether or not theres a color char next
        if(chars[startIndex % chars.length] == ChatColor.COLOR_CHAR)
            colorNext = true;
        else if(lastColor != null)
            colorNext = false;
        return sb.toString();
    }

    @Override
    public HUDEffect clone() {
        ScrollEffect effect = new ScrollEffect(getInterval(), length);
        effect.startIndex = startIndex;
        return effect;
    }
}
