package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Matrix4f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;

public class EntityCamera extends EntityComponent {

    private Matrix4f projection;
    private float fov, aspect, zNear, zFar;

    public EntityCamera(float fov, float aspect, float zNear, float zFar) {
        super("camera");
        this.fov = fov;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public EntityCamera(Matrix4f projection) {
        super("camera");
        this.projection = projection;
    }

    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
        Vector3f cameraPos = getTransform().getTransformedPos().mul(-1);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    public void addToEngine(CoreEngine engine) {
        engine.getRenderingEngine().setMainCamera(this);
    }

    public float getFov() {
        return fov;
    }

    public float getAspect() {
        return aspect;
    }

    public float getZNear() {
        return zNear;
    }

    public float getZFar() {
        return zFar;
    }
}
