package me.nickrobson.lib.reflect;

import java.lang.reflect.Field;

/**
 * @author Nick Robson
 */
public class SafeField {

    public static SafeField NULL = new SafeField(null) {

        @Override
        public <T> SafeObject<T> get(Object owner) {
            return new SafeObject<T>(null);
        }

        @Override
        public void set(Object owner, Object value) {
        }
    };

    protected static SafeField get(Class<?> owner, String field) {
        try {
            return new SafeField(owner.getField(field));
        } catch (NullPointerException | SecurityException | NoSuchFieldException ex) {
            return SafeField.NULL;
        }
    }

    private final Field field;

    private SafeField(Field field) {
        this.field = field;
    }

    public Field getHandle() {
        return this.field;
    }

    @SuppressWarnings("unchecked")
    public <T> SafeObject<T> get(Object owner) {
        try {
            this.field.setAccessible(true);
            return new SafeObject<T>((T) this.field.get(owner));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    public void set(Object owner, Object value) {
        try {
            this.field.set(owner, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
        }
    }
}
