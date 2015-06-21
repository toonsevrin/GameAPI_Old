package com.exorath.game.api.nms.v1_8_R2;

import net.minecraft.server.v1_8_R2.Block;
import net.minecraft.server.v1_8_R2.BlockPosition;
import net.minecraft.server.v1_8_R2.EntityInsentient;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.exorath.game.api.nms.NMSProvider;

/**
 * @author Nick Robson
 */
public class MC1_8_R2_NMSProvider implements NMSProvider {
    
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
    public void navigate( LivingEntity entity, Location location, double speed, Runnable callback ) {
        Object handle = this.getEntity( entity );
        if ( handle instanceof EntityInsentient ) {
            EntityInsentient ent = (EntityInsentient) handle;
            ent.getNavigation().a( location.getX(), location.getY(), location.getZ(), speed );
        }
    }
}
