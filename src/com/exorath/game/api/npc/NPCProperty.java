package com.exorath.game.api.npc;

import com.exorath.game.api.Property;
import org.bukkit.entity.EntityType;

/**
 * Created by too on 31/05/2015.
 */
public class NPCProperty {
    public static final Property
        NAME = Property.get("name", "Display name of this NPC", ""),
        SKIN = Property.get("skin", "Username of the skin", null),
        TYPE = Property.get("type", "Entity type of this npc", EntityType.PLAYER),
        PROTECTED = Property.get("protected", "Whether or not this npc is invulnerable", true);

}
