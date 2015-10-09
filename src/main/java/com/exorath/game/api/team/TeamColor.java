package com.exorath.game.api.team;

import org.bukkit.ChatColor;
import org.bukkit.Color;

/**
 * @author Nick Robson
 */
public enum TeamColor {

    NONE(null, null),
    PURPLE(Color.PURPLE, ChatColor.DARK_PURPLE),
    CYAN(Color.TEAL, ChatColor.AQUA),
    ORANGE(Color.ORANGE, ChatColor.GOLD),
    LIME(Color.LIME, ChatColor.GREEN),
    RED(Color.RED, ChatColor.RED),
    BLUE(Color.BLUE, ChatColor.BLUE),
    GRAY(Color.GRAY, ChatColor.GRAY),
    SILVER(Color.SILVER, ChatColor.WHITE),
    GOLD(Color.YELLOW, ChatColor.GOLD);

    private final Color color;
    private final ChatColor chat;

    private TeamColor(Color color, ChatColor chat) {
        this.color = color;
        this.chat = chat;
    }

    public Color getArmorColor() {
        return color;
    }

    public ChatColor getChatColor() {
        return chat;
    }

}
