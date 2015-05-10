package com.ClassicRockFan.ShockWave.engine.rendering;

import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.AABB;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.BoundingPlane;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.BoundingSphere;

public class FrustumCull {

    private Plane[] planes;

    private float zNear, zFar, ratio, angle,tang;
    private float nw,nh,fw,fh;
    private Vector3f ntl,ntr,nbl,nbr,ftl,ftr,fbl,fbr;

    public FrustumCull() {
        planes = new Plane[6];
        for(int i = 0; i < planes.length; i++){
            planes[i] = new Plane(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(0,0,0));
        }
    }

    public void setCamInternals(EntityCamera camera){
        this.ratio = camera.getAspect();
        this.angle = camera.getFov();
        this.zNear = camera.getZNear();
        this.zFar = camera.getZFar();

        this.tang = (float) Math.tan(Math.toRadians(angle*0.5));
        this.nh = zNear * tang;
        this.nw = nh * ratio;
        this.fh = zFar * tang;
        this.fw = fh * ratio;
    }

    public void setCamDef(EntityCamera camera){
        Transform transform = camera.getTransform();
        Vector3f pos = transform.getPos();
        Vector3f left = transform.getRot().getLeft();
        Vector3f up = transform.getRot().getUp();

        Vector3f z = (pos.sub(left)).normalized();
        Vector3f x = (up.mul(z)).normalized();
        Vector3f y = z.mul(x);

        //Far plane calculation
        Vector3f fc = pos.sub(z).mul(zFar);
        this.ftl = fc.add(y.mul(fh)).sub(x.sub(fw));
        this.ftr = fc.add(y.mul(fh)).add(x.mul(fw));
        this.fbl = fc.sub(y.mul(fh)).sub(x.mul(fw));
        this.fbr = fc.sub(y.mul(fh)).add(x.mul(fw));

        //Near plane calculations
        Vector3f nc = pos.sub(z).mul(zNear);
        this.ntl = nc.add(y.mul(nh)).sub(x.sub(nw));
        this.ntr = nc.add(y.mul(nh)).add(x.mul(nw));
        this.nbl = nc.sub(y.mul(nh)).sub(x.mul(nw));
        this.nbr = nc.sub(y.mul(nh)).add(x.mul(nw));

        planes[0].set3Points(ntr, ntr, ftl);
        planes[1].set3Points(nbl, nbr, fbr);
        planes[2].set3Points(ntl, nbl, fbl);
        planes[3].set3Points(nbr, ntr, fbr);
        planes[4].set3Points(ntl, ntr, nbr);
        planes[5].set3Points(ftr, ftl, fbl);
    }

    public void pointInFrustum(EntityCamera camera){
        //Convenience
        Transform transform = camera.getTransform();
        Vector3f up = transform.getRot().getUp().normalized();
        Vector3f right = transform.getRot().getRight().normalized();
        Vector3f forward = transform.getRot().getForward().normalized();

        float halfHeight = (float)Math.tan(camera.getFov()/camera.getAspect()/2)*camera.getZNear();
        float halfWidth = (float)Math.tan(camera.getFov()/2)*camera.getZFar();

        //Far plane calculation
        Vector3f fc = transform.getPos().add(forward).mul(camera.getZFar());
        this.ftl = fc.add(up.mul(halfHeight)).sub(right.sub(halfWidth));
        this.ftr = fc.add(up.mul(halfHeight)).add(right.mul(halfWidth));
        this.fbl = fc.sub(up.mul(halfHeight)).sub(right.mul(halfWidth));
        this.fbr = fc.sub(up.mul(halfHeight)).add(right.mul(halfWidth));

        //Near plane calculations
        Vector3f nc = transform.getPos().add(forward).mul(camera.getZNear());
        this.ntl = fc.add(up.mul(halfHeight)).sub(right.sub(halfWidth));
        this.ntr = fc.add(up.mul(halfHeight)).add(right.mul(halfWidth));
        this.nbl = fc.sub(up.mul(halfHeight)).sub(right.mul(halfWidth));
        this.nbr = fc.sub(up.mul(halfHeight)).add(right.mul(halfWidth));

        Vector3f a = ((nc.add(right).mul(halfWidth)).sub(transform.getPos())).normalized();

        Vector3f normalRight = up.mul(a);
    }

    public boolean pointInFrustum(Vector3f pos){
        boolean result = true;

        for(int i = 0; i < 6; i++){
            if(planes[i].getDistance(pos) < 0)
                return  false;
        }

        return result;
    }

    public boolean sphereInFrustum(BoundingSphere sphere){
        float distance;
        boolean result = true;

        for(int i =0; i<6; i++){
            distance = planes[i].getDistance(sphere.getCenter());
            if(distance < -sphere.getRadius())
                return false;
            else if( distance < sphere.getRadius())
                return true;
        }
        return result;
    }

    public boolean boxInFrustum(AABB b){
        for(int i=0; i < 6; i++) {
            BoundingPlane boundingPlanes = new BoundingPlane(planes[i].getNormal(), planes[i].getD());

            IntersectData data = b.intersectPlane(boundingPlanes);
            if(data.isDoesIntersect())
                return true;
        }

        return false;
    }

}
