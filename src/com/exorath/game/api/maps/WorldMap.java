package com.exorath.game.api.maps;

import com.exorath.game.api.npc.NPC;
import com.exorath.game.api.player.GamePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.Collection;

/**
 * Created by TOON on 6/27/2015.
 */
public class WorldMap extends Map {
    private File world;

    @Override
    public void teleport(GamePlayer player, double x, double y, double z) {

    }
    @Override
    public void spawnNPC(NPC npc) {

    }
    @Override
    public Arrow spawnArrow(double x, double y, double z, Vector direction, float speed, float spread) {
        return null;
    }
    @Override
    public Block getBlock(double x, double y, double z) {
        return null;
    }
    @Override
    public Item dropItem(double x, double y, double z, ItemStack itemStack) {
        return null;
    }
    @Override
    public Collection<NPC> getNPCs() {
        return null;
    }
    @Override
    public Collection<GamePlayer> getNearbyPlayers(double x, double y, double z, double radiusX, double radiusY, double radiusZ) {
        return null;
    }
    @Override
    public void playEffect(Effect effect, double x, double y, double z) {

    }
    @Override
    public void playSound(double x, double y, double z, Sound sound, float volume, float pitch) {

    }

    @Override
    public void reset(){

    }
}
