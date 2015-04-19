package com.ClassicRockFan.ShockWave.engine.phyics;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;

public class PhysicsComponent extends EntityComponent {

    private Vector3f position;
    private Vector3f oldPosition;
    private Vector3f velocity;
    private Vector3f acceleration;
    private boolean hasCamera;
    private Collider collider;
    private float mass;
    private float k;
    private boolean immovable;


    public PhysicsComponent(Collider collider, Vector3f velocity, float mass) {
        this(collider, velocity, mass, 0);
    }

    public PhysicsComponent(Collider collider, Vector3f velocity, float mass, float springConstant) {
        this(collider, velocity, mass, springConstant, false);
    }

    public PhysicsComponent(Collider collider, Vector3f velocity, float mass, float springConstant, boolean immovable) {
        super("physicsComponent");
        this.position = collider.getCenter();
        this.oldPosition = collider.getCenter();
        this.velocity = velocity;
        this.acceleration = new Vector3f(0, 0, 0);
        this.collider = collider;
        this.hasCamera = false;
        this.mass = mass;
        this.k = springConstant;
        this.immovable = immovable;
    }


    @Override
    public void update(float delta) {
        Vector3f part1 = getVelocity().mul(delta);
        Vector3f part2 = getAcceleration().mul(delta * delta);
        setPosition(getPosition().add(part1.add(part2)));
        getTransform().getPos().set(getPosition());

        setVelocity(getVelocity().add(getAcceleration().mul(delta)));
        setAcceleration(new Vector3f(0, 0, 0));
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.oldPosition = getPosition();
        this.position = position;
    }

    public Vector3f getOldPosition() {
        return oldPosition;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector3f acceleration) {
        this.acceleration = acceleration;
    }

    public Collider getCollider() {
        Vector3f translation = position.sub(oldPosition);
        oldPosition = position;
        collider.transform(translation);
        return collider;
    }

    public boolean isHasCamera() {
        return hasCamera;
    }

    public void setHasCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }

    public float getMass() {
        return mass;
    }

    public float getK() {
        return k;
    }

    public boolean isImmovable() {
        return immovable;
    }
}
