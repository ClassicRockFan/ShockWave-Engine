package com.ClassicRockFan.ShockWave.engine.phyics.Bounding;

import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.Collider;
import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;

public class BoundingSphere extends Collider {

    private Vector3f center;
    private float radius;

    public BoundingSphere(Vector3f center, float radius) {
        super(Collider.TYPE_SPHERE);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void transform(Vector3f translation) {
        center.set(center.getX() + translation.getX(), center.getY() + translation.getY(), center.getZ() + translation.getZ());
    }

    @Override
    public Vector3f getCenter() {
        return center;
    }

    public IntersectData intersectBoundingSphere(BoundingSphere other) {
        float radiusDistance = radius + other.getRadius();
        Vector3f direction = (other.getCenter().sub(center));

        float centerDistance = direction.length();
        direction.set(direction.div(centerDistance));

        float distance = centerDistance - radiusDistance;

        return new IntersectData(centerDistance < radiusDistance, direction.mul(distance));

    }

    public float getRadius() {
        return radius;
    }
}
