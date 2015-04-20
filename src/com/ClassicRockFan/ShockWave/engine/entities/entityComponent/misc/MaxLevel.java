package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;

public class MaxLevel extends EntityComponent{

    private int yMax;

    public MaxLevel(int yMax) {
        super();
        this.yMax = yMax;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getParent().getTransform().getPos().getY() > yMax)
            getParent().getTransform().getPos().setY(yMax);
    }
}
