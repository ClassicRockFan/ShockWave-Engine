package com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement;

import com.base.engine.core.Vector3f;

import java.util.HashMap;

public abstract class MappedValues {

    HashMap<String, Float> floatHashmap;
    HashMap<String, Vector3f> vectorHashmap;

    public MappedValues() {
        floatHashmap = new HashMap<String, Float>();
        vectorHashmap = new HashMap<String, Vector3f>();
    }

    public void addVector(String name, Vector3f vector) {
        vectorHashmap.put(name, vector);
    }

    public void addFloat(String name, Float value) {
        floatHashmap.put(name, value);
    }

    public Vector3f getVector3f(String name) {
        Vector3f result = vectorHashmap.get(name);
        if (result != null)
            return result;
        return new Vector3f(0, 0, 0);
    }

    public float getFloat(String name) {
        Float result = floatHashmap.get(name);
        if (result != null)
            return result;
        return 0;
    }
}
