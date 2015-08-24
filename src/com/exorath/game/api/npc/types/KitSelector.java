package com.exorath.game.api.npc.types;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import com.exorath.game.api.Game;
import com.exorath.game.api.kit.Kit;
import com.exorath.game.api.menu.EasyMenu;
import com.exorath.game.api.npc.NPCEquipment;
import com.exorath.game.api.npc.SpawnedNPC;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public class KitSelector extends AbstractNPC {

    private final Kit kit;

    public KitSelector() {
        super( ChatColor.GREEN + "Kit Selector", new NPCEquipment() );
        this.kit = null;
    }

    public KitSelector( Kit kit ) {
        super( ChatColor.GREEN + "Select: " + ChatColor.AQUA + kit.getName(), kit.toNPCEquipment() );
        this.kit = kit;
    }

    @Override
    public Class<? extends LivingEntity> getEntityClass() {
        return Zombie.class;
    }

    @Override
    public void onClicked( Game game, GamePlayer player, SpawnedNPC npc ) {
        if ( this.kit != null ) {
            this.kit.give( player, game );
            player.sendMessage( ChatColor.GREEN + "Now using kit: " + this.kit.getName() );
        } else {
            player.openMenu( new KitSelectorMenu( game ) );
        }
    }

    public static class KitSelectorMenu extends EasyMenu {

        public KitSelectorMenu( Game game ) {
            super( 36 );
            int slot = 0;
            for ( Kit kit : game.getKitManager().getKits() ) {
                if ( slot < 36 ) {// TODO: Pages
                    this.setItem( slot++, kit.getIcon(), ( e, g, p ) -> kit.give( p, g ) );
                }
            }
        }

    }

}
