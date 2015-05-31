package com.exorath.game.api.npc.navigator;

import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.GNPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Created by too on 31/05/2015.
 */
public class Navigator {
    private GNPC npc;
    private boolean navigating = false;
    private Properties properties;
    public Navigator(GNPC npc){
        this.npc = npc;
        properties = new Properties();
    }

    /**
     * Stop any active navigation
     */
    public void stopNavigating(){
        navigating = false;
    }
    /**
     * Whether or not this npc is moving towards a target
     * @return Whether or not this npc is moving towards a target
     */
    public boolean isNavigating(){
        return navigating;
    }

    /**
     * Make the npc navigate to a location
     * @param loc Location to navigate too
     */
    public void setTarget(Location loc){
        navigating = true;
    }

    /**
     * Make the npc target an entity
     * @param entity Entity to navigate to, will not stop untill reached.
     */
    public void setTarget(Entity entity){
        navigating = true;
    }

    /**
     * Get the navigators properties
     * @return properties of this Navigator
     */
    public Properties getProperties(){
        return properties;
    }

    /**
     * Set walking/flying speed of npc
     * @param speed Walking/flying speed of npc
     */
    public void setSpeed(double speed){
        properties.set(NavigatorProperty.SPEED, speed);
    }

    /**
     * Get walking/flying speed of npc
     * @return Walking/flying speed of npc
     */
    public double getSpeed(){
        return properties.as(NavigatorProperty.SPEED, Double.class);
    }

}
