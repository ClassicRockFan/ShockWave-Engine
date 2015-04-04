package com.ClassicRockFan.ShockWave.engine.entities.light;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;


public class Light extends Item {


    private Vector3f color;
    private float intensity;
    private Shader shader;
    private EntityBaseLight base;

    public Light(CoreEngine engine, Vector3f color, float intensity) {
        super(engine, "light");
        engine.getRenderingEngine().addEntityLight(this);
        this.color = color;
        this.intensity = intensity;
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

    public EntityBaseLight getBase() {
        return base;
    }

    public void setBase(EntityBaseLight base) {
        this.base = base;
    }
}
