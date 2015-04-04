package com.ClassicRockFan.ShockWave.engine.entities.light;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.BaseLightEntity;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;


public class Light extends Entity {
    private Vector3f color;
    private float intensity;
    private Shader shader;
    private BaseLightEntity base;

    public Light(Vector3f color, float intensity) {
        super("light");
        this.color = color;
        this.intensity = intensity;
    }

    @Override
    public void init(CoreEngine engine) {
        super.init(engine);
    }

    @Override
    public void load(){
        super.load();
        super.getEngine().getRenderingEngine().addLight(this);
    }

    public Shader getShader() {
        return shader;
    }

    //Getters and Setters below
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public BaseLightEntity getBase() {
        return base;
    }

    public void setBase(BaseLightEntity base) {
        this.base = base;
    }
}
