package com.yoshigenius.lib.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class SQLManager {
    
    // SQL STUFFS
    
    private Map<String, SQLTable> tables = new HashMap<>();
    private Connection connection = null;
    private String host;
    protected String database;
    private String user;
    private String password;
    private int port;
    
    public SQLManager( String host, int port, String database, String user, String password ) {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            
            this.host = host;
            this.port = port;
            this.database = database;
            this.user = user;
            this.password = password;
            // open connection
            this.connection = this.open();
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }
    
    public Connection open() {
        
        try {
            this.connection = DriverManager.getConnection( "jdbc:mysql://" + this.host + "/" + this.database + "?user="
                    + this.user + "&password=" + this.password );
        } catch ( SQLException ex ) {
            throw new IllegalArgumentException( "Invalid SQL server/database information", ex );
        }
        
        return this.connection;
    }
    
    public void refresh() {
        if ( !this.checkConnection() ) {
            this.connection = this.open();
        }
    }
    
    public boolean checkConnection() {
        try {
            if ( this.connection != null && !this.connection.isClosed() ) {
                return true;
            }
        } catch ( SQLException e ) {
        }
        return false;
    }
    
    protected String[] getCredentials() {
        return new String[] { this.host, String.valueOf( this.port ), this.database, this.user, this.password };
    }
    
    protected Connection getConnection() {
        return this.connection;
    }
    
    public SQLTable getTable( String name ) {
        if ( name == null ) {
            return null;
        }
        this.updateTables();
        return this.tables.get( name );
    }
    
    private void updateTables() {
        try {
            this.tables.clear();
            PreparedStatement statement = this.connection.prepareStatement( "SHOW TABLES FROM " + this.database );
            ResultSet res = statement.executeQuery();
            while ( res.next() ) {
                String tableName = res.getString( "Tables_in_" + this.database );
                
                this.tables.put( tableName, new SQLTable( this, tableName ) );
                
            }
        } catch ( SQLException ex ) {
            ex.printStackTrace();
        }
    }
    
}
