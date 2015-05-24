package com.exorath.game.api.kits;

import com.exorath.game.api.players.GAPIPlayer;
import com.exorath.game.lib.GAPIItemStack;
import com.exorath.game.lib.UnlockRequirements;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by too on 23/05/2015.
 * Base class for all GAPIPlayer kits.
 */
public abstract class Kit {
    private static final String DEFAULT_NAME = "Kit";
    private static final Material DEFAULT_MATERIAL = Material.WORKBENCH;
    private static final String[] DEFAULT_DESCRIPTION = new String[]{ChatColor.WHITE + "This is a cool kit."};

    private KitProperties properties;

    public Kit(){
        this.properties = new KitProperties();
    }

    /**
     * Gets the itemStack for this kit to be displayed in the shop inventory
     * @return itemStack with nice markup
/*
     *//*

    public ItemStack getKitSelectorItem(GAPIPlayer player) {
        GAPIItemStack is = new GAPIItemStack(getMaterial());
        UnlockRequirements reqs = getUnlockRequirements();
        String buyString;
        //if(player.hasUnlockedKit(this){
            is.setDisplayName(ChatColor.BOLD.toString()+ ChatColor.GREEN);
            is.add(ChatColor.GREEN + "Unlocked.");
        //}else{
            if(reqs.getHonorPoints() > 0) {
               //buyString = greenOrRedString("", player.getHonorPoints() >= reqs.getHonorPoints());
            }
            if(reqs.getCredits() > 0){

            }
            is.setDisplayName(ChatColor.BOLD.toString()+ ChatColor.GREEN);
        //}
        if(reqs.canUnlock(player)){

        }else{

        }
        List<String> lore = new ArrayList<String>();
        lore.addAll(getDescription());
        lore.add
        return is);
    }
*/

    /**
     * This ItemStack can be used for display purposes in your game.
     * @return
     */
    public ItemStack getKitItem(){
        return new GAPIItemStack(ChatColor.YELLOW + getName(), getMaterial(), getDescription());
    }

    /**
     * Give this kit to the specified player, once game has started events will trigger.
     * @param player
     */
    public void give(GAPIPlayer player){
        //TODO: Go into GAPIPlayer and add methods addKit, delKit, clearKits and getKits.
    }
    public boolean isUnlocked(GAPIPlayer player){
        UnlockRequirements reqs = getUnlockRequirements();
        if(reqs == null)
            return true;
        //if( player doesn't have honor points){
        return false;
        //else if player doesn't have credits
        //return false;

    }
    /* Start Event */
    //TODO: Call these events when ingame
    //TODO: Add game start event as parameter
    public void onGameStart(GAPIPlayer player){}
    //TODO: Change these events to custom events
    public void onInteract(PlayerInteractEvent event, GAPIPlayer player){}
    public void onDeath(PlayerDeathEvent event, GAPIPlayer player){}
    public void onConsume(PlayerItemConsumeEvent event, GAPIPlayer player){}
    public void onRespawn(PlayerRespawnEvent event, GAPIPlayer player){}
    public void onSneakToggle(PlayerToggleSneakEvent event, GAPIPlayer player){}
    public void onSprintToggle(PlayerToggleSprintEvent event,GAPIPlayer player){}
    /* End Events */

    public void setName(String name){
        properties.set(KitProperty.NAME, name);
    }
    public String getName(){
        return (String) properties.get(KitProperty.NAME, DEFAULT_NAME);
    }
    public void setMaterial(Material material){
        properties.set(KitProperty.MATERIAL, material);
    }
    public Material getMaterial(){
        return (Material) properties.get(KitProperty.NAME, DEFAULT_MATERIAL);
    }
    public void setDescription(List<String> description){
        properties.set(KitProperty.DESCRIPTION, description);
    }
    public List<String> getDescription(){ return (List<String>) properties.get(KitProperty.NAME, DEFAULT_DESCRIPTION); }
    public UnlockRequirements getUnlockRequirements(){ return (UnlockRequirements) properties.get(KitProperty.UNLOCK_REQUIREMENTS); }

    private static String greenOrRedString(String name, boolean green){
        if(green) return ChatColor.GREEN + name;
        return ChatColor.RED + name;
    }
}
