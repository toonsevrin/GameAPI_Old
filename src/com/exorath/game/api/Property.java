package com.exorath.game.api;

import java.util.HashMap;
import java.util.Map;

import com.yoshigenius.lib.reflect.Reflection;

public class Property {

    private static Map<String, Property> properties = new HashMap<String, Property>();

    public static Property get(String name, String desc, Object def) {
        return Property.get(name, desc, def, false);
    }

    public static Property get(String name, String desc, Object def, boolean strict) {
        return name != null && Property.properties.containsKey(name.toLowerCase())
                ? Property.properties.get(name.toLowerCase()) : new Property(name, desc, def, strict);
    }

    private final String name, desc;
    private final Object def;
    private final boolean strict;
    private boolean cloned = false;

    private Property(String name, String desc, Object def, boolean strict) {
        this.name = name;
        this.desc = desc;
        this.def = def;
        this.strict = strict;

        if (name != null)
            Property.properties.put(name.toLowerCase(), this);
    }

    public String getKey() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public Object getDefault() {
        if (cloned && def instanceof Cloneable)
            return Reflection.getMethod(def.getClass(), "clone").invoke(def);
        return def;
    }

    public boolean isStrict() {
        return strict;
    }

    public boolean isCloned() {
        return cloned;
    }

    public Property setCloned() {
        cloned = true;
        return this;
    }

}
