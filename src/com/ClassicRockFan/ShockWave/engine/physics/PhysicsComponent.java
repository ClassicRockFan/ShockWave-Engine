package com.ClassicRockFan.ShockWave.engine.physics;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.Collider;

public class PhysicsComponent extends EntityComponent {

    private Vector3f position;
    private Vector3f oldPosition;
    private Vector3f velocity;
    private Vector3f acceleration;
    private Vector3f constantAcceleration;
    private boolean hasCamera;
    private Collider collider;
    private float mass;
    private float k;
    private boolean immovable;
    private boolean gravityEffecting;


    public PhysicsComponent(Collider collider, Vector3f velocity, float mass) {
        this(collider, velocity, mass, mass);
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
        this.constantAcceleration = new Vector3f(0,0,0);
        this.collider = collider;
        this.hasCamera = false;
        this.mass = mass;
        this.k = springConstant;
        this.immovable = immovable;
        this.gravityEffecting = true;
    }


    @Override
    public void update(float delta) {
        if(!isImmovable()) {
            adjustVelocity(getAcceleration().mul(delta).add(getConstantAcceleration().mul(delta)));
            Vector3f part1 = getVelocity().mul(delta);
            setPosition(getPosition().add(part1));
            getTransform().getPos().set(getPosition());


            this.acceleration = new Vector3f(0, 0, 0);
        }else{
            this.acceleration = new Vector3f(0,0,0);
            this.velocity = new Vector3f(0,0,0);
            this.constantAcceleration = new Vector3f(0,0,0);
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.oldPosition = getPosition();
        this.position = position;
    }

    public void addForce(Vector3f force){
        setAcceleration(force.div(getMass()));
    }

    public Vector3f getOldPosition() {
        return oldPosition;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void adjustVelocity(Vector3f velocity){
        setVelocity(getVelocity().add(velocity));
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getAcceleration() {
        return acceleration;
    }

    private void setAcceleration(Vector3f acceleration) {
        this.acceleration = getAcceleration().add(acceleration);
    }
    public void setConstantAcceleration(Vector3f acceleration){
        this.constantAcceleration = acceleration;
    }

    public Vector3f getConstantAcceleration() {
        return constantAcceleration;
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

    public boolean isGravityEffecting() {
        return gravityEffecting;
    }

    public void setGravityEffecting(boolean gravityEffecting) {
        this.gravityEffecting = gravityEffecting;
    }
}
