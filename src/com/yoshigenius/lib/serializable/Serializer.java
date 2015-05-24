package com.yoshigenius.lib.serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Serializer {
    
    public static final String SEPARATOR_CLASS = "@";
    public static final String SEPARATOR_INFO = ";";
    
    public static String serialize( Serializable s ) {
        return s.getClass().getPackage().getName() + "." + s.getClass().getSimpleName() + Serializer.SEPARATOR_CLASS
                + s.serialize();
    }
    
    public static Serializable deserialize( String s ) {
        String[] d = s.split( Serializer.SEPARATOR_CLASS, 2 );
        if ( d.length < 2 || d[ 1 ] == null || d[ 1 ].isEmpty() ) {
            return null;
        }
        try {
            Class<?> clazz = Class.forName( d[ 0 ] );
            if ( !Serializable.class.isAssignableFrom( clazz ) ) {
                return null;
            }
            Class<? extends Serializable> ser = clazz.asSubclass( Serializable.class );
            Constructor<? extends Serializable> constr = ser.getConstructor();
            Serializable inst = null;
            if ( constr != null ) {
                constr.setAccessible( true );
                inst = constr.newInstance();
                inst.deserialize( d[ 1 ] );
            } else {
                inst = ser.newInstance();
            }
            return inst;
        } catch ( ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            e.printStackTrace();
        }
        return null;
    }
    
}
