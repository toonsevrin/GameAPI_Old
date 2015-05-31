package com.yoshigenius.lib.util;

import com.yoshigenius.lib.serializable.Serializable;
import com.yoshigenius.lib.serializable.Serializer;

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
        if ( object instanceof String && Serializable.class.isAssignableFrom( clazz ) ) {
            Serializable ser = Serializer.deserialize( object.toString() );
            if ( ser != null ) {
                return (S) ser;
            }
        }
        if ( clazz == String.class || object instanceof String ) {
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
        if ( min >= max ) {
            return current;
        }
        // e.g. 7, 2, 6
        // e.g. 1, 2, 6
        // max-min = 4
        // max-min+1 = 5
        while ( current < min ) {
            current += max - min + 1; // 1 -> 6
        }
        while ( current > max ) {
            current -= max - min + 1; // 7 -> 2
        }
        return current;
    }
}
