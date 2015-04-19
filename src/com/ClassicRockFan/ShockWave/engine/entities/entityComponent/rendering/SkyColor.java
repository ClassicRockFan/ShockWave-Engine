package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering;


import com.ClassicRockFan.ShockWave.engine.core.Time;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;

public class SkyColor extends EntityComponent {

    public SkyColor() {
        super();
    }

    @Override
    public void update(float delta) {
        getParent().getEngine().getRenderingEngine().setClearColor((float) Math.cos(Time.getTime()) * 4, (float) Math.sin(Time.getTime()) * 4, (float) Math.sin(Time.getTime()) * 4, 0.0f);
    }


}
