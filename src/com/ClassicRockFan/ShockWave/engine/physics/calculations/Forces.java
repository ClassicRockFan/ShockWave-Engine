package com.ClassicRockFan.ShockWave.engine.physics.calculations;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

public class Forces {

    public static Vector3f calcAcceleration(Vector3f force, float mass) {
        float deltaX = force.getX() / mass;
        float deltaY = force.getY() / mass;
        float deltaZ = force.getZ() / mass;

        return new Vector3f(deltaX, deltaY, deltaZ);
    }

    public static Vector3f calcForce(Vector3f acceleration, float mass) {
        float deltaX = acceleration.getX() * mass;
        float deltaY = acceleration.getY() * mass;
        float deltaZ = acceleration.getZ() * mass;

        return new Vector3f(deltaX, deltaY, deltaZ);
    }

    public static float calcMass(Vector3f acceleration, Vector3f force) {
        return force.getX() / acceleration.getX();
    }

}
