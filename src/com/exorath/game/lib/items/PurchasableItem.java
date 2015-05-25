package com.exorath.game.lib.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.exorath.game.api.players.GPlayer;
import com.exorath.game.lib.Rank;
import com.exorath.game.lib.UnlockRequirements;
import com.yoshigenius.lib.util.ItemStackBuilder;

/**
 * Created by too on 24/05/2015.
 * A simpler way to generate a purchasable item, this always requires complex and ugly methods so
 * it's better to do once.
 */
public class PurchasableItem extends ItemStack {
    private GPlayer player;
    private String name;
    private Material material;
    private String[] description;
    private UnlockRequirements requirements;
    
    public PurchasableItem( String name, Material material, String[] description, UnlockRequirements requirements, GPlayer player, boolean unlocked ) {
        this.name = name;
        this.material = material;
        this.description = description;
        this.requirements = requirements;
        this.player = player;
        this.buildItem( unlocked );
    }
    
    /**
     * Update the ItemStack
     * 
     * @param unlocked
     *            Whether the item has been unlocked already
     */
    public void update( boolean unlocked ) {
        this.buildItem( unlocked );
    }
    
    public void update() {
        this.buildItem();
    }
    
    private void buildItem() {
        this.buildItem( false );
    }
    
    /**
     * Generate a purchasable ItemStack with possible cost and restriction
     * Ugly method handy to avoid repetition
     * TODO: Add possible amount support
     * 
     * @param unlocked
     *            Whether the item has been unlocked already
     */
    private void buildItem( boolean unlocked ) {
        ItemStackBuilder builder = new ItemStackBuilder( this );
        ItemStackBuilder.ItemStackBuilderMeta meta = builder.getMeta();
        meta.setLore( this.description );
        meta.addLore( "" );
        if ( unlocked ) {
            meta.setDisplayName( ChatColor.BOLD.toString() + ChatColor.GREEN + this.name );
        } else {
            meta.setDisplayName( PurchasableItem.getRedOrGreen( ChatColor.BOLD + this.name, this.requirements.canUnlock( this.player ) ) );
            if ( this.requirements.getHonorPoints() > 0 ) {
                meta.addLore( PurchasableItem.getRedOrGreen( "Cost: " + this.requirements.getHonorPoints() + " honor points",
                        this.player.hasHonorPoints( this.requirements.getHonorPoints() ) ) );
            }
            if ( this.requirements.getCredits() > 0 ) {
                meta.addLore( PurchasableItem.getRedOrGreen( "Cost: " + this.requirements.getCredits() + " credits",
                        this.player.hasCredits( this.requirements.getCredits() ) ) );
            }
            if ( this.requirements.getMinRank() != Rank.NONE ) {
                meta.addLore( PurchasableItem.getRedOrGreen( ChatColor.BOLD + this.requirements.getMinRank().getName() + " required", this.player
                        .getRank().inheritsFrom( this.requirements.getMinRank() ) ) );
            }
        }
    }
    
    private static String getRedOrGreen( String line, boolean green ) {
        if ( green ) {
            return ChatColor.GREEN + line;
        }
        return ChatColor.RED + line;
    }
    
    public UnlockRequirements getRequirements() {
        return this.requirements;
    }
    
    public void setRequirements( UnlockRequirements requirements ) {
        this.requirements = requirements;
    }
    
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial( Material material ) {
        this.material = material;
    }
    
    public String[] getDescription() {
        return this.description;
    }
    
    public void setDescription( String[] description ) {
        this.description = description;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
}
