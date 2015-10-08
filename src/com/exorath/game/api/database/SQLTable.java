package com.exorath.game.api.database;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.exorath.game.GameAPI;

/**
 * Created by too on 31/05/2015.
 */
public class SQLTable {

    public static final String KEY = "Key";
    private String name;
    private Map<String, SQLColumn> columns = new HashMap<>();

    public SQLTable(String name) {
        this.name = name;
    }

    /**
     * loads the data from the database of the given key
     */
    public boolean load(SQLData data) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = GameAPI.getSQLManager().getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + name + " WHERE " + SQLTable.KEY + " = ? LIMIT 1");
            statement.setString(1, data.getUUID().toString());
            statement.execute();
            rs = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
        if(rs == null)
            return false;
        try {
            if (rs.next()) {
                ResultSetMetaData rsMeta = rs.getMetaData();
                for (int i = 0; i < rsMeta.getColumnCount(); i++) {
                    String columnName = rsMeta.getColumnName(i);
                    Object obj = rs.getObject(columnName);
                    if (obj == null)
                        continue;
                    data.setData(columnName, obj);
                }
            } else
                GameAPI.printConsole("SQLTable.getData with key " + data.getUUID() + " failed to load data. SQLData doesn't exist.");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Save the data to the database of the given key
     */
    public void save(SQLData data) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = GameAPI.getSQLManager().getConnection();
            statement = connection.prepareStatement("SELECT * FROM " + name + " WHERE " + SQLTable.KEY + " = ? LIMIT 1");
            statement.setString(1, data.getUUID().toString());
            statement.execute();
            rs = statement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
        if(rs == null)
            return;
        try {
            if (rs.next()) {
                ResultSetMetaData rsMeta = rs.getMetaData();
                for (int i = 0; i < rsMeta.getColumnCount(); i++) {
                    String columnName = rsMeta.getColumnName(i);
                    Object obj = rs.getObject(columnName);
                    if (obj == null)
                        continue;
                    data.setData(columnName, obj);
                }
            } else
                GameAPI.error("SQLTable.getData with key " + data.getUUID() + " failed to load data. SQLData doesn't exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update or insert data into database
     *
     * @param data SQLData you want to insert or update
     */
    public void setData(SQLData data) {
        if (rowExists(data.getUUID().toString()))
            createRow(data);
        else
            updateRow(data);
    }

    /**
     * Create a column in the database
     *
     * @param column
     */
    public void addColumn(SQLColumn column) {
        GameAPI.getSQLManager()
                .execute("ALTER TABLE " + name + " ADD " + column.getKey() + column.getType().getDataTypeStructured());
        loadColumn(column);
    }

    /**
     * Load a column in the columns hashmap
     *
     * @param column
     */
    protected void loadColumn(SQLColumn column) {
        columns.put(column.getKey(), column);
    }

    /**
     * Create a new row, this will only happen if the row key doesn't exist yet.
     *
     * @param data
     */
    public void createRow(SQLData data) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsChanged = -1;
        try {
            connection = GameAPI.getSQLManager().getConnection();
            statement = connection.prepareStatement("INSERT INTO " + name + "(" + SQLManager.getQuestionMarks(data.getData().size()) + ") VALUES (" + SQLManager.getQuestionMarks(data.getData().size()) + ")");
            int i = 1;
            for(String key : data.getData().keySet()){
                statement.setObject(i, key);
                statement.setObject(i, data.getData().size() + i);
                i++;
            }
            rowsChanged = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }

        if (rowsChanged >= 0)
            GameAPI.printConsole("Created row " + data.getUUID().toString() + " in table " + name + " successfully.");
        else
            GameAPI.printConsole("Attempt to create row " + data.getUUID().toString() + " in table " + name + " failed.");
    }

    /**
     * Update an already existing row
     *
     * @param data SQLData you want to update
     */
    public void updateRow(SQLData data) {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsChanged = -1;
        try {
            int size = data.getData().size();
            connection = GameAPI.getSQLManager().getConnection();
            statement = connection.prepareStatement("UPDATE " + name + "SET" + SQLManager.getQuestionMarksKeyValue(size) + " WHERE " + KEY + " = ?");
            int i = 1;
            for(String key : data.getData().keySet()){
                statement.setObject(i, key);
                i++;
                statement.setObject(i, data.getData().get(key));
                i++;
            }
            statement.setString(size + 1, data.getUUID().toString());
            rowsChanged = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.closeConnection(connection);
            ConnectionPool.closeStatement(statement);
        }
        if(rowsChanged >= 0)
        GameAPI.printConsole("Updated row " + data.getUUID() + " in table " + name + " successfully.");
        else
            GameAPI.error("There was an error updating row " + data.getUUID() + " in table" + name);
    }

    /**
     * Check if the row with given key exists in the table
     *
     * @param key Key that should be checked
     * @return whether or not the row exists.
     */
    public boolean rowExists(String key) {
        try {
            ResultSet rs = GameAPI.getSQLManager()
                    .execute("SELECT EXISTS(SELECT 1 FROM" + name + " WHERE " + KEY + "='" + key + "');");
            if (rs == null)
                return false;
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Add a column to the MYSQL Table
     *
     * @param columnName name this column should have
     * @param type       Type this column should have
     */
    public void addColumn(String columnName, ColumnType type) {
        GameAPI.getSQLManager().execute(
                "ALTER TABLE " + name + " ADD " + columnName + " " + type.getDataTypeStructured() + " UNIQUE;");

        GameAPI.printConsole("Added column " + columnName + " with type " + type.getDataTypeStructured() + " to table "
                + name + ".");
    }

    /**
     * Get a Map of all the columns and their names in this table
     *
     * @return A Map of all the columns and their names in this table
     */
    public Map<String, SQLColumn> getColumns() {
        return columns;
    }
}
