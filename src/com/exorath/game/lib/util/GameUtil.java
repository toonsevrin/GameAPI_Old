package com.exorath.game.lib.util;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import com.exorath.game.api.nms.NMS;

/**
 * A utility class housing some helper methods.
 *
 * @author Nick Robson
 */
public final class GameUtil {
    
    @SuppressWarnings( "unchecked" )
    public static <S> S cast( Object object, Class<S> clazz ) {
        if ( clazz == Object.class ) {
            return (S) object;
        }
        try {
            S s = clazz.cast( object );
            return s;
        } catch ( Exception ex ) {}
        try {
            S s = (S) object;
            return s;
        } catch ( Exception ex ) {}
        if ( object instanceof Number ) {
            if ( clazz == int.class || clazz == Integer.class ) {
                return (S) (Integer) object;
            }
            if ( clazz == short.class || clazz == Short.class ) {
                return (S) (Short) object;
            }
            if ( clazz == long.class || clazz == Long.class ) {
                return (S) (Long) object;
            }
            if ( clazz == float.class || clazz == Float.class ) {
                return (S) (Float) object;
            }
            if ( clazz == double.class || clazz == Double.class ) {
                return (S) (Double) object;
            }
            if ( clazz == byte.class || clazz == Byte.class ) {
                return (S) (Byte) object;
            }
        }
        if ( object instanceof Boolean || clazz == boolean.class || clazz == Boolean.class ) {
            return (S) (Boolean) object;
        }
        if ( clazz == String.class ) {
            return (S) object.toString();
        }
        if ( Serializable.class.isAssignableFrom( clazz ) || object instanceof Serializable ) {
            return (S) (Serializable) object;
        }
        return null;
    }
    
    public static int cycle( int current, int max ) {
        return GameUtil.cycle( current, 0, max );
    }
    
    /**
     * Get next integer, starts from 0 if max reached
     * 
     * @param current
     *            current integer
     * @param min
     *            minimum integer to start from
     * @param max
     *            maximum integer. Restart from min when exceeded
     * @return next integer in cycle
     */
    public static int cycle( int current, int min, int max ) {
        current++;
        if ( current > max ) {
            current = min;
        }
        return current;
    }
    
    public static <T extends Entity> T spawn( Class<T> entity, Location loc, boolean invisible, String customName ) {
        T e = loc.getWorld().spawn( loc, entity );
        NMS.get().setInvisible( e, invisible );
        if ( customName != null ) {
            e.setCustomName( customName );
            e.setCustomNameVisible( true );
        }
        return e;
    }
}
