package com.ClassicRockFan.ShockWave.engine.entities.items;

import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.EntityManager;

public class Item extends Entity{

    private EntityManager entityManager;
    private CoreEngine engine;

    public Item(CoreEngine engine, String name) {
        super(name);
        this.engine = engine;
        this.entityManager = engine.getEntityManager();
        entityManager.registerItem(this);
        init();
    }

    @Override
    public void init(){
        Logging.printLog("Loading an item named: " + this.getName());
    }

    public EntityManager getItemManager() {
        return entityManager;
    }

    public CoreEngine getEngine() {
        return engine;
    }
}
