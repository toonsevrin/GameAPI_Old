package com.yoshigenius.lib.serializable;

/**
 * Represents a class that can be serialized to and deserialized from a {@link java.lang.String}
 * object.
 * 
 * @author fusion
 */
public interface Serializable {
    
    /**
     * Serialize this object to a String object.
     * 
     * @return The serialized object.
     */
    public String serialize();
    
    /**
     * Deserialize this object from a string.
     * 
     * @param s
     *            The string to deserialize from.
     */
    public void deserialize( String s );
    
}
