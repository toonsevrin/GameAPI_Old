package com.yoshigenius.lib.sql;

public final class SQLColumn {
    
    private final SQLTable parent;
    private final String name, type, key, def, extra;
    private final boolean nullable;
    
    SQLColumn( SQLTable parent, String name, String type, boolean nullable, String key, String def, String extra ) {
        this.parent = parent;
        this.name = name;
        this.type = type;
        this.nullable = nullable;
        this.key = key;
        this.def = def;
        this.extra = extra;
    }
    
    public SQLTable getParent() {
        return this.parent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getType() {
        return this.type;
    }
    
    public boolean isNullable() {
        return this.nullable;
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getDefault() {
        return this.def;
    }
    
    public String getExtra() {
        return this.extra;
    }
    
}
