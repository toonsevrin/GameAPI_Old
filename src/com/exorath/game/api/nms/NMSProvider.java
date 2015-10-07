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

    Class<?> getMinecraftServerClass();

    Class<?> getBukkitServerClass();

    Class<?> getBukkitPlayerClass();

    default Package getNMSPackage() {
        return getMinecraftServerClass().getPackage();
    }

    default Package getCraftBukkitPackage() {
        return getBukkitServerClass().getPackage();
    }

    default Class<?> getClass(String name) {
        try {
            return Class.forName(getNMSPackage().getName() + "." + name);
        } catch (Throwable t) {
            return null;
        }
    }

    Class<?> getBlockClass();

    Class<?> getWorldClass();

    Class<?> getWorldServerClass();

    Class<?> getEntityClass();

    Class<?> getPlayerClass();

    Class<?> getGameProfileClass();

    Class<?> getPlayerInteractManagerClass();

    Object getMinecraftServer();

    Object getBukkitServer();

    Object getBlock(Block block);

    Object getWorld(World world);

    Object createPlayerInteractManager(World world);

    Object createNetworkManager();

    Object createNetServerHandler(Object nms_DedicatedServer, Object nms_NetworkManager, Object nms_Player);

    Object getEntity(Entity entity);

    Object getPlayer(Player player);

    Object getPacketTunnel(Player player);

    Object getPlayerInteractManager(Player player);

    Object getPlayerAbilities(Player player);

    void setCanInstantBuild(Player player, boolean enabled);

    void setInvulnerable(Player player, boolean enabled);

    void setWalkSpeed(Player player, float f);

    void setFlySpeed(Player player, float f);

    void setPortalCooldown(Player player, int cooldown);

    void setSleeping(Player player, boolean sleeping);

    void setTimeOffset(Player player, long timeOffset);

    void setWeather(Player player, WeatherType weather);

    void setInvisible(Entity e, boolean invisible);

    void navigate(LivingEntity entity, Location location, double speed);

    void revive(Player p);

    Player getBukkitPlayer(Object nms_Player);

}
