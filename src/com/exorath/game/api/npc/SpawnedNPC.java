package com.exorath.game.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import com.exorath.game.api.npc.navigator.NPCNavigator;

/**
 * @author Nick Robson
 */
public class SpawnedNPC {
    
    public static SpawnedNPC spawn( NPC npc, Location location ) {
        LivingEntity entity = location.getWorld().spawn( location, npc.getEntityClass() );
        // TODO: Set names, skins, player NPC handling.
        SpawnedNPC snpc = new SpawnedNPC( npc, entity );
        return snpc;
    }
    
    private NPC npc;
    private LivingEntity entity;
    private NPCNavigator navigator = null;
    
    public SpawnedNPC( NPC npc, LivingEntity entity ) {
        this.npc = npc;
        this.entity = entity;
    }
    
    /**
     * Gets the corresponding NPC type of this spawned NPC.
     * 
     * @return This NPC's matching NPC object.
     */
    public NPC getNPC() {
        return this.npc;
    }
    
    /**
     * Gets the NPC's corresponding Bukkit entity.
     * 
     * @return The Bukkit entity.
     */
    public LivingEntity getBukkitEntity() {
        return this.entity;
    }
    
    /**
     * Gets whether or not this NPC is valid.
     * 
     * @return True iff the entity exists and is valid.
     */
    public boolean isValid() {
        return this.entity != null && this.entity.isValid() && !this.entity.isDead();
    }
    
    /**
     * Despawns this NPC.
     */
    public void despawn() {
        this.entity.remove();
        this.entity = null;
    }
    
    /**
     * Gets the NPC's corresponding NPCNavigator, or null if it hasn't been created yet.
     * 
     * @return The NPC's navigator object.
     * @see {@link #createNavigator()}
     */
    public NPCNavigator getNavigator() {
        return this.navigator;
    }
    
    /**
     * Creates and sets this NPC's navigator if non-existent.
     * 
     * @return The old navigator (if it existed), or the newly created one.
     */
    public NPCNavigator createNavigator() {
        if ( this.navigator == null ) {
            this.navigator = new NPCNavigator( this );
        }
        return this.navigator;
    }
    
}
