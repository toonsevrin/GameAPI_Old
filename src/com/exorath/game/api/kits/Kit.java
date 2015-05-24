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
import com.exorath.game.api.players.GPlayer;
import com.exorath.game.lib.UnlockRequirements;

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
    
    public UnlockRequirements getUnlockRequirements() {
        return this.properties.as( KitProperty.UNLOCK_REQUIREMENTS, UnlockRequirements.class );
    }
    
    public void setUnlockRequirements( UnlockRequirements reqs ) {
        this.properties.set( KitProperty.UNLOCK_REQUIREMENTS, reqs );
    }
    
    public static void getKitItem( String name, Material material ) {}
    
    /*
            
            /**
     * Gets the itemStack for this kit to be displayed in the shop inventory
     * @return itemStack with nice markup
             /
            public ItemStack getKitSelectorItem(GPlayer player) {
                ItemStack is = new ItemStack(Material.getMaterial( this.getMaterial() ));
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
                lore.addAll(this.getDescription());
                lore.add
                return is);
            }
            
     */
    
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
