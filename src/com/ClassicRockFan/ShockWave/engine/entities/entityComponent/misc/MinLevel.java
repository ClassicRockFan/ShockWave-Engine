package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;

public class MinLevel extends EntityComponent {

    private int yLow;

    public MinLevel(int yLower) {
        super();
        this.yLow = yLower;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getParent().getTransform().getPos().getY() < yLow)
            getParent().getTransform().getPos().setY(yLow);
    }
}
