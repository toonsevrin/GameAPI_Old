package com.exorath.game.api.maps;

import com.exorath.game.GameAPI;
import com.exorath.game.api.npc.NPC;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.util.Schematic;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by TOON on 6/27/2015.
 */
public class SchematicMap extends Map{
    private World world; //This is a world shared between all schematic maps

    private Schematic schematic;
    private Location minLoc; //Minloc in the Schematics world (This will be auto-assigned with the MapManager)
    private Set<NPC> npcs = new HashSet<NPC>();

    public SchematicMap(World world){
        this.world = world;
    }
    @Override
    public void teleport(GamePlayer player, double x, double y, double z) {
        player.getBukkitPlayer().teleport(getMinLoc().add(x, y, z));
    }
    @Override
    public void spawnNPC(NPC npc) {
        npcs.add(npc);
        //TODO: spawn the npc at the right location.
    }
    @Override
    public Arrow spawnArrow(double x, double y, double z, Vector direction, float speed, float spread) {
        return world.spawnArrow(getMinLoc().add(x, y, z), direction, speed, spread);
    }
    @Override
    public Block getBlock(double x, double y, double z) {
        return world.getBlockAt(getMinLoc().add(x, y, z));
    }
    @Override
    public Item dropItem(double x, double y, double z, ItemStack itemStack) {
        return world.dropItem(getMinLoc().add(x, y, z), itemStack);
    }
    @Override
    public Collection<NPC> getNPCs() {
        return null;
    }
    @Override
    public Collection<GamePlayer> getNearbyPlayers(double x, double y, double z, double radiusX, double radiusY, double radiusZ) {
        return null; //TODO: Go through players in the game and check if they are close
    }
    @Override
    public void playEffect(Effect effect, double x, double y, double z) {

    }
    @Override
    public void playSound(double x, double y, double z, Sound sound, float volume, float pitch) {
        world.playSound(minLoc.add(x, y, z), sound, volume, pitch);
    }


    @Override
    public void reset(){

    }


    public Location getMinLoc(){
        return minLoc.clone();
    }
}
