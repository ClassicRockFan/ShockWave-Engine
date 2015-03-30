package com.ClassicRockFan.ShockWave.engine.components.lighting;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class DirectionalLight extends BaseLight {

    public DirectionalLight(Vector3f color, float intensity) {
        super(color, intensity);

        setShader(new Shader("Forward-Directional"));
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }
}
