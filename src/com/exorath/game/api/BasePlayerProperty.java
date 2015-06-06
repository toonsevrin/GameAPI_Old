package com.exorath.game.api;

import com.exorath.game.api.behaviour.HungerBehaviour;

/**
 * Created by too on 27/05/2015.
 */
public class BasePlayerProperty {
    
    public static final Property
            PVP = Property.get( "pvp", "Whether or not pvp is allowed", false ),
            BLOCK_BREAK = Property.get( "block.break", "Wether or not block breaking is allowed", false ),
            BLOCK_PLACE = Property.get( "block.place", "Whether or not block placement is allowed", false ),
            BLOCK_INTERACT = Property.get( "block.interact", "Whether or not interaction with blocks is allowed", false ),
            ENTITY_INTERACT = Property.get( "entity.interact", "Whether or not interaction with entities is allowed", false ),
            CHAT = Property.get( "chat", "Whether or not chat is allowed", true ),
            HUNGER = Property.get( "hunger", "Whether or not players get hungry", HungerBehaviour.DEFAULT );
    
}
