package com.ClassicRockFan.ShockWave.engine.entities.light.lights;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class DirectionalLightEntity extends Light {

    public DirectionalLightEntity(CoreEngine engine, Vector3f color, float intensity) {
        super(color, intensity);

        setShader(new Shader("Forward-Directional"));
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }
}
