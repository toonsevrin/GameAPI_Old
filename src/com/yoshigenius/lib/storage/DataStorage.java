package com.yoshigenius.lib.storage;

import com.yoshigenius.lib.util.GameUtil;

/**
 * A utility class providing easy saving and loading of objects to and from
 * memory.
 *
 * @author Nick Robson
 */
public class DataStorage {

    private final SimpleMap<String, Object> settings = new SimpleMap<>();

    /**
     * Gets the value of the input setting.
     *
     * @param key
     *            The setting's ID.
     * @param clazz
     *            The {@code java.lang.Class} to attempt to retrieve it as.
     * @param <T>
     *            The type expected of the setting.
     * @return The value.
     */
    public <T> T get(String key, Class<T> clazz) {
        return GameUtil.cast(this.settings.get(key), clazz);
    }

    /**
     * Gets a {@link SimpleMap} of the settings.
     *
     * @return The settings.
     */
    public SimpleMap<String, Object> getSettings() {
        return this.settings;
    }

    /**
     * Sets the value of the input setting.
     *
     * @param key
     *            The setting's ID.
     * @param value
     *            The value to set it to.
     */
    public void set(String key, Object value) {
        this.settings.put(key, value);
    }

}
