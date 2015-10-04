package com.exorath.game.api.npc;

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
    public String getName();

    /**
     * Get NPCs skin name if a Player NPC, otherwise null.
     *
     * @return NPCs skin name.
     */
    public String getSkin();

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
        return getProperties().as(NPCProperty.PROTECTED, boolean.class);
    }

    /**
     * Enable or disable protection.
     *
     * @param protect
     *            Whether protection should be enabled or disabled.
     */
    default public void setProtected(boolean enabled) {
        getProperties().set(NPCProperty.PROTECTED, enabled);
    }

    /**
     * Gets the NPC's NPCEquipment object.
     *
     * @return If the NPC can have equipment, then it will return the NPC's
     *         equipment, otherwise
     *         null.
     */
    public NPCEquipment getEquipment();

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
    public void onClicked(Game game, GamePlayer player, SpawnedNPC npc);

}
