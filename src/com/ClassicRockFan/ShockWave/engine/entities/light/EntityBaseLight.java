package com.ClassicRockFan.ShockWave.engine.entities.light;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class EntityBaseLight extends EntityComponent {

    private Vector3f color;
    private float intensity;
    private Shader shader;
    private EntityBaseLight base;

    public EntityBaseLight(Vector3f color, float intensity) {
        super("light");
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
