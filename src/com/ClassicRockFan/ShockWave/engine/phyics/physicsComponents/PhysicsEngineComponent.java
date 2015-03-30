package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;

public class PhysicsEngineComponent extends GameComponent {

    private PhysicsEngine physicsEngine;

    public PhysicsEngineComponent(PhysicsEngine physicsEngine) {
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void update(float delta){
        physicsEngine.simulate(delta);
        physicsEngine.handleCollisions(delta);
    }

    public PhysicsEngine getPhysicsEngine() {return physicsEngine;}
}
