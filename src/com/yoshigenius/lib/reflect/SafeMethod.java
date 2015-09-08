package com.yoshigenius.lib.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Nick Robson
 */
public class SafeMethod {

    public static SafeMethod NULL = new SafeMethod(null) {

        @Override
        public <T> SafeObject<T> invoke(Object owner, Object... args) {
            return new SafeObject<T>(null);
        }
    };

    protected static SafeMethod get(Class<?> owner, String method, Class<?>... params) {
        try {
            return new SafeMethod(owner.getMethod(method, params));
        } catch (NullPointerException | NoSuchMethodException | SecurityException ex) {
            return SafeMethod.NULL;
        }
    }

    private final Method method;

    private SafeMethod(Method method) {
        this.method = method;
    }

    public Method getHandle() {
        return this.method;
    }

    @SuppressWarnings("unchecked")
    public <T> SafeObject<T> invoke(Object owner, Object... args) {
        try {
            this.method.setAccessible(true);
            return new SafeObject<T>((T) this.method.invoke(owner, args));
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

}
