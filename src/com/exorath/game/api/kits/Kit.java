package com.exorath.game.api.kits;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.exorath.game.api.Properties;
import com.exorath.game.api.players.GPlayer;

/**
 * Created by too on 23/05/2015.
 * Base class for all GAPIPlayer kits.
 */
public abstract class Kit {
    
    public static final String DEFAULT_NAME = "Kit";
    public static final Material DEFAULT_MATERIAL = Material.WORKBENCH;
    public static final String[] DEFAULT_DESCRIPTION = new String[] { ChatColor.WHITE + "This is a cool kit." };
    
    private GPlayer player;
    private Properties properties = new Properties();
    
    public Kit( String name, Material material, GPlayer player ) {
        this.player = player;
    }
    
    //TODO: Call these events when ingame
    //TODO: Add game start event as parameter
    public void onGameStart() {}
    
    //TODO: Change these events to custom events
    public void onInteract( PlayerInteractEvent event ) {}
    
    public void onDeath( PlayerDeathEvent event ) {}
    
    public void onConsume( PlayerItemConsumeEvent event ) {}
    
    public void onRespawn( PlayerRespawnEvent event ) {}
    
    public void onSneakToggle( PlayerToggleSneakEvent event ) {}
    
    public void onSprintToggle( PlayerToggleSprintEvent event ) {}
    
    public GPlayer getPlayer() {
        return this.player;
    }
    
    public void setPlayer( GPlayer player ) {
        this.player = player;
    }
    
    public void setName( String name ) {
        this.properties.set( KitProperty.NAME, name );
    }
    
    public String getName() {
        return this.properties.as( KitProperty.NAME, String.class );
    }
    
    public void setMaterial( Material material ) {
        this.properties.set( KitProperty.MATERIAL, material );
    }
    
    public String getMaterial() {
        return this.properties.as( KitProperty.NAME, String.class );
    }
    
    public void setDescription( String[] description ) {
        this.properties.set( KitProperty.DESCRIPTION, description );
    }
    
    public String getDescription() {
        return this.properties.as( KitProperty.NAME, String.class );
    }
    
    public static void getKitItem( String name, Material material ) {
        
    }
}
