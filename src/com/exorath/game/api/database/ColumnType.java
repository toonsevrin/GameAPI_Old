package com.exorath.game.api.database;

/**
 * Created by too on 31/05/2015.
 * Column types. TODO: Add name String
 */
public enum ColumnType {
    INTEGER(""),
    DOUBLE(""),
    DATE(""),
    TIME(""),
    STRING_32(""),
    STRING_64(""),
    STRING_128(""),
    STRING_256(""),
    STRING_512(""),
    STRING_1024(""),
    STRING_2048(""),
    STRING_4096("");
    private String name;
    ColumnType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
