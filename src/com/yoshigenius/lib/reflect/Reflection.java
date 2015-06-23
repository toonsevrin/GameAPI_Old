package com.yoshigenius.lib.reflect;

/**
 * A utility class providing easy access to the rest of the library uniformly.
 * 
 * @author Nick Robson
 */
public class Reflection {
    
    /**
     * Gets the {@link SafeObject} version of a provided object for use in this library.
     * 
     * @param object
     *            The object.
     * @return The SafeObject version.
     */
    public static <T> SafeObject<T> getObject( T object ) {
        return new SafeObject<T>( object );
    }
    
    /**
     * Gets a Class' constructor with provided arguments.
     * 
     * @param owner
     *            The class.
     * @param params
     *            The parameters' classes.
     * @return The SafeConstructor if found, or SafeConstructor.NULL if not.
     */
    public static SafeConstructor getConstructor( Class<?> owner, Class<?>... params ) {
        return SafeConstructor.get( owner, params );
    }
    
    /**
     * Gets a Class' method with provided name and arguments.
     * 
     * @param owner
     *            The class.
     * @param method
     *            The method's name.
     * @param params
     *            The method's parameters.
     * @return The SafeMethod if found, or SafeMethod.NULL if not.
     */
    public static SafeMethod getMethod( Class<?> owner, String method, Class<?>... params ) {
        return SafeMethod.get( owner, method, params );
    }
    
    /**
     * Gets a Class' field with provided name.
     * 
     * @param owner
     *            The class.
     * @param field
     *            The field's name.
     * @return The SafeField if found, or SafeField.NULL if not.
     */
    public static SafeField getField( Class<?> owner, String field ) {
        return SafeField.get( owner, field );
    }
    
    /**
     * Gets a Class's field through the path style of com.example.package.ClassName#fieldName
     * 
     * @param fieldPath
     *            The field's path (as described above).
     * @return The SafeField if found, or SafeField.NULL if not.
     */
    public static SafeField getField( String fieldPath ) {
        String[] s = fieldPath.split( "#", 2 );
        if ( s.length == 2 ) {
            try {
                Class<?> c = Class.forName( s[ 0 ] );
                String field = s[ 1 ];
                
                return Reflection.getField( c, field );
            } catch ( ClassNotFoundException e ) {}
        }
        return SafeField.NULL;
    }
    
}
