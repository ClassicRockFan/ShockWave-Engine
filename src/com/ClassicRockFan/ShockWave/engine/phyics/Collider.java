package com.ClassicRockFan.ShockWave.engine.phyics;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.bounding.BoundingSphere;

public class Collider {

    public static final int TYPE_SPHERE = 0;
    public static final int TYPE_AABB = 1;
    public static final int TYPE_SIZE = 2;

    public static final Vector3f NO_MATCH = new Vector3f(-100, -100, -100);

    private int type;

    public Collider(int type) {
        this.type = type;
    }

    public Vector3f getCenter() {
        return new Vector3f(0, 0, 0);
    }


    public void transform(Vector3f translation) {
    }

    public IntersectData intersect(Collider other) {
        if (type == TYPE_SPHERE && other.getType() == TYPE_SPHERE) {
            BoundingSphere self = (BoundingSphere) this;
            return self.intersectBoundingSphere((BoundingSphere) other);
        } else if (type == TYPE_SPHERE && other.getType() == TYPE_AABB || type == TYPE_AABB && other.getType() == TYPE_SPHERE) {

        } else {
            System.err.println("Collision not implemented between colliders of types:" + this.getType() + " & " + other.getType());
            System.exit(1);
        }
        return new IntersectData(false, NO_MATCH);
    }

    public int getType() {
        return type;
    }
}
