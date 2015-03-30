package com.ClassicRockFan.ShockWave.engine.components.rendering;


import com.base.engine.components.coreComponents.GameComponent;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Vector3f;

public class Camera extends GameComponent {

    private Matrix4f projection;
    private float fov, aspect, zNear, zFar;

    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.fov = fov;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Camera(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    @Override
    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().addCamera(this);
    }

    public float getFov() {
        return fov;
    }

    public float getAspect() {
        return aspect;
    }

    public float getzNear() {
        return zNear;
    }

    public float getzFar() {
        return zFar;
    }
}
