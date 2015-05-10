package com.ClassicRockFan.ShockWave.engine.physics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;

public class PhysicsObjectComponent extends EntityComponent {

    PhysicsComponent physicsComponent;

    public PhysicsObjectComponent(PhysicsComponent physicsComponent) {
        super("physicsObjectComponent");
        this.physicsComponent = physicsComponent;
    }

    @Override
    public void update(float delta) {
        getTransform().getPos().set(physicsComponent.getPosition());
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }
}
