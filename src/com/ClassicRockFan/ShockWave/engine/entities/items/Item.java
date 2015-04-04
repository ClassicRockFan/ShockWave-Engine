package com.ClassicRockFan.ShockWave.engine.entities.items;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Item extends Entity{

    public Item(String name) {
        super(name);
    }

    @Override
    public void init(CoreEngine engine){
        super.init(engine);
    }

    public CoreEngine getEngine() {
        return super.getEngine();
    }
}
