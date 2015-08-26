package com.exorath.game.api.npc.types;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.LivingEntity;

import com.exorath.game.api.Game;
import com.exorath.game.api.npc.SpawnedNPC;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.spectate.SpectateManager;

/**
 * @author Nick Robson
 */
public class SpectatorNPC extends AbstractNPC {

    public SpectatorNPC() {
        super( ChatColor.DARK_AQUA + "Spectate" );
    }

    @Override
    public Class<? extends LivingEntity> getEntityClass() {
        return org.bukkit.entity.NPC.class;
    }

    @Override
    public void onClicked( Game game, GamePlayer player, SpawnedNPC npc ) {
        game.getManager( SpectateManager.class ).setSpectating( player, true );
    }

}
