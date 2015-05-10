package com.ClassicRockFan.ShockWave.engine.physics.calculations;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

public class Momentum {

    public static Vector3f calcDeltaVelocity(Vector3f force, float time, float mass) {
        return force.mul(time).div(mass);
    }

    public static Vector3f calcDisplacement(float k, Vector3f force) {
        return force.div(k);
    }

}
