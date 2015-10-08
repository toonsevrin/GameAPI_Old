package com.exorath.game.api.database;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by TOON on 10/8/2015.
 */
public class ConnectionPool {
    private HikariDataSource hikari;
    public ConnectionPool(String address, int port, String databaseName, String username, String password){
        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);//Supports up to 3k connections simmultaniously
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", databaseName);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
    }
    public Connection getConnection() throws SQLException{
       return hikari.getConnection();
    }
    public static void closeConnection(Connection connection){
        if(connection != null)
            try {
                connection.close();
            }catch(SQLException e){e.printStackTrace();}
    }
    public static void closeStatement(PreparedStatement statement){
            if(statement != null)
                try{
                    statement.close();
                }catch(SQLException e){e.printStackTrace();}
    }
}
