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
public class MissingNMSProvider implements NMSProvider {
    
    @Override
    public Class<?> getMinecraftServerClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Class<?> getBukkitServerClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Class<?> getBlockClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Class<?> getWorldClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Class<?> getEntityClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Class<?> getPlayerClass() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getMinecraftServer() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getBukkitServer() {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getBlock( Block block ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getWorld( World world ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getEntity( Entity entity ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getPlayer( Player player ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getPacketTunnel( Player player ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getPlayerInteractManager( Player player ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public Object getPlayerAbilities( Player player ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setCanInstantBuild( Player player, boolean enabled ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setInvulnerable( Player player, boolean enabled ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setWalkSpeed( Player player, float f ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setFlySpeed( Player player, float f ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setPortalCooldown( Player player, int cooldown ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setSleeping( Player player, boolean sleeping ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setTimeOffset( Player player, long timeOffset ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setWeather( Player player, WeatherType weather ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void setInvisible( Entity e, boolean invisible ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
    @Override
    public void navigate( LivingEntity entity, Location location, double speed, Runnable callback ) {
        throw new UnsupportedOperationException( "There is no NMSProvider for this version of Minecraft." );
    }
    
}
