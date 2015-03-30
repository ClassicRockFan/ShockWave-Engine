package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;

public class PhysicsObjectComponent extends GameComponent {

    PhysicsObject physicsObject;

    public PhysicsObjectComponent(PhysicsObject physicsObject) {
        this.physicsObject = physicsObject;
    }

    @Override
    public void update(float delta){
        getTransform().getPos().set(physicsObject.getPosition());
    }

    public PhysicsObject getPhysicsObject() {
        return physicsObject;
    }
}
