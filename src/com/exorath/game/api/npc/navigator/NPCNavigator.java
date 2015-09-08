package com.exorath.game.api.npc.navigator;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import com.exorath.game.api.Properties;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.npc.SpawnedNPC;
import com.yoshigenius.lib.util.GameUtil;

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
        return this.npc;
    }

    /**
     * Makes the NPC start walking, if the target location is valid.
     */
    public void navigate() {
        this.stop();
        this.navigationTask = GameUtil.scheduleTimer(() -> {
            NMS.get().navigate(this.npc.getBukkitEntity(), this.getTargetLocation(), this.getSpeed());
        } , 5, 5);
    }

    /**
     * Gets the target's current location.
     * 
     * @return The target's location.
     */
    public Location getTargetLocation() {
        if (this.targetEnt != null && this.targetEnt.isValid() && !this.targetEnt.isDead()) {
            return this.targetEnt.getLocation();
        } else if (this.targetEnt != null) {
            this.targetEnt.remove();
            this.targetEnt = null;
        }
        return this.targetLoc;
    }

    /**
     * Stop any active navigation
     */
    public void stop() {
        if (this.navigationTask == null) {
            this.navigationTask.cancel();
            this.navigationTask = null;
        }
    }

    /**
     * Whether or not this NPC is moving towards a target
     * 
     * @return Whether or not this NPC is moving towards a target
     */
    public boolean isNavigating() {
        return this.navigationTask != null;
    }

    /**
     * Make the NPC navigate to a location
     * 
     * @param loc
     *            Location to navigate to
     */
    public void setTarget(Location loc) {
        this.targetEnt = null;
        this.targetLoc = loc;
    }

    /**
     * Make the NPC target an entity
     * 
     * @param entity
     *            Entity to navigate to, will not stop until reached.
     */
    public void setTarget(Entity entity) {
        this.targetEnt = entity;
        this.targetLoc = null;
    }

    /**
     * Get the navigators properties
     * 
     * @return properties of this Navigator
     */
    public Properties getProperties() {
        return this.properties;
    }

    /**
     * Set walking/flying speed of the NPC
     * 
     * @param speed
     *            Walking/flying speed of the NPC
     */
    public void setSpeed(double speed) {
        this.properties.set(NPCNavigatorProperty.SPEED, speed);
    }

    /**
     * Get walking/flying speed of the NPC
     * 
     * @return Walking/flying speed of the NPC
     */
    public double getSpeed() {
        return this.properties.as(NPCNavigatorProperty.SPEED, double.class);
    }

}
