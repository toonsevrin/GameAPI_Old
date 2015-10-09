package me.nickrobson.lib.reflect;

import me.nickrobson.lib.util.GameUtil;

public class SafeObject<T> {

    public static SafeObject<Object> get(Object object) {
        return new SafeObject<Object>(object);
    }

    private final T o;

    public SafeObject(T o) {
        this.o = o;
    }

    public T getObject() {
        return this.o;
    }

    public <S> S getObjectAs(Class<S> desired) {
        T o = this.getObject();
        return GameUtil.cast(o, desired);
    }

    public SafeField getField(String name) {
        if (this.o == null) {
            return SafeField.NULL;
        }
        return Reflection.getField(this.o.getClass(), name);
    }

    public SafeMethod getMethod(String name, Class<?>... params) {
        if (this.o == null) {
            return SafeMethod.NULL;
        }
        return Reflection.getMethod(this.o.getClass(), name, params);
    }

    public SafeConstructor getConstructor(Class<?>... params) {
        if (this.o == null) {
            return SafeConstructor.NULL;
        }
        return Reflection.getConstructor(this.o.getClass(), params);
    }

}
