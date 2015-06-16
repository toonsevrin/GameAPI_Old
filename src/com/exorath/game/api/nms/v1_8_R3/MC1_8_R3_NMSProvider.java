package com.exorath.game.api.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MethodProfiler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftOcelot;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;

import com.exorath.game.api.nms.NMSProvider;
import com.exorath.game.lib.util.GameUtil;
import com.yoshigenius.lib.reflect.Reflection;

/**
 * @author Nick Robson
 */
public class MC1_8_R3_NMSProvider implements NMSProvider {
    
    @Override
    public Class<?> getMinecraftServerClass() {
        return MinecraftServer.class;
    }
    
    @Override
    public Class<?> getBukkitServerClass() {
        return CraftServer.class;
    }
    
    @Override
    public Class<?> getBlockClass() {
        return Block.class;
    }
    
    @Override
    public Class<?> getWorldClass() {
        return World.class;
    }
    
    @Override
    public Class<?> getEntityClass() {
        return Entity.class;
    }
    
    @Override
    public Class<?> getPlayerClass() {
        return EntityPlayer.class;
    }
    
    @Override
    public Object getMinecraftServer() {
        return MinecraftServer.getServer();
    }
    
    @Override
    public Object getBukkitServer() {
        return Bukkit.getServer();
    }
    
    @Override
    public Object getBlock( org.bukkit.block.Block block ) {
        return ( (CraftWorld) block.getWorld() ).getHandle().c( new BlockPosition( block.getX(), block.getY(), block.getZ() ) );
    }
    
    @Override
    public Object getWorld( org.bukkit.World world ) {
        return ( (CraftWorld) world ).getHandle();
    }
    
    @Override
    public Object getEntity( org.bukkit.entity.Entity entity ) {
        return ( (CraftEntity) entity ).getHandle();
    }
    
    @Override
    public Object getPlayer( Player player ) {
        return ( (CraftPlayer) player ).getHandle();
    }
    
    @Override
    public Object getPacketTunnel( Player player ) {
        return ( (CraftPlayer) player ).getHandle().playerConnection;
    }
    
    @Override
    public Object getPlayerInteractManager( Player player ) {
        return ( (CraftPlayer) player ).getHandle().playerInteractManager;
    }
    
    @Override
    public Object getPlayerAbilities( Player player ) {
        return ( (CraftPlayer) player ).getHandle().abilities;
    }
    
    @Override
    public void setCanInstantBuild( Player player, boolean enabled ) {
        ( (CraftPlayer) player ).getHandle().abilities.canInstantlyBuild = enabled;
    }
    
    @Override
    public void setInvulnerable( Player player, boolean enabled ) {
        ( (CraftPlayer) player ).getHandle().abilities.isInvulnerable = enabled;
    }
    
    @Override
    public void setWalkSpeed( Player player, float f ) {
        ( (CraftPlayer) player ).getHandle().abilities.walkSpeed = f;
    }
    
    @Override
    public void setFlySpeed( Player player, float f ) {
        ( (CraftPlayer) player ).getHandle().abilities.flySpeed = f;
    }
    
    @Override
    public void setPortalCooldown( Player player, int cooldown ) {
        ( (CraftPlayer) player ).getHandle().portalCooldown = cooldown;
    }
    
    @Override
    public void setSleeping( Player player, boolean sleeping ) {
        ( (CraftPlayer) player ).getHandle().sleeping = sleeping;
    }
    
    @Override
    public void setTimeOffset( Player player, long timeOffset ) {
        ( (CraftPlayer) player ).getHandle().timeOffset = timeOffset;
    }
    
    @Override
    public void setWeather( Player player, WeatherType weather ) {
        ( (CraftPlayer) player ).getHandle().weather = weather;
    }
    
    @Override
    public void setInvisible( Entity entity, boolean invisible ) {
        ( (CraftEntity) entity ).getHandle().setInvisible( invisible );
    }
    
    @Override
    public void navigate( Creature entity, Location location, Runnable run ) {
        net.minecraft.server.v1_8_R3.EntityCreature ent = ( (CraftCreature) entity ).getHandle();
        MethodProfiler profiler = (MethodProfiler) Reflection.getField( PathfinderGoalSelector.class, "d" ).get( ent.goalSelector )
                .getObject();
        Ocelot ocelot = GameUtil.spawn( Ocelot.class, location, true, null );
        ent.setGoalTarget( ( (CraftOcelot) ocelot ).getHandle() );
        ent.goalSelector = new PathfinderGoalSelector( profiler );
        ent.goalSelector.a( new PathfinderGoalMoveTowardsTarget( ent, 1, 1 ) );
    }
    
}
