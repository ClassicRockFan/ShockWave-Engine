package com.ClassicRockFan.ShockWave.engine.entities.items;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Item extends Entity{

    private ItemManager itemManager;


    public Item(CoreEngine engine) {
        super(engine);
        this.itemManager = engine.getItemManager();
        itemManager.registerItem(this);
        init();
    }

    public ItemManager getItemManager() {
        return itemManager;
    }


}
