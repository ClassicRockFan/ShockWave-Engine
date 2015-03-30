package com.ClassicRockFan.ShockWave.engine.components.lighting;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

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
