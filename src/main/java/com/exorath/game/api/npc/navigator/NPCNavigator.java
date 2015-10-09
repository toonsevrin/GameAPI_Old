package com.exorath.game.api.npc.navigator;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import com.exorath.game.api.Properties;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.npc.SpawnedNPC;

import me.nickrobson.lib.util.GameUtil;

/**
 * @author toon
 * @author Nick Robson
 */
public class NPCNavigator {

    private final SpawnedNPC npc;
    private Properties properties = new Properties();

    private Entity targetEnt;
    private Location targetLoc;

    private BukkitTask navigationTask = null;

    public NPCNavigator(SpawnedNPC npc) {
        this.npc = npc;
    }

    /**
     * Gets the NPC this navigator is for.
     *
     * @return The NPC.
     */
    public SpawnedNPC getNPC() {
        return npc;
    }

    /**
     * Makes the NPC start walking, if the target location is valid.
     */
    public void navigate() {
        stop();
        navigationTask = GameUtil.scheduleTimer(() -> {
            NMS.get().navigate(npc.getBukkitEntity(), getTargetLocation(), getSpeed());
        } , 5, 5);
    }

    /**
     * Gets the target's current location.
     *
     * @return The target's location.
     */
    public Location getTargetLocation() {
        if (targetEnt != null && targetEnt.isValid() && !targetEnt.isDead())
            return targetEnt.getLocation();
        else if (targetEnt != null) {
            targetEnt.remove();
            targetEnt = null;
        }
        return targetLoc;
    }

    /**
     * Stop any active navigation
     */
    public void stop() {
        if (navigationTask != null) {
            navigationTask.cancel();
            navigationTask = null;
        }
    }

    /**
     * Whether or not this NPC is moving towards a target
     *
     * @return Whether or not this NPC is moving towards a target
     */
    public boolean isNavigating() {
        return navigationTask != null;
    }

    /**
     * Make the NPC navigate to a location
     *
     * @param loc
     *            Location to navigate to
     */
    public void setTarget(Location loc) {
        targetEnt = null;
        targetLoc = loc;
    }

    /**
     * Make the NPC target an entity
     *
     * @param entity
     *            Entity to navigate to, will not stop until reached.
     */
    public void setTarget(Entity entity) {
        targetEnt = entity;
        targetLoc = null;
    }

    /**
     * Get the navigators properties
     *
     * @return properties of this Navigator
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * Set walking/flying speed of the NPC
     *
     * @param speed
     *            Walking/flying speed of the NPC
     */
    public void setSpeed(double speed) {
        properties.set(NPCNavigatorProperty.SPEED, speed);
    }

    /**
     * Get walking/flying speed of the NPC
     *
     * @return Walking/flying speed of the NPC
     */
    public double getSpeed() {
        return properties.as(NPCNavigatorProperty.SPEED, double.class);
    }

}
