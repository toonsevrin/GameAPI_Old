package com.exorath.game.api.hud;

/**
 * Created by TOON on 8/9/2015.
 */
public class HUDPriority {
    public static final Priority HIGHEST = Priority.HIGHEST;
    public static final Priority HIGH = Priority.HIGH;
    public static final Priority MEDIUM = Priority.MEDIUM;
    public static final Priority GAME_API = Priority.GAME_API;
    public static final Priority LOW = Priority.LOW;
    public static final Priority LOWEST = Priority.LOWEST;

    private Priority priority;
    private int subPriority;
    public HUDPriority(Priority priority, int subPriority){
        this.priority = priority;
        this.subPriority = subPriority;
    }
    public HUDPriority(Priority priority){
        this.priority = priority;
    }
    public Priority getPriority(){
        return priority;
    }
    public int getSubPriority(){
        return subPriority;
    }
    public HUDPriority clone(){
        return new HUDPriority(priority, subPriority);
    }

    public enum Priority{
        HIGHEST(5),
        HIGH(4),
        MEDIUM(3),
        GAME_API(2),
        LOW(1),
        LOWEST(0);

        private int priority;
        Priority(int priority){
            this.priority = priority;
        }
        public HUDPriority get(){
            return new HUDPriority(this, 0);
        }
        public HUDPriority get(int subPriority){
            return new HUDPriority(this, subPriority);
        }
        public int getPriority(){
            return priority;
        }
    }
}
