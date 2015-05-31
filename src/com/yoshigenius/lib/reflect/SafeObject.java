package com.yoshigenius.lib.reflect;

/**
 * @author Nick Robson
 */
public class SafeObject<T> {
    
    private T o;
    
    public SafeObject( T o ) {
        this.o = o;
    }
    
    public T getObject() {
        return this.o;
    }
    
    public SafeField getField( String name ) {
        if ( this.o == null ) {
            return SafeField.NULL;
        }
        return Reflection.getField( this.o.getClass(), name );
    }
    
    public SafeMethod getMethod( String name, Class<?>... params ) {
        if ( this.o == null ) {
            return SafeMethod.NULL;
        }
        return Reflection.getMethod( this.o.getClass(), name, params );
    }
    
    public SafeConstructor getConstructor( Class<?>... params ) {
        if ( this.o == null ) {
            return SafeConstructor.NULL;
        }
        return Reflection.getConstructor( this.o.getClass(), params );
    }
    
}
