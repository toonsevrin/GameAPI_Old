package com.exorath.game.api;

import java.util.HashMap;
import java.util.Map;

import com.exorath.game.lib.util.GameUtil;

/**
 * Created by too on 23/05/2015.
 * Properties are used to load and save certain settings within a session.
 * Note that we are not using child properties in code atm. This is because the Game object tree
 * does the same thing.
 */

public class Properties {
    
    private Map<Property, Object> properties = new HashMap<Property, Object>();
    
    /**
     * @param property
     *            The property
     * @param def
     *            Default value to return if key not found
     * @return Returns the value out of properties or def if value not found.
     */
    public Object get( Property property, Object def ) {
        if ( this.properties.containsKey( property ) ) {
            return this.properties.get( property );
        } else {
            return def;
        }
    }
    
    /**
     * Gets a property's value as a specific type
     * 
     * @param property
     *            The property
     * @param clazz
     *            The type to get the value as
     * @param <T>
     *            The type
     * @return The property's value as the given type.
     */
    @SuppressWarnings( "unchecked" )
    public <T> T as( Property property, Class<T> clazz ) {
        Object o = this.get( property, clazz );
        T t = GameUtil.cast( o, clazz );
        return t == null ? (T) property.getDefault() : t;
    }
    
    /**
     * Set a property to a value
     *
     * @param property
     *            The property to store under
     * @param value
     *            The value you want to store in the key.
     */
    public void set( Property property, Object value ) {
        this.properties.put( property, value );
    }
}
