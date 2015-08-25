package com.exorath.game.api.action;

import org.apache.commons.lang3.Validate;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * This action adds base behaviour to the hunger events
 * @author Nick Robson
 */
public interface HungerAction {
    
    void apply( FoodLevelChangeEvent event );
    
    void apply( EntityDamageEvent event );
    
    public class Default implements HungerAction {
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {}
        
        @Override
        public void apply( EntityDamageEvent event ) {}
        
    }
    
    public class DefaultNoDamage implements HungerAction {
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {}
        
        @Override
        public void apply( EntityDamageEvent event ) {
            event.setCancelled( true );
        }
        
    }
    
    public class Disabled implements HungerAction {
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {
            event.setCancelled( true );
        }
        
        @Override
        public void apply( EntityDamageEvent event ) {
            event.setCancelled( true );
        }
        
    }
    
    public class FixedAmount implements HungerAction {
        
        private int amount;
        private boolean damage;
        
        public FixedAmount( int amount ) {
            this( amount, true );
        }
        
        public FixedAmount( int amount, boolean damage ) {
            this.amount = amount;
            this.damage = damage;
            Validate.isTrue( amount >= 0 && amount <= 20 );
        }
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {
            event.setFoodLevel( this.amount );
        }
        
        @Override
        public void apply( EntityDamageEvent event ) {
            if ( !this.damage ) {
                event.setCancelled( true );
            }
        }
        
    }
    
    public class MinimumAmount implements HungerAction {
        
        private int amount;
        
        public MinimumAmount( int amount ) {
            this.amount = amount;
            Validate.isTrue( amount >= 0 && amount <= 20 );
        }
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {
            event.setFoodLevel( Math.max( this.amount, event.getFoodLevel() ) );
        }
        
        @Override
        public void apply( EntityDamageEvent event ) {}
        
    }
    
    public class MaximumAmount implements HungerAction {
        
        private int amount;
        
        public MaximumAmount( int amount ) {
            this.amount = amount;
            Validate.isTrue( amount >= 0 && amount <= 20 );
        }
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {
            event.setFoodLevel( Math.min( this.amount, event.getFoodLevel() ) );
        }
        
        @Override
        public void apply( EntityDamageEvent event ) {}
        
    }
    
    public class BoundedAmount implements HungerAction {
        
        private final int min, max;
        
        public BoundedAmount( int a, int b ) {
            this.min = Math.min( a, b );
            this.max = Math.max( a, b );
            Validate.isTrue( this.min >= 0 && this.min <= 20 );
            Validate.isTrue( this.max >= 0 && this.max <= 20 );
        }
        
        @Override
        public void apply( FoodLevelChangeEvent event ) {
            event.setFoodLevel( Math.max( this.min, Math.min( this.max, event.getFoodLevel() ) ) );
        }
        
        @Override
        public void apply( EntityDamageEvent event ) {}
        
    }
    
}
