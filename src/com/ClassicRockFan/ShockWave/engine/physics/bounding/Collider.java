package com.ClassicRockFan.ShockWave.engine.physics.bounding;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.physics.response.ImpulseResponse;
import com.ClassicRockFan.ShockWave.engine.physics.response.Response;

public class Collider {

    public static final int TYPE_SPHERE = 0;
    public static final int TYPE_AABB = 1;
    public static final int TYPE_PLANE = 2;

    public static final Vector3f NO_MATCH = new Vector3f(-100, -100, -100);

    private int type;
    private Response response;

    public Collider(int type) {
        this(type, new ImpulseResponse());
    }

    public Collider(int type, Response response) {
        this.type = type;
        this.response = response;
    }

    public Vector3f getCenter() {
        return new Vector3f(0, 0, 0);
    }
    public float getRadius(){
        return 0.0f;
    }


    public void transform(Vector3f translation) {
    }

    public IntersectData intersect(Collider other) {
        if(type == TYPE_PLANE && other.getType() == TYPE_PLANE) {
            BoundingPlane self = (BoundingPlane) this;
            return self.interesectPlane((BoundingPlane)other);
        }else if (type == TYPE_SPHERE && other.getType() == TYPE_SPHERE) {
            BoundingSphere self = (BoundingSphere) this;
            return self.intersectBoundingSphere((BoundingSphere) other);
        } else if (type == TYPE_SPHERE && other.getType() == TYPE_PLANE) {
            BoundingPlane other_ = (BoundingPlane) other;
            return other_.intersectSphere((BoundingSphere) this);
        } else if (type == TYPE_PLANE && other.getType() == TYPE_SPHERE) {
            BoundingPlane self = (BoundingPlane) this;
            return self.intersectSphere((BoundingSphere) other);
        }else if(type == TYPE_AABB && other.getType() == TYPE_AABB) {
            AABB self = (AABB) this;
            return self.intersectAABB((AABB) other);
        }else if(type == TYPE_AABB && other.getType() == TYPE_PLANE) {
            AABB self = (AABB) this;
            return self.intersectPlane((BoundingPlane) other);
        }else if(type == TYPE_PLANE && other.getType() == TYPE_AABB) {
            AABB other_ = (AABB) other;
            return other_.intersectPlane((BoundingPlane) this);
        }else if(type == TYPE_AABB && other.getType() == TYPE_SPHERE) {
            AABB self = (AABB) this;
            return self.intersectSphere((BoundingSphere) other);
        }else if(type == TYPE_SPHERE && other.getType() == TYPE_AABB){
            AABB other_ = (AABB) other;
            return other_.intersectSphere((BoundingSphere) this);
        } else {
            System.err.println("Collision not implemented between colliders of types:" + this.getType() + " & " + other.getType());
            //System.exit(1);
        }
        return new IntersectData(false, NO_MATCH);
    }

    public int getType() {
        return type;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
