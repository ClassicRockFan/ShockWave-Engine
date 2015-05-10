package com.ClassicRockFan.ShockWave.engine.physics.calculations;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;

public class Kinematics {

    public static Vector3f calcDistance(Vector3f velocityInitial, Vector3f acceleration, float time, PhysicsComponent object) {
        float deltaX = velocityInitial.getX() * time + (float) 0.5 * (acceleration.getX() * time * time);
        float deltaY = velocityInitial.getY() * time + (float) 0.5 * (acceleration.getY() * time * time);
        float deltaZ = velocityInitial.getZ() * time + (float) 0.5 * (acceleration.getZ() * time * time);

        return new Vector3f(deltaX + object.getPosition().getX(), deltaY + object.getPosition().getY(), deltaZ + object.getPosition().getZ());
    }

    public static Vector3f calcDistance(Vector3f velocityInitial, Vector3f acceleration, float time) {
        float deltaX = velocityInitial.getX() * time + (float) 0.5 * (acceleration.getX() * time * time);
        float deltaY = velocityInitial.getY() * time + (float) 0.5 * (acceleration.getY() * time * time);
        float deltaZ = velocityInitial.getZ() * time + (float) 0.5 * (acceleration.getZ() * time * time);

        return new Vector3f(deltaX, deltaY, deltaZ);
    }

    public static Vector3f calcAcceleration(Vector3f velocityInitial, Vector3f velocityFinal, float time) {
        float deltaX = (velocityFinal.getX() - velocityInitial.getX()) / time;
        float deltaY = (velocityFinal.getY() - velocityInitial.getY()) / time;
        float deltaZ = (velocityFinal.getZ() - velocityInitial.getZ()) / time;

        return new Vector3f(deltaX, deltaY, deltaZ);
    }

    public static Vector3f calcVelocity(Vector3f initialVelocity, Vector3f acceleration, float time) {
        float deltaX = initialVelocity.getX() + acceleration.getX() * time;
        float deltaY = initialVelocity.getY() + acceleration.getY() * time;
        float deltaZ = initialVelocity.getX() + acceleration.getZ() * time;

        return new Vector3f(deltaX, deltaY, deltaZ);
    }
}
