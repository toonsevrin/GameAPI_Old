package com.exorath.game.api.player;

import org.bukkit.GameMode;

import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default player property keys.
 */
public class PlayerProperty extends BasePlayerProperty {
    
    public static final Property PREFIX = Property.get( "prefix", "Prefix for members, will be used on various places", "" );
    public static final Property GAMEMODE = Property.get( "gamemode", "Default members gamemode", GameMode.SURVIVAL );
    public static final Property NAME_TAG_VISIBILITY = Property.get( "nametags", "Whether or not members name tag should be visible", true );
    
}
