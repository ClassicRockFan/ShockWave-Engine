package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;

public class PhysicsObjectComponent extends EntityComponent {

    PhysicsObject physicsObject;

    public PhysicsObjectComponent(PhysicsObject physicsObject) {
        super("physicsObjectComponent");
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
