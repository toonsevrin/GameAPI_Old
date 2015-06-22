package com.exorath.game.api.npc;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

import com.exorath.game.api.Properties;

/**
 * @author toon
 * @author Nick Robson
 */
public interface NPC {
    
    /**
     * Get the NPCs display name.
     * 
     * @return The NPCs display name.
     */
    public String getName();
    
    /**
     * Set the NPCs display name.
     * 
     * @param name
     *            The NPCs display name.
     */
    public void setName( String name );
    
    /**
     * Get NPCs skin name if a Player NPC, otherwise null.
     * 
     * @return NPCs skin name.
     */
    public String getSkin();
    
    /**
     * Set NPCs skin name if this entity is a Player NPC.
     * 
     * @param skin
     *            NPCs skin name.
     */
    public void setSkin( String skin );
    
    /**
     * Get NPCs entity type
     * 
     * @return NPCs entity type, null if not set
     */
    public Class<? extends LivingEntity> getEntityClass();
    
    /**
     * Get NPCs properties
     * 
     * @return NPCs properties
     */
    public Properties getProperties();
    
    /**
     * Checks if NPC is invulnerable.
     * 
     * @return Whether or not NPC is invulnerable.
     */
    default public boolean isProtected() {
        return this.getProperties().as( NPCProperty.PROTECTED, boolean.class );
    }
    
    /**
     * Enable or disable protection.
     * 
     * @param protect
     *            Whether protection should be enabled or disabled.
     */
    default public void setProtected( boolean enabled ) {
        this.getProperties().set( NPCProperty.PROTECTED, enabled );
    }
    
    /**
     * Gets the NPC's EntityEquipment object.
     * 
     * @return If the NPC can have equipment, then it will return the NPC's equipment, otherwise
     *         null.
     */
    public EntityEquipment getEquipment();
    
}
