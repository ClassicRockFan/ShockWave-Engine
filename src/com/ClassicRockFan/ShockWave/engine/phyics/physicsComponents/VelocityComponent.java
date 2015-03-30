package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameComponent;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.calculations.Kinematics;

public class VelocityComponent extends GameComponent {

    /*
    This class basically allows you to set an object in motion
    without going through the physics engine.
     */

    Vector3f velocity;

    public VelocityComponent(Vector3f velocity) {
        this.velocity = velocity;
    }

    @Override
    public void update(float delta) {
        getTransform().getPos().set(getTransform().getPos().add(Kinematics.calcDistance(velocity, new Vector3f(0, 0, 0), delta)));
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    ;
}
