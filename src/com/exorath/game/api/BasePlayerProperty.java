package com.exorath.game.api;

/**
 * @author toon
 * @author Nick Robson
 */
public class BasePlayerProperty {

    public static final Property PVP = Property.get("pvp", "Whether or not pvp is allowed", false);
    public static final Property BLOCK_BREAK = Property.get("block.break", "Wether or not block breaking is allowed",
            false);
    public static final Property BLOCK_PLACE = Property.get("block.place", "Whether or not block placement is allowed",
            false);
    public static final Property BLOCK_INTERACT = Property.get("block.interact",
            "Whether or not interaction with blocks is allowed", false);
    public static final Property ENTITY_INTERACT = Property.get("entity.interact",
            "Whether or not interaction with entities is allowed", false);
    public static final Property CHAT = Property.get("chat", "Whether or not chat is allowed", true);

}
