package com.ClassicRockFan.ShockWave.engine.phyics.bounding;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;

public class Plane {

    float dist;
    private Vector3f normal;

    public Plane(Vector3f normal, float dist) {
        this.normal = normal;
        this.dist = dist;
    }

    public Plane normalized() {
        float magnitude = normal.length();

        return new Plane(new Vector3f(normal.getX() / magnitude, normal.getY() / magnitude, normal.getZ() / magnitude), dist / magnitude);
    }

    public IntersectData interspectSphere(BoundingSphere sphere) {
        float distanceFromCenter = Math.abs(normal.dot(sphere.getCenter()) + dist);
        float distanceFromSphere = distanceFromCenter - sphere.getRadius();
        return new IntersectData(distanceFromSphere < 0, normal.mul(distanceFromSphere));
    }

    //Getters and Setters
    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }
}
