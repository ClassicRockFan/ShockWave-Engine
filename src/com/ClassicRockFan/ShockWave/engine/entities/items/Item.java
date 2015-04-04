package com.ClassicRockFan.ShockWave.engine.entities.items;

import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Item extends Entity{

    private ItemManager itemManager;
    private CoreEngine engine;

    public Item(CoreEngine engine, String name) {
        super(name);
        this.engine = engine;
        this.itemManager = engine.getItemManager();
        itemManager.registerItem(this);
        init();
    }

    @Override
    public void init(){
        Logging.printLog("Loading an item named: " + this.getName());
    }

    public ItemManager getItemManager() {
        return itemManager;
    }


}
