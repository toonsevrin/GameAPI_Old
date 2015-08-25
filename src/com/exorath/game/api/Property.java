package com.exorath.game.api;

import java.util.HashMap;
import java.util.Map;

public class Property {

    private static Map<String, Property> properties = new HashMap<String, Property>();

    public static Property get(String name, String desc, Object def) {
        return Property.get(name, desc, def, false);
    }

    public static Property get(String name, String desc, Object def, boolean strict) {
        return name != null && Property.properties.containsKey(name.toLowerCase()) ? Property.properties.get(name.toLowerCase()) : new Property(
                name, desc, def, strict);
    }

    private final String name, desc;
    private final Object def;
    private final boolean strict;

    private Property(String name, String desc, Object def, boolean strict) {
        this.name = name;
        this.desc = desc;
        this.def = def;
        this.strict = strict;

        if (name != null)
            Property.properties.put(name.toLowerCase(), this);
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

    public boolean isStrict() {
        return this.strict;
    }

}
