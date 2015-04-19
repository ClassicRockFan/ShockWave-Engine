package com.ClassicRockFan.ShockWave.engine.phyics;

import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

public class IntersectData {

    private boolean doesIntersect;
    private Vector3f direction;

    public IntersectData(boolean doesIntersect, Vector3f direction) {
        this.doesIntersect = doesIntersect;
        this.direction = direction;
    }

    public boolean isDoesIntersect() {
        return doesIntersect;
    }

    public float getDistance() {
        return direction.length();
    }

    public Vector3f getDirection() {
        return direction;
    }
}
