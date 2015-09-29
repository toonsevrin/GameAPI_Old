package me.nickrobson.lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Nick Robson
 */
public class SafeConstructor {

    public static final SafeConstructor NULL = new SafeConstructor(null) {

        @Override
        public <T> SafeObject<T> construct(Object... args) {
            return new SafeObject<T>(null);
        }
    };

    public static SafeConstructor get(Class<?> owner, Class<?>... params) {
        try {
            return new SafeConstructor(owner.getConstructor(params));
        } catch (NullPointerException | NoSuchMethodException | SecurityException e) {
            return SafeConstructor.NULL;
        }
    }

    private final Constructor<?> constr;

    public SafeConstructor(Constructor<?> constr) {
        this.constr = constr;
    }

    public Constructor<?> getHandle() {
        return this.constr;
    }

    @SuppressWarnings("unchecked")
    public <T> SafeObject<T> construct(Object... args) {
        try {
            return new SafeObject<T>((T) this.constr.newInstance(args));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException ex) {
            return null;
        }
    }

}
