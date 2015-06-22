package com.exorath.game.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

import com.exorath.game.GameAPI;

/**
 * Modified by toon on 31/05/2015. Created by Nick
 */
public class SQLManager {
    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    
    private Map<String, SQLTable> tables = new HashMap<>();
    private Connection connection;
    
    public SQLManager( String host, int port, String database, String user, String password ) {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            this.host = host;
            this.port = port;
            this.database = database;
            this.user = user;
            this.password = password;
            
            this.connection = this.open();
            
            this.loadTables();
        } catch ( ClassNotFoundException exception ) {
            exception.printStackTrace();
        }
    }
    
    /**
     * Get an SQLTable, formatted as pluginName_name
     *
     * @param plugin
     *            plugin which table is used by.
     * @param tableName
     *            name of table.
     * @return Existing or new SQLTable with the formatted name.
     */
    public SQLTable getTable( Plugin plugin, String tableName ) {
        return this.getTable( plugin.getName(), tableName );
    }
    
    public SQLTable getTable( String pluginName, String tableName ) {
        String name = pluginName + "__" + tableName;
        if ( this.tables.containsKey( name ) ) {
            return this.tables.get( pluginName );
        }
        return this.addTable( name );
    }
    
    /**
     * Open new connection with remote MYSQL database
     * 
     * @return Newly created connection with remote MYSQL database
     */
    protected Connection open() {
        try {
            this.connection = DriverManager.getConnection( "jdbc:mysql://" + this.host + "/" + this.database + "?user="
                    + this.user + "&password=" + this.password );
        } catch ( SQLException ex ) {
            throw new IllegalArgumentException( "Invalid SQL server/database information", ex );
        }
        
        return this.connection;
    }
    
    /**
     * Open the connection again if closed
     */
    public void refresh() {
        if ( !this.checkConnection() ) {
            this.connection = this.open();
        }
    }
    
    /**
     * Check whether or not the connection is valid
     * 
     * @return Whether or not the connection is valid
     */
    public boolean checkConnection() {
        try {
            if ( this.connection != null && !this.connection.isClosed() ) {
                return true;
            }
        } catch ( SQLException e ) {}
        return false;
    }
    
    /**
     * Get an array of the credentials [HOST, PORT, DATABASE, USERNAME, PASSWORD]
     * 
     * @return An array of the credentials [HOST, PORT, DATABASE, USERNAME, PASSWORD]
     */
    protected String[] getCredentials() {
        return new String[] { this.host, String.valueOf( this.port ), this.database, this.user, this.password };
    }
    
    /**
     * Get the connection with the mysql database
     * 
     * @return The connection with the mysql database
     */
    protected Connection getConnection() {
        return this.connection;
    }
    
    /**
     * Load tables into tables HashMap.
     */
    private void loadTables() {
        try {
            this.tables.clear();
            ResultSet res = this.executeQuery( "SHOW TABLES FROM " + this.database );
            
            while ( res.next() ) {
                String tableName = res.getString( "Tables_in_" + this.database );
                SQLTable table = new SQLTable( tableName );
                
                ResultSet columnsRes = this.executeQuery( "select * from information_schema.columns where table_schema = '" + this.database
                        + "' and table_name = '" + tableName + "'" );
                while ( columnsRes.next() ) {
                    String columnName = columnsRes.getString( "COLUMN_NAME" ); //get data type out of column result set
                    String columnDataType = columnsRes.getString( "DATA_TYPE" ); //get data type out of column result set
                    ColumnType columnType = ColumnType.getColumnType( columnDataType ); //Column type found by data type
                    if ( columnType == null ) {
                        continue;
                    }
                    if ( columnType.isVarChar() ) {
                        int maxCharLength = columnsRes.getInt( "CHARACTER_MAXIMUM_LENGTH" ); // get max char length from varchar column
                        if ( maxCharLength != 0 ) {
                            columnType = ColumnType.getColumnType( columnDataType, maxCharLength ); // adjust columnType
                        }
                    }
                    table.loadColumn( new SQLColumn( columnName, columnType ) );
                }
                this.tables.put( tableName, table );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Create a new table in the database
     * 
     * @param name
     *            Name of the newly created table
     * @return The newly created table, null if there was an error
     */
    private SQLTable addTable( String name ) {
        this.executeQuery( "CREATE TABLE " + name + " (" + SQLTable.KEY + "varchar(64))" );
        this.loadTables();
        if ( this.tables.containsKey( name ) ) {
            GameAPI.printConsole( "Table " + name + " has been created in the mysql database." );
            return this.tables.get( name );
        }
        GameAPI.error( "Table " + name + " failed to create for some reason." );
        return null;
    }
    
    /**
     * Executes a query on the remote SQL database
     * 
     * @param query
     *            The query which has to be executed
     * @return ResultSet returned by this query, null if there was an issue.
     */
    public ResultSet executeQuery( String query ) {
        try {
            this.refresh();
            PreparedStatement statement = this.connection.prepareStatement( query );
            return statement.executeQuery();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Executes an update on the remote SQL database
     * 
     * @param update
     *            The update to be run
     * @return Number of rows changed, or -1 if unsuccessful.
     */
    public int executeUpdate( String update ) {
        try {
            this.refresh();
            PreparedStatement statement = this.connection.prepareStatement( update );
            return statement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return -1;
    }
}
