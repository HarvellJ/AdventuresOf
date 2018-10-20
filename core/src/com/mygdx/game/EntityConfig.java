package com.mygdx.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.game.InventoryItem.ItemTypeID;
import com.mygdx.game.Entity;

public class EntityConfig {
	
    private Array<ItemTypeID> inventory;
    private String itemTypeID;

    public static enum EntityProperties{
        ENTITY_HEALTH_POINTS,
        ENTITY_ATTACK_POINTS,
        ENTITY_DEFENSE_POINTS,
        ENTITY_HIT_DAMAGE_TOTAL,
        ENTITY_XP_REWARD,
        ENTITY_GP_REWARD,
        NONE
    }

    EntityConfig(){
        inventory = new Array<ItemTypeID>();
    }

    EntityConfig(EntityConfig config){
        itemTypeID = config.getItemTypeID();
        inventory = new Array<ItemTypeID>();
        inventory.addAll(config.getInventory());
    }
    
    public String getItemTypeID() {
        return itemTypeID;
    }

    public void setItemTypeID(String itemTypeID) {
        this.itemTypeID = itemTypeID;
    }
    
    public Array<ItemTypeID> getInventory() {
        return inventory;
    }

    public void setInventory(Array<ItemTypeID> inventory) {
        this.inventory = inventory;
    }
}
