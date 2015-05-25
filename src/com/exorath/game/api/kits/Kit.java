package com.exorath.game.api.kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;

import com.exorath.game.api.Properties;
import com.exorath.game.api.events.GPlayerDeathEvent;
import com.exorath.game.api.events.GPlayerInteractEvent;
import com.exorath.game.api.events.GPlayerItemConsumeEvent;
import com.exorath.game.api.events.GPlayerRespawnEvent;
import com.exorath.game.api.events.GPlayerToggleSneakEvent;
import com.exorath.game.api.events.GPlayerToggleSprintEvent;
import com.exorath.game.api.events.GameStartEvent;
import com.exorath.game.api.events.GameStopEvent;
import com.exorath.game.api.players.GPlayer;
import com.exorath.game.lib.UnlockRequirements;
import com.exorath.game.lib.items.ItemStackBuilder;
import com.exorath.game.lib.items.PurchasableItem;

/**
 * Created by too on 23/05/2015.
 * Base class for all GPlayer kits.
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
    public void onGameStart( GameStartEvent event ) {}
    
    public void onGameStop( GameStopEvent event ) {}
    
    public void onInteract( GPlayerInteractEvent event ) {}
    
    public void onDeath( GPlayerDeathEvent event ) {}
    
    public void onConsume( GPlayerItemConsumeEvent event ) {}
    
    public void onRespawn( GPlayerRespawnEvent event ) {}
    
    public void onSneakToggle( GPlayerToggleSneakEvent event ) {}
    
    public void onSprintToggle( GPlayerToggleSprintEvent event ) {}
    
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
    
    public Material getMaterial() {
        return this.properties.as( KitProperty.NAME, Material.class );
    }
    
    public void setDescription( String[] description ) {
        this.properties.set( KitProperty.DESCRIPTION, description );
    }
    
    public String[] getDescription() {
        return this.properties.as( KitProperty.NAME, String[].class );
    }
    
    public UnlockRequirements getUnlockRequirements() {
        return this.properties.as( KitProperty.UNLOCK_REQUIREMENTS, UnlockRequirements.class );
    }
    
    public void setUnlockRequirements( UnlockRequirements reqs ) {
        this.properties.set( KitProperty.UNLOCK_REQUIREMENTS, reqs );
    }
    
    public PurchasableItem getKitItem( GPlayer player ) {
        //TODO: Check if kit is unlocked or not (last boolean)
        return new PurchasableItem( this.getName(), this.getMaterial(), this.getDescription(), this.getUnlockRequirements(), player, false );
    }
    
    /**
     * Gets the itemStack for this kit to be displayed in the shop inventory
     *
     * @return itemStack with nice markup
     */
    public ItemStack getKitSelectorItem( GPlayer player ) {
        ItemStackBuilder builder = new ItemStackBuilder( this.getMaterial() );
        
        return builder.get();
    }
    
    /**
     * This ItemStack can be used for display purposes in your game.
     *
     * @return
     */
    public ItemStack getKitItem() {
        // TODO
        return null;
        //return new ItemStackBuilder(ChatColor.YELLOW + this.getName(), this.getMaterial(), this.getDescription());
    }
    
    /**
     * Give this kit to the specified player, once game has started events will trigger.
     *
     * @param player
     */
    public void give( GPlayer player ) {
        //TODO: Go into GPlayer and add methods addKit, delKit, clearKits and getKits.
    }
    
    public boolean isUnlocked( GPlayer player ) {
        UnlockRequirements reqs = this.getUnlockRequirements();
        if ( reqs == null ) {
            return true;
        }
        //if( player doesn't have honor points){
        return false;
        //else if player doesn't have credits
        //return false;
        
    }
    
    /* Start Event */
    //TODO: Call these events when ingame
    //TODO: Add game start event as parameter
    public void onGameStart( GPlayer player ) {}
    
    //TODO: Change these events to custom events
    public void onInteract( PlayerInteractEvent event, GPlayer player ) {}
    
    public void onDeath( PlayerDeathEvent event, GPlayer player ) {}
    
    public void onConsume( PlayerItemConsumeEvent event, GPlayer player ) {}
    
    public void onRespawn( PlayerRespawnEvent event, GPlayer player ) {}
    
    public void onSneakToggle( PlayerToggleSneakEvent event, GPlayer player ) {}
    
    public void onSprintToggle( PlayerToggleSprintEvent event, GPlayer player ) {}
    
    /* End Events */
    
    public static String greenOrRedString( String name, boolean green ) {
        return ( green ? ChatColor.GREEN : ChatColor.RED ) + name;
    }
}
