package com.exorath.game.api;

import java.util.HashMap;
import java.util.Map;

public class Property {
    
    private static Map<String, Property> properties = new HashMap<>();
    
    public static Property get( String name, String desc, Object def ) {
        return name != null && Property.properties.containsKey( name.toLowerCase() ) ? Property.properties.get( name.toLowerCase() ) : new Property(
                name, desc, def );
    }
    
    private final String name, desc;
    private final Object def;
    
    public Property( String name, String desc, Object def ) {
        this.name = name;
        this.desc = desc;
        this.def = def;
        
        if ( name != null ) {
            Property.properties.put( name.toLowerCase(), this );
        }
    }
    
    public Property( String name, String desc ) {
        this( name, desc, null );
    }
    
    public String getKey() {
        return this.name;
    }
    
    public String getDescription() {
        return this.desc;
    }
    
    public Object getDefault() {
        return this.def;
    }
    
}
