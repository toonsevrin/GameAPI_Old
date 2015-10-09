package com.exorath.game.api.npc.types;

import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.NPC;
import com.exorath.game.api.npc.NPCEquipment;

/**
 * @author Nick Robson
 */
public abstract class AbstractNPC implements NPC {

    private String name;
    private String skin;
    private NPCEquipment equipment;
    private Properties properties = new Properties();

    public AbstractNPC(String name) {
        this(name, null, new NPCEquipment());
    }

    public AbstractNPC(String name, String skin) {
        this(name, skin, new NPCEquipment());
    }

    public AbstractNPC(String name, NPCEquipment equipment) {
        this(name, null, equipment);
    }

    public AbstractNPC(String name, String skin, NPCEquipment equipment) {
        this.name = name;
        this.skin = skin;
        this.equipment = equipment;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSkin() {
        return this.skin;
    }

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public NPCEquipment getEquipment() {
        return this.equipment;
    }

}
