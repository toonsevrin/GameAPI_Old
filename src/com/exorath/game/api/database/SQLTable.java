package com.exorath.game.api.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import com.exorath.game.GameAPI;

/**
 * Created by too on 31/05/2015.
 */
public class SQLTable {
    public static final String KEY = "Key";
    private String name;
    private HashMap<String, SQLColumn> columns = new HashMap<String, SQLColumn>();
    
    public SQLTable( String name ) {
        this.name = name;
    }
    
    /**
     * Get the data of the given key in this database
     * 
     * @param key
     *            Key to receive data from
     * @return data of key, null if it doesn't exist.
     */
    public SQLData getData( String key ) {
        //TODO: If exists: load and return. Else: return null;
        ResultSet rs = GameAPI.getSQLManager().executeQuery( "SELECT * FROM " + this.name + " WHERE " + SQLTable.KEY + "='" + key + "' LIMIT 1" );
        SQLData data = new SQLData( key );
        try {
            if ( rs.next() ) {
                ResultSetMetaData rsMeta = rs.getMetaData();
                int columns = rsMeta.getColumnCount();
                for ( int i = 0; i < columns; i++ ) {
                    String columnName = rsMeta.getColumnName( i );
                    Object obj = rs.getObject( columnName );
                    if ( obj == null ) {
                        continue;
                    }
                    data.addData( columnName, obj );
                }
            } else {
                GameAPI.error( "SQLTable.getData with key " + key + " failed to load data. Data doesn't exist." );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        
        return data;
    }
    
    /**
     * Update or insert data into database
     * 
     * @param key
     *            Row key you want to insert or update
     * @param data
     *            Data you want to insert or update
     */
    public void setData( String key, SQLData data ) {
        if ( this.rowExists( key ) ) {
            this.createRow( key, data );
        } else {
            this.updateRow( key, data );
        }
    }
    
    /**
     * Create a column in the database
     * 
     * @param column
     */
    public void addColumn( SQLColumn column ) {
        GameAPI.getSQLManager().executeQuery( "ALTER TABLE " + this.name + " ADD " + column.getKey() + column.getType().getDataTypeStructured() );
        this.loadColumn( column );
    }
    
    /**
     * Load a column in the columns hashmap
     * 
     * @param column
     */
    protected void loadColumn( SQLColumn column ) {
        this.columns.put( column.getKey(), column );
    }
    
    /**
     * Create a new row, this will only happen if the row key doesn't exist yet.
     * 
     * @param key
     * @param data
     */
    public void createRow( String key, SQLData data ) {
        ResultSet rs = GameAPI.getSQLManager().executeQuery( "INSERT INTO " + this.name + " " + data.getValuesString() + ";" );//create new row with data
        GameAPI.printConsole( "Created row " + key + " in table " + this.name + " successfully." );
        //TODO: Add error reporting
    }
    
    /**
     * Update an already existing row
     * 
     * @param key
     *            Row you want to update.
     * @param data
     *            SQLData you want to update
     */
    public void updateRow( String key, SQLData data ) {
        StringBuilder query = new StringBuilder( "UPDATE " + this.name + " SET " );
        for ( String dataKey : data.getData().keySet() ) {
            query.append( dataKey );
            query.append( "='" );
            query.append( data.getData().get( dataKey ).toString() );
            query.append( "' " );
        }
        query.append( "WHERE " + SQLTable.KEY + "=" + "'" + key + "'" );
        
        ResultSet rs = GameAPI.getSQLManager().executeQuery( query.toString() );//update row with data
        
        GameAPI.printConsole( "Updated row " + key + " in table " + this.name + " successfully." );
        //TODO: Add error reporting
    }
    
    /**
     * Check if the row with given key exists in the table
     * 
     * @param key
     *            Key that should be checked
     * @return whether or not the row exists.
     */
    public boolean rowExists( String key ) {
        try {
            ResultSet rs = GameAPI.getSQLManager().executeQuery(
                    "SELECT EXISTS(SELECT 1 FROM" + this.name + " WHERE " + SQLTable.KEY + "='" + key + "');" );
            if ( rs == null ) {
                return false;
            }
            if ( rs.next() ) {
                return true;
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return false;
        
    }
    
    /**
     * Add a column to the MYSQL Table
     * 
     * @param columnName
     *            name this column should have
     * @param type
     *            Type this column should have
     */
    public void addColumn( String columnName, ColumnType type ) {
        GameAPI.getSQLManager().executeQuery( "ALTER TABLE " + this.name + " ADD " + columnName + " " + type.getDataTypeStructured() + " UNIQUE;" );
        
        GameAPI.printConsole( "Added column " + columnName + " with type " + type.getDataTypeStructured() + " to table " + this.name + "." );
    }
    
    /**
     * Refresh the tables. TODO: Double check if this causes issues because tables are cleared.
     */
    private void refresh() {
        GameAPI.getSQLManager().refresh();
    }
    
    /**
     * Get a HashMap of all the columns and their names in this table
     * 
     * @return A HashMap of all the columns and their names in this table
     */
    public HashMap<String, SQLColumn> getColumns() {
        return this.columns;
    }
}
