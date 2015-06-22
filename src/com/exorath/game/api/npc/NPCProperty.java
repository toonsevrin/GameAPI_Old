package com.exorath.game.api.npc;

import org.bukkit.entity.Player;

import com.exorath.game.api.Property;

/**
 * @author toon
 */
public class NPCProperty {
    
    public static final Property NAME = Property.get( "name", "Display name of this NPC", "" );
    public static final Property SKIN = Property.get( "skin", "Username of the skin", null );
    public static final Property TYPE = Property.get( "type", "Entity class of this npc", Player.class );
    public static final Property PROTECTED = Property.get( "protected", "Whether or not this npc is invulnerable", true );
    
}
