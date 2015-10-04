package com.exorath.game.api;

/**
 * @author toon
 * @author Nick Robson
 *         Note: Friendly fire overwrites pvp
 *         TODO: IMPLEMENT ITERACTION
 */
public class BasePlayerProperty {

    public static final Property PVP = Property.get("damage.player", "Whether or not pvp is allowed", false);//IMPLEMENTED!
    public static final Property DAMAGE_RECEIVE = Property.get("damage.receive", "Whether or not player receives damage", true);
    public static final Property DAMAGE_BY_ENTITY = Property.get("damage.by.entity", "Whether or not you take entity damage", false);//IMPLEMENTED!
    public static final Property DAMAGE_ENTITY = Property.get("damage.entity", "Whether or you deal damage to entities", false);//IMPLEMENTED!
    public static final Property BLOCK_BREAK = Property.get("block.break", "Whether or not block breaking is allowed",
            false);//IMPLEMENTED!
    public static final Property BLOCK_PLACE = Property.get("block.place", "Whether or not block placement is allowed",
            false);//IMPLEMENTED!
    // public static final Property BLOCK_INTERACT = Property.get("block.interact",
    //        "Whether or not interaction with blocks is allowed", false);
    //public static final Property ENTITY_INTERACT = Property.get("entity.interact",
    //       "Whether or not interaction with entities is allowed", false);
    public static final Property INTERACT = Property.get("interact", "Whether or not interacting is allowed", true);
    public static final Property CHAT = Property.get("chat", "Whether or not chat is allowed", true);//IMPLEMENTED
    public static final Property DROP_ITEMS = Property.get("items.drop", "Whether or not dropping is allowed", true);
    public static final Property HUNGER = Property.get("hunger", "Whether or not hunger should decay", true);

}
