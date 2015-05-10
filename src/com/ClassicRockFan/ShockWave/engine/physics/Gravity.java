package com.ClassicRockFan.ShockWave.engine.physics;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

import java.util.ArrayList;

public class Gravity {

    private Vector3f accel;

    public Gravity(){
        this(new Vector3f(0, -9.8f, 0));
    }

    public Gravity(Vector3f accel) {
        this.accel = accel;
    }

    public void doGravity(ArrayList<PhysicsComponent> objects){
        for(PhysicsComponent component: objects){
            component.addForce(accel.mul(component.getMass()));
        }
    }


}
