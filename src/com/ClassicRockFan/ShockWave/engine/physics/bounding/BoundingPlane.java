package com.ClassicRockFan.ShockWave.engine.physics.bounding;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;

public class BoundingPlane extends Collider{

    float dist;
    private Vector3f normal;

    public BoundingPlane(Vector3f normal, float dist) {
        super(Collider.TYPE_PLANE);
        this.normal = normal;
        this.dist = dist;
    }

    public BoundingPlane normalized() {
        float magnitude = normal.length();

        return new BoundingPlane(new Vector3f(normal.getX() / magnitude, normal.getY() / magnitude, normal.getZ() / magnitude), dist / magnitude);
    }

    public IntersectData intersectSphere(BoundingSphere sphere) {
        float distanceFromCenter = Math.abs(normal.dot(sphere.getCenter()) + dist);
        float distanceFromSphere = distanceFromCenter - sphere.getRadius();
        return new IntersectData(distanceFromSphere < 0, normal.mul(distanceFromSphere));
    }

    public IntersectData interesectPlane(BoundingPlane plane){
        return new IntersectData(plane.getNormal().equals(getNormal()), plane.getNormal().cross(getNormal()));
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
