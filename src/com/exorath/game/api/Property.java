package com.exorath.game.api;

public class Property {
    
    public static Property get( String name, String desc, Object def ) {
        return new Property( name, desc, def );
    }
    
    private final String name, desc;
    private final Object def;
    
    public Property( String name, String desc, Object def ) {
        this.name = name;
        this.desc = desc;
        this.def = def;
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
