package com.exorath.game.api.database;
import com.exorath.game.GameAPI;
import com.zaxxer.hikari.HikariConfig;
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
        GameAPI.printConsole("jdbc:mysql://" + address + ":" + port + "/" + databaseName);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + address + ":" + port + "/" + databaseName);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setMaxLifetime(28740000);

        hikari = new HikariDataSource(config);
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
