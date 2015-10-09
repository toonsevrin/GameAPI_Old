package com.exorath.game.api.database;

import com.exorath.game.GameAPI;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Modified by toon on 31/05/2015. Created by Nick
 */
public class SQLManager {
    private ConnectionPool connectionPool;
    private String address;
    private int port;
    private String database;
    private String username;
    private String password;

    private Map<String, SQLTable> tables = new HashMap<>();

    public SQLManager(String address, int port, String database, String username, String password) {
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connectionPool = new ConnectionPool(address, port, database, username, password);

        this.loadTables();
    }

    /**
     * Get an SQLTable, formatted as pluginName_name
     *
     * @param plugin    plugin which table is used by.
     * @param tableName name of table.
     * @return Existing or new SQLTable with the formatted name.
     */
    public SQLTable getTable(Plugin plugin, String tableName) {
        return this.getTable(plugin.getName(), tableName);
    }

    public SQLTable getTable(String pluginName, String tableName) {
        return getTable(pluginName + "_" + tableName);

    }

    public SQLTable getTable(String name) {
        if (this.tables.containsKey(name)) {
            return this.tables.get(name);
        }
        return this.addTable(name);
    }

    /**
     * Load tables into tables HashMap.
     */
    private void loadTables() {
        this.tables.clear();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet res = null;//this.execute("SHOW TABLES FROM " + this.database);
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement("SHOW TABLES FROM " + database);
            res = statement.executeQuery();
            while (res.next()) {
                getColumn(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
    }

    private void getColumn(ResultSet res) throws SQLException{
        String tableName = res.getString("Tables_in_" + this.database);
        SQLTable table = new SQLTable(tableName);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement("SELECT * FROM information_schema.columns WHERE table_schema = ? and table_name = ?");
            statement.setString(1, database);
            statement.setString(2, tableName);
            ResultSet columnsRes = statement.executeQuery();
            while (columnsRes.next()) {
                String columnName = columnsRes.getString("COLUMN_NAME");//get data type out of column result set
                String columnDataType = columnsRes.getString("DATA_TYPE");//get data type out of column result set
                ColumnType columnType = ColumnType.getColumnType(columnDataType);//Column type found by data type
                if (columnType == null) {
                    continue;
                }
                if (columnType.isVarChar()) {
                    int maxCharLength = columnsRes.getInt("CHARACTER_MAXIMUM_LENGTH");// get max char length from varchar column
                    if (maxCharLength != 0) {
                        columnType = ColumnType.getColumnType(columnDataType, maxCharLength);// adjust columnType
                    }
                }
                table.loadColumn(new SQLColumn(columnName, columnType));
            }
            this.tables.put(tableName, table);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
    }

    /**
     * Create a new table in the database
     *
     * @param name Name of the newly created table
     * @return The newly created table, null if there was an error
     */
    private SQLTable addTable(String name) {
        if (name == null)
            return null;
        this.execute("CREATE TABLE " + name + " (varchar(64))");
        this.loadTables();
        if (this.tables.containsKey(name)) {
            GameAPI.printConsole("Table " + name + " has been created in the mysql database.");
            return this.tables.get(name);
        }
        GameAPI.error("Table " + name + " failed to create for some reason.");
        return null;
    }

    /**
     * Executes a query on the remote SQL database
     *
     * @param query The query which has to be executed
     * @return ResultSet returned by this query, null if there was an issue.
     */
    public ResultSet execute(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();
            return statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
        return null;
    }

    public int executeUpdate(String query) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(query);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
        return 0;
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public static String getQuestionMarks(int amount) {
        if (amount <= 0)
            return " ";
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < amount; i++)
            sb.append("? ");
        return sb.toString();
    }

    public static String getQuestionMarksKeyValue(int amount) {
        if (amount <= 0)
            return " ";
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < amount; i++)
            sb.append("? = ? ,");
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
