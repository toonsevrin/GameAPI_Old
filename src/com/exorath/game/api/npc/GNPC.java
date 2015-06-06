package com.exorath.game.api.npc;

import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.navigator.Navigator;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by too on 24/05/2015.
 * TODO: Create an npc control layer
 */
public interface GNPC {
    
    /**
     * Get npcs display name
     * 
     * @return Npcs display name
     */
    public String getName();
    
    /**
     * Set npcs display name
     * 
     * @param name
     *            npcs display name
     */
    public void setName( String name );
    
    /**
     * Get npcs skin name
     * 
     * @return npcs skin name
     */
    public String getSkin();
    
    /**
     * Set npcs skin name
     * 
     * @param skin
     *            Npcs skin name
     */
    public void setSkin( String skin );
    
    /**
     * Get npcs location
     * 
     * @return Npcs location. Null if not spawned
     */
    public Location getLocation();
    
    /**
     * Teleport npc, spawn him if not spawned.
     * 
     * @param location
     *            Location to teleport npc too.
     */
    public void setLocation( Location location );
    
    /**
     * Get npcs properties
     * 
     * @return Npcs properties
     */
    public Properties getProperties();
    
    /**
     * Get npcs UUID
     * 
     * @return Npcs UUID
     */
    public UUID getUniqueID();
    
    /**
     * Get npcs entity type
     * 
     * @return Npcs entity type, null if not set
     */
    public EntityType getEntityType();
    
    /**
     * Set npcs entity type
     * 
     * @param entityType
     *            Npcs entity type
     */
    public void setEntityType( EntityType entityType );
    
    /**
     * Checks if npc is invulnerable
     * 
     * @return whether or not npc is invulnerable
     */
    public boolean isProtected();
    
    /**
     * Enable or disable protection
     * 
     * @param protect
     *            Enable or disables protection
     */
    public void setProtected( boolean protect );
    
    /**
     * Spawns the npc
     * 
     * @param location
     *            location to spawn at
     * @return true if spawned successfully, false if not
     */
    public boolean spawn( Location location );
    
    /**
     * Despawn the npc
     * 
     * @return returns true if despawned successfully (False if not spawned)
     */
    public boolean despawn();
    
    /**
     * Get this npcs navigator
     * 
     * @return The npcs navigator
     */
    public Navigator getNavigator();
    
    /**
     * Set the npcs navigator
     * 
     * @param navigator
     *            Npcs navigator
     */
    public void setNavigator( Navigator navigator );
    
    //START inventory methods (Only affect entities that can hold these items)
    
    public void setItemInHand( ItemStack itemInHand );
    
    public ItemStack getItemInHand();
    
    public void setHelmet( ItemStack helmet );
    
    public ItemStack getHelmet();
    
    public void setChestPlate( ItemStack chestPlate );
    
    public ItemStack getChestPlate();
    
    public void setLeggings( ItemStack leggings );
    
    public ItemStack getLeggings();
    
    public void setBoots( ItemStack boots );
    
    public ItemStack getBoots();
    
    //END inventory methods
    
}
