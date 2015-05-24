package com.exorath.gameAPI.game.kits;

import com.exorath.gameAPI.game.players.GAPIPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by too on 23/05/2015.
 * Base class for all GAPIPlayer kits.
 */
public abstract class Kit {
    private static final String DEFAULT_NAME = "Kit";
    private static final Material DEFAULT_MATERIAL = Material.WORKBENCH;
    private static final String[] DEFAULT_DESCRIPTION = new String[]{ChatColor.WHITE + "This is a cool kit."};

    private GAPIPlayer player;
    private KitProperties properties;

    public Kit(String name, Material material, GAPIPlayer player){
        this.player = player;
        this.properties = new KitProperties();
    }

    //TODO: Call these events when ingame
    //TODO: Add game start event as parameter
    public void onGameStart(){}
    //TODO: Change these events to custom events
    public void onInteract(PlayerInteractEvent event){}
    public void onDeath(PlayerDeathEvent event){}
    public void onConsume(PlayerItemConsumeEvent event){}
    public void onRespawn(PlayerRespawnEvent event){}
    public void onSneakToggle(PlayerToggleSneakEvent event){}
    public void onSprintToggle(PlayerToggleSprintEvent event){}

    public void getKitItem(){
        ItemStack is = new ItemStack(getMaterial());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.WHITE + "Select: " + getName());
    }
    
    public GAPIPlayer getPlayer() {
        return player;
    }
    public void setPlayer(GAPIPlayer player) {
        this.player = player;
    }
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
    public void setDescription(String[] description){
        properties.set(KitProperty.DESCRIPTION, description);
    }
    public String[] getDescription(){
        return (String[]) properties.get(KitProperty.NAME, DEFAULT_DESCRIPTION);
    }
}
