package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;

public class PhysicsEngineComponent extends EntityComponent {

    private PhysicsEngine physicsEngine;

    public PhysicsEngineComponent(PhysicsEngine physicsEngine) {
        super("EntityComponent");
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void update(float delta){
        physicsEngine.simulate(delta);
        physicsEngine.handleCollisions(delta);
    }

    public PhysicsEngine getPhysicsEngine() {return physicsEngine;}
}
