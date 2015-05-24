package com.yoshigenius.lib.storage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SimpleMap<K, V> extends HashMap<K, V> {
    
    public static class Cell<T1, T2> {
        
        private final T1 key;
        private final T2 value;
        
        public Cell( T1 key, T2 value ) {
            this.key = key;
            this.value = value;
        }
        
        public T1 key() {
            return this.key;
        }
        
        public T2 value() {
            return this.value;
        }
        
    }
    
    private static final long serialVersionUID = 606261482726593523L;
    private final List<Cell<K, V>> cells = new LinkedList<>();
    
    public SimpleMap() {
        super();
    }
    
    @SafeVarargs
    public SimpleMap( Cell<K, V>... cells ) {
        for ( Cell<K, V> cell : cells ) {
            if ( cell != null ) {
                this.cells.add( cell );
                this.put( cell.key(), cell.value(), false );
            }
        }
    }
    
    public List<Cell<K, V>> getCells() {
        List<Cell<K, V>> list = new LinkedList<>();
        for ( K key : this.keySet() ) {
            list.add( new SimpleMap.Cell<K, V>( key, this.get( key ) ) );
        }
        return list;
    }
    
    public V add( Cell<K, V> cell ) {
        this.cells.add( cell );
        return this.put( cell.key(), cell.value(), false );
    }
    
    @Override
    public V put( K key, V value ) {
        return this.put( key, value, true );
    }
    
    public V put( K key, V value, boolean store ) {
        if ( store ) {
            this.cells.add( new Cell<K, V>( key, value ) );
        }
        return super.put( key, value );
    }
    
}
