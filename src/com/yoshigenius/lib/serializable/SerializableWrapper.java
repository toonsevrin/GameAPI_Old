package com.yoshigenius.lib.serializable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Allows serialization of {@link java.lang.String}s, {@link java.lang.Boolean}s
 * and
 * {@link java.lang.Number}s.
 * 
 * @author fusion
 */
public class SerializableWrapper implements Serializable {

    private Object obj;

    public SerializableWrapper() {
        this.obj = null;
    }

    public SerializableWrapper(Number n) {
        this.obj = n;
    }

    public SerializableWrapper(String s) {
        this.obj = s;
    }

    public SerializableWrapper(Boolean b) {
        this.obj = b;
    }

    @Override
    public String serialize() {
        return this.obj.getClass().getPackage().getName() + "." + this.obj.getClass().getSimpleName()
                + Serializer.SEPARATOR_CLASS + this.obj.toString();
    }

    @Override
    public void deserialize(String s) {
        String[] d = s.split(Serializer.SEPARATOR_CLASS, 2);
        if (d.length < 2) {
            this.obj = null;
            return;
        }
        try {
            if (d[0].equals("java.lang.String")) {
                this.obj = d[1];
                return;
            }
            Class<?> c = Class.forName(d[0]);
            String n = c.getName();
            if (Character.isLowerCase(n.charAt(0))) {
                n = Character.toUpperCase(n.charAt(0)) + n.substring(1).toLowerCase();
            }
            Class<?> c2 = Class.forName(n);
            Method m = c2.getMethod("parse" + n, String.class);
            m.setAccessible(true);
            this.obj = m.invoke(null, d[1]);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            this.obj = null;
        }
    }

}
