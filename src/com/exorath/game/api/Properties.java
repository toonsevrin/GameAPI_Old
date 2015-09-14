package com.exorath.game.api;

import java.util.HashMap;
import java.util.Map;

import com.yoshigenius.lib.storage.SimpleMap;
import com.yoshigenius.lib.util.GameUtil;

/**
 * Created by too on 23/05/2015.
 * Properties are used to load and save certain settings within a session.
 * Note that we are not using child properties in code atm. This is because the
 * Game object tree
 * does the same thing.
 */

public class Properties {

    private static final Map<Class<?>, Class<?>> PRIMITIVES = new HashMap<Class<?>, Class<?>>() {

        {
            put(byte.class, Byte.class);
            put(char.class, Character.class);
            put(short.class, Short.class);
            put(int.class, Integer.class);
            put(long.class, Long.class);
            put(float.class, Float.class);
            put(double.class, Double.class);
            put(boolean.class, Boolean.class);
        }
    };

    private SimpleMap<Property, Object> properties = new SimpleMap<Property, Object>();

    /**
     * @param property
     *            The property
     * @param def
     *            Default value to return if key not found
     * @return Returns the value out of properties or def if value not found.
     */
    public Object get(Property property, Object def) {
        return properties.getOrDefault(property, def);
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
    @SuppressWarnings("unchecked")
    public <T> T as( Property property, Class<T> clazz) {
        Object o = get(property, null);
        T t = GameUtil.cast(o, clazz);
        return t == null ? (T) property.getDefault() : primitive(t, clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T primitive(T t, Class<T> clazz) {
        if (t == null && PRIMITIVES.containsKey(clazz)) {
            Class<?> equiv = PRIMITIVES.get(clazz);
            if (Number.class.isAssignableFrom(equiv))
                return (T) (Number) 0;
            else if (equiv == Character.class)
                return (T) (Character) '\0';
            else if (equiv == Boolean.class)
                return (T) (Boolean) false;
        }
        return t;
    }

    /**
     * Set a property to a value
     *
     * @param property
     *            The property to store under
     * @param value
     *            The value you want to store in the key.
     */
    public void set(Property property, Object value) {
        if (property.isStrict() && !property.getDefault().getClass().isAssignableFrom(value.getClass()))
            throw new IllegalArgumentException(String.format(
                    "Property %s is strict, and provided value of type %s does not match required type of %s.",
                    property.getKey(),
                    value.getClass().getSimpleName(),
                    property.getDefault().getClass().getSimpleName()));
        properties.put(property, value);
    }

}
