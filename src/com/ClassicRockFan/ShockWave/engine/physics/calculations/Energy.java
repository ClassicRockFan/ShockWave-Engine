package com.ClassicRockFan.ShockWave.engine.physics.calculations;

public class Energy {

    public static float calcGPE(float mass, float gravity, float height) {
        return mass * gravity * height;
    }

    public static float calcKE(float mass, float velocity) {
        return (float) (0.5) * mass * velocity * velocity;
    }
}
