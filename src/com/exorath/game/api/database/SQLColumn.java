package com.exorath.game.api.database;

/**
 * Created by too on 1/06/2015.
 */
public class SQLColumn {

    private String key;
    private com.exorath.game.api.database.ColumnType type;

    public SQLColumn(String key, com.exorath.game.api.database.ColumnType type) {
        this.key = key;
        this.type = type;
    }

    /**
     * Get the name of this column
     *
     * @return The name of this column
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the type of this column
     *
     * @return The type of this column
     */
    public ColumnType getType() {
        return type;
    }
}
