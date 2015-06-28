package com.exorath.game.api.maps;

import com.exorath.game.api.npc.NPC;
import com.exorath.game.api.player.GamePlayer;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.Set;

/**
 * Created by TOON on 6/27/2015.
 */
public abstract class Map {
    /* Game methods TODO: Replace the x,y,z doubles with a vector :)*/
    public abstract void teleport(GamePlayer player, double x, double y, double z);
    public abstract void spawnNPC(NPC npc);
    public abstract Arrow spawnArrow(double x, double y, double z, Vector direction, float speed, float spread);
    public abstract Block getBlock(double x, double y, double z);
    public abstract Item dropItem(double x, double y, double z, ItemStack itemStack);
    public abstract Collection<NPC> getNPCs();
    public abstract Collection<GamePlayer> getNearbyPlayers(double x, double y, double z, double radiusX, double radiusY, double radiusZ);
    public abstract void playEffect(Effect effect, double x, double y, double z); //Will be implemented in our custom effect lib
    public abstract void playSound(double x, double y, double z, Sound sound, float volume, float pitch);

    /* Map methods */
    public abstract void reset();


}
