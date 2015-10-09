package com.exorath.game.api;

/**
 * @author toon
 * @author Nick Robson
 *         Note: Friendly fire overwrites pvp
 *         TODO: IMPLEMENT ITERACTION
 */
public class BasePlayerProperty {

    public static final Property PVP = Property.get("player.damage.player", "Whether or not pvp is allowed", false);//IMPLEMENTED!
    public static final Property DAMAGE_RECEIVE = Property.get("player.damage.receive", "Whether or not player receives damage", true);
    public static final Property DAMAGE_BY_ENTITY = Property.get("player.damage.by.entity", "Whether or not you take entity damage", false);//IMPLEMENTED!
    public static final Property DAMAGE_ENTITY = Property.get("player.damage.entity", "Whether or you deal damage to entities", false);//IMPLEMENTED!
    public static final Property BLOCK_BREAK = Property.get("player.block.break", "Whether or not block breaking is allowed",
            false);
    public static final Property BLOCK_PLACE = Property.get("player.block.place", "Whether or not block placement is allowed",
            false);
    // public static final Property BLOCK_INTERACT = Property.get("player.interact.block",
    //        "Whether or not interaction with blocks is allowed", false);
    //public static final Property ENTITY_INTERACT = Property.get("player.interact.entity",
    //       "Whether or not interaction with entities is allowed", false);
    public static final Property INTERACT = Property.get("player.interact", "Whether or not interacting is allowed", true);
    public static final Property CHAT = Property.get("player.chat", "Whether or not chat is allowed", true);//IMPLEMENTED
    public static final Property DROP_ITEMS = Property.get("player.items.drop", "Whether or not dropping is allowed", true);
    public static final Property HUNGER = Property.get("player.hunger", "Whether or not hunger should decay", true);

}
