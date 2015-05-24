package com.exorath.game.lib;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by too on 23/05/2015.
 * GAPIPlayer rank.
 */
public enum Rank {
    NONE("", 0),
    VIP(ChatColor.DARK_PURPLE + "VIP", 1),
    PREMIUM(ChatColor.GREEN + "Premium",2),
    MODERATOR(ChatColor.AQUA + "Moderator",3),
    ADMIN(ChatColor.DARK_RED + "Admin", 4),
    OWNER(ChatColor.GOLD + "Owner", 5);
    private String name;
    private int inheritance;
    Rank(String name, int inheritance){
        this.name = name;
        this.inheritance = inheritance;
    }
    public String getName(){
        return name;
    }
    public int getInheritance(){
        return inheritance;
    }
    public boolean inheritsFrom(Rank rank){
        return (this.getInheritance() >= rank.getInheritance())? true: false;
    }
}
