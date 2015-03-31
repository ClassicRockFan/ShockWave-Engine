package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Transform;

public abstract class Entity {

    private Transform transform;
    private CoreEngine engine;

    public Entity(CoreEngine engine) {
        this.engine = engine;
        transform = new Transform();
    }

    public void init(){

    }

    public void load(){

    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public CoreEngine getEngine() {
        return engine;
    }
}
