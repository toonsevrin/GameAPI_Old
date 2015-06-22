package com.exorath.game.api.nms;

import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * @author Nick Robson
 */
public interface NMSProvider {
    
    public Class<?> getMinecraftServerClass();
    
    public Class<?> getBukkitServerClass();
    
    public default Package getNMSPackage() {
        return this.getMinecraftServerClass().getPackage();
    }
    
    public default Package getCraftBukkitPackage() {
        return this.getBukkitServerClass().getPackage();
    }
    
    public Class<?> getBlockClass();
    
    public Class<?> getWorldClass();
    
    public Class<?> getEntityClass();
    
    public Class<?> getPlayerClass();
    
    public Object getMinecraftServer();
    
    public Object getBukkitServer();
    
    public Object getBlock( Block block );
    
    public Object getWorld( World world );
    
    public Object getEntity( Entity entity );
    
    public Object getPlayer( Player player );
    
    public Object getPacketTunnel( Player player );
    
    public Object getPlayerInteractManager( Player player );
    
    public Object getPlayerAbilities( Player player );
    
    public void setCanInstantBuild( Player player, boolean enabled );
    
    public void setInvulnerable( Player player, boolean enabled );
    
    public void setWalkSpeed( Player player, float f );
    
    public void setFlySpeed( Player player, float f );
    
    public void setPortalCooldown( Player player, int cooldown );
    
    public void setSleeping( Player player, boolean sleeping );
    
    public void setTimeOffset( Player player, long timeOffset );
    
    public void setWeather( Player player, WeatherType weather );
    
    public void setInvisible( Entity e, boolean invisible );
    
    public void navigate( LivingEntity entity, Location location, double speed, Runnable callback );
    
}
