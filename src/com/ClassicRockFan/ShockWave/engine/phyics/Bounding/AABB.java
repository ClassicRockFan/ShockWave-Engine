package com.ClassicRockFan.ShockWave.engine.phyics.Bounding;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.Collider;
import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;

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
//
//    public IntersectData intersectSphere(BoundingSphere other){
//
//
//
//    }

    //Getters and Setters Below
    public Vector3f getMinExtents() {
        return minExtents;
    }

    public void setMinExtents(Vector3f minExtents) {
        this.minExtents = minExtents;
    }

    public Vector3f getMaxExtents() {
        return maxExtents;
    }

    public void setMaxExtents(Vector3f maxExtents) {
        this.maxExtents = maxExtents;
    }
}
