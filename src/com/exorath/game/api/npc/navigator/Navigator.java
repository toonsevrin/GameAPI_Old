package com.exorath.game.api.npc.navigator;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.GNPC;

/**
 * Created by too on 31/05/2015.
 */
public class Navigator {
    
    private GNPC npc;
    private boolean navigating = false;
    private Properties properties;
    
    public Navigator( GNPC npc ) {
        this.npc = npc;
        this.properties = new Properties();
    }
    
    /**
     * Stop any active navigation
     */
    public void stopNavigating() {
        this.navigating = false;
    }
    
    /**
     * Whether or not this npc is moving towards a target
     * 
     * @return Whether or not this npc is moving towards a target
     */
    public boolean isNavigating() {
        return this.navigating;
    }
    
    /**
     * Make the npc navigate to a location
     * 
     * @param loc
     *            Location to navigate too
     */
    public void setTarget( Location loc ) {
        this.navigating = true;
    }
    
    /**
     * Make the npc target an entity
     * 
     * @param entity
     *            Entity to navigate to, will not stop untill reached.
     */
    public void setTarget( Entity entity ) {
        this.navigating = true;
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
     * Set walking/flying speed of npc
     * 
     * @param speed
     *            Walking/flying speed of npc
     */
    public void setSpeed( double speed ) {
        this.properties.set( NavigatorProperty.SPEED, speed );
    }
    
    /**
     * Get walking/flying speed of npc
     * 
     * @return Walking/flying speed of npc
     */
    public double getSpeed() {
        return this.properties.as( NavigatorProperty.SPEED, Double.class );
    }
    
}
