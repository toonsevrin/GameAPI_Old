package com.yoshigenius.lib.reflect;

/**
 * @author Nick Robson
 */
public class Reflection {
    
    public static SafeField getField( Class<?> owner, String field ) {
        return SafeField.get( owner, field );
    }
    
    public static SafeMethod getMethod( Class<?> owner, String method, Class<?>... params ) {
        return SafeMethod.get( owner, method, params );
    }
    
    public static SafeConstructor getConstructor( Class<?> owner, Class<?>... params ) {
        return SafeConstructor.get( owner, params );
    }
    
}
