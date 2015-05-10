package com.ClassicRockFan.ShockWave.engine.physics.bounding;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;

public class AABB extends Collider {

    private Vector3f minExtents;
    private Vector3f maxExtents;

    public AABB(Vector3f minExtents, Vector3f maxExtents) {
        super(TYPE_AABB);
        this.minExtents = minExtents;
        this.maxExtents = maxExtents;
    }

    public IntersectData intersectAABB(AABB other) {
        Vector3f distance1 = other.getMinExtents().sub(maxExtents);
        Vector3f distance2 = other.getMaxExtents().sub(minExtents);
        Vector3f distances = distance1.max(distance2);

        float maxDistance = distances.maxVal();

        return new IntersectData(maxDistance < 0, distances);
    }


    public IntersectData intersectPlane(BoundingPlane boundingPlane){
        // Get the Extense vector
        Vector3f E = (getMaxExtents().sub(getMinExtents())).div(2.0f);

        // Get the center of the Box
        Vector3f center = getMinExtents().add(E);

        Vector3f N = boundingPlane.getNormal();

        // Dot Product between the plane normal and the center of the Axis Aligned Box
        // using absolute values
        float fRadius = Math.abs(N.getX()*E.getX()) + Math.abs(N.getY() * E.getY()) + Math.abs(N.getZ() * E.getZ());

        BoundingSphere sphere = new BoundingSphere(center, fRadius);

        return boundingPlane.intersectSphere(sphere);
    }

    public IntersectData intersectSphere(BoundingSphere other){
        return new BoundingSphere(getCenter(), getRadius()).intersectBoundingSphere(other);
    }

    @Override
    public Vector3f getCenter(){
        Vector3f total =  minExtents.add(maxExtents);
        return total.div(2);
    }

    @Override
    public float getRadius(){
        Vector3f total = getMaxExtents().sub(getCenter());
        return total.length();
    }

    //Getters and Setters Below
    public Vector3f getMinExtents() {
        return minExtents;
    }
    public Vector3f getMaxExtents() {
        return maxExtents;
    }

}
