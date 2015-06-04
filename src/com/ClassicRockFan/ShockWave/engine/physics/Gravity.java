package com.ClassicRockFan.ShockWave.engine.physics;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

import java.util.HashMap;

public class Gravity {

    private Vector3f accel;

    public Gravity(){
        this(new Vector3f(0, -9.8f, 0));
    }

    public Gravity(Vector3f accel) {
        this.accel = accel;
    }

    public void doGravity(HashMap<PhysicsComponent, Vector3f> objects){

        for(int i = 0; i < objects.size(); i++){
            float angle = objects.get(i).normalized().dot(new Vector3f(0,1,0));

            float sinAngle = (float) Math.sin(angle);

            Vector3f force = accel.mul(sinAngle);

        }
    }


}
