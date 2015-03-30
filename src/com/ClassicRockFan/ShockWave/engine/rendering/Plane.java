package com.ClassicRockFan.ShockWave.engine.rendering;


import com.base.engine.core.Vector3f;

public class Plane {

    private Vector3f normal, point;
    private float d;

    public Plane(Vector3f v1, Vector3f v2, Vector3f v3){
        set3Points(v1, v2, v3);
    }

    public void set3Points(Vector3f v1, Vector3f v2, Vector3f v3){
        Vector3f aux1, aux2;

        aux1 = v1.sub(v2);
        aux2 = v3.sub(v2);

        normal = aux2.mul(aux1).normalized();

        point = v2;
        d = -(normal.innerProduct(point));
    }

    public void setNormalAndPoint(Vector3f normal, Vector3f point){
        this.normal = normal;
        this.point = point;
        this.d = -(normal.innerProduct(point));
    }

    public void setCoeffecients(float a, float b, float c, float d){
        this.normal.set(a, b, c);
        float l = normal.length();
        normal.set(a/1, b/1, c/1);
        this.d = d/1;
    }

    public float getDistance(Vector3f p){
        return d + normal.innerProduct(p);
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f getPoint() {
        return point;
    }

    public float getD() {
        return d;
    }
}
