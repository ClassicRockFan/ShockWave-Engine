package com.ClassicRockFan.ShockWave.engine.entities.items;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Item extends Entity {

    public Item() {
        super();
    }

    public Item(String name) {
        super(name);
    }

    @Override
    public void finalizeSetup(CoreEngine engine) {
        super.finalizeSetup(engine);
    }

    public CoreEngine getEngine() {
        return super.getEngine();
    }
}
