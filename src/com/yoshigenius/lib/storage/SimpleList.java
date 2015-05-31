package com.yoshigenius.lib.storage;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Nick Robson
 */
public class SimpleList<E> extends LinkedList<E> {
    
    public static class Cell<T> {
        
        private final T element;
        
        public Cell( T element ) {
            this.element = element;
        }
        
        public T element() {
            return this.element;
        }
        
    }
    
    private static final long serialVersionUID = 7741646554260168329L;
    
    private final Random random = new Random();
    
    public SimpleList() {
        super();
    }
    
    @SafeVarargs
    public SimpleList( Cell<E>... cells ) {
        for ( Cell<E> cell : cells ) {
            if ( cell != null ) {
                this.add( cell.element() );
            }
        }
    }
    
    public SimpleList( Collection<E> values ) {
        for ( E e : values ) {
            this.add( e );
        }
    }
    
    @SuppressWarnings( "unchecked" )
    public E getRandom( E... excludes ) {
        List<E> exc = Arrays.asList( excludes );
        E ret = null;
        while ( ret == null || exc.contains( ret ) ) {
            ret = this.get( this.random.nextInt( this.size() ) );
        }
        return ret;
    }
    
    @Override
    public SimpleList<E> clone() {
        SimpleList<E> list = new SimpleList<>();
        list.addAll( this );
        return list;
    }
    
}
