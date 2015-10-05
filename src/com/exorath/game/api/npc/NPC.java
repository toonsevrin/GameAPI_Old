package com.exorath.game.api.npc;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import com.exorath.game.api.Game;
import com.exorath.game.api.Properties;
import com.exorath.game.api.player.GamePlayer;

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
    String getName();

    /**
     * Get NPCs skin name if a Player NPC, otherwise null.
     *
     * @return NPCs skin name.
     */
    String getSkin();

    /**
     * Get NPCs entity type
     *
     * @return NPCs entity type, null if not set
     */
    Class<? extends LivingEntity> getEntityClass();

    /**
     * Get NPCs properties
     *
     * @return NPCs properties
     */
    Properties getProperties();

    /**
     * Checks if NPC is invulnerable.
     *
     * @return Whether or not NPC is invulnerable.
     */
    default boolean isProtected() {
        return getProperties().as(NPCProperty.PROTECTED, boolean.class);
    }

    /**
     * Enable or disable protection.
     *
     * @param protect
     *            Whether protection should be enabled or disabled.
     */
    default void setProtected(boolean enabled) {
        getProperties().set(NPCProperty.PROTECTED, enabled);
    }

    /**
     * Gets the NPC's NPCEquipment object.
     *
     * @return If the NPC can have equipment, then it will return the NPC's
     *         equipment, otherwise
     *         null.
     */
    NPCEquipment getEquipment();

    /**
     * Called when a player clicks the NPC.
     *
     * @param game
     *            The game the player is in.
     * @param player
     *            The player.
     * @param npc
     *            The NPC.
     */
    void onClicked(Game game, GamePlayer player, SpawnedNPC npc);

    /**
     * Spawns this NPC at the specified Location.
     *
     * @param location
     *            The location to spawn at.
     * @return A SpawnedNPC instance describing the newly-spawned NPC.
     */
    default SpawnedNPC spawn(Location location) {
        return SpawnedNPC.spawn(this, location);
    }

}
