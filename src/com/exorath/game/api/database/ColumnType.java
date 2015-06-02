package com.exorath.game.api.database;

/**
 * Created by too on 31/05/2015.
 * Column types. TODO: Add name String
 */
public enum ColumnType {
    INTEGER("int"),
    DOUBLE(""),
    DATE("date"),
    DATE_TIME("datetime"),
    TIME("time"),
    STRING_32(32),
    STRING_64(64),
    STRING_128(128),
    STRING_256(256),
    STRING_512(512),
    STRING_1024(1024),
    STRING_2048(2048),
    STRING_4096(4096);

    private String name;
    private int maxCharLength;

    ColumnType(String name){
        this.name = name;
    }
    ColumnType(int maxCharLength){
        this.name = "varchar";
        this.maxCharLength = maxCharLength;
    }
    public String getName(){
        return name;
    }
    public int getMaxCharLength(){return maxCharLength;}
    public static ColumnType getColumnType(String name){
        for(ColumnType type : ColumnType.values()){
            if(type.getName().equals(name)) return type;
        }
        return null;
    }
    public static ColumnType getColumnType(int maxCharLength){
        for(ColumnType type : ColumnType.values()){
            if(type.getMaxCharLength() == 0) return null;
            if(type.getMaxCharLength() == maxCharLength) return type;
        }
        return null;
    }
    public boolean isVarChar(){
        return name.equals("varchar");
    }
}
