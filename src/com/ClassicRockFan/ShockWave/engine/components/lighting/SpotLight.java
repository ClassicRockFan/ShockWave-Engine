package com.ClassicRockFan.ShockWave.engine.components.lighting;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.rendering.Attenuation;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class SpotLight extends PointLight {

    private float cutoff;

    public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float coneRadius) {
        super(color, intensity, attenuation);
        this.cutoff = coneRadius;

        setShader(new Shader("Forward-Spot"));
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRot().getForward();
    }

    public float getConeRadius() {
        return cutoff;
    }

    public void setConeRadius(float coneRadius) {
        this.cutoff = coneRadius;
    }
}
