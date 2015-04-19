package com.ClassicRockFan.ShockWave.engine.phyics;


import com.ClassicRockFan.ShockWave.engine.EventHandling.physicsEvents.CollisionEvent;
import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class PhysicsEngine {
    public static final float DEFAULT_GRAVITY_ACCEL = 10.0f;
    public static float IMMOVABLE_MASS = -1.0f;
    public static float NULL_MASS = 99999999999999999f;
    private ArrayList<PhysicsObject> objects;
    private CoreEngine engine;
    private ProfileTimer simulateTimer;
    private ProfileTimer collisionTimer;

    public PhysicsEngine(CoreEngine engine) {
        objects = new ArrayList<PhysicsObject>();
        this.engine = engine;
        this.simulateTimer = new ProfileTimer();
        this.collisionTimer = new ProfileTimer();
        engine.getConsole().addConsoleText("Creating the Physics Engine!");
    }

    public void simulate(float delta) {
        simulateTimer.startInvocation();
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).integrate(delta);
            if (objects.get(i).isHasCamera()) {
                objects.get(i).getVelocity().set(0, 0, 0);
            }
        }
        simulateTimer.stopInvocation();
    }

    public void handleCollisions(float delta) {
        collisionTimer.startInvocation();
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                //Define the two new objects
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);

                IntersectData data = obj1.getCollider().intersect(obj2.getCollider());
                if (data.isDoesIntersect()) {
                    engine.getEventManager().addEvent(new CollisionEvent(obj1, obj2, data));
                }
            }
        }
        collisionTimer.stopInvocation();
    }

    public double displaySimulateTime(double dividend) {
        return simulateTimer.displayAndReset("Simulation Time: ", dividend);
    }

    public double displayCollisionTime(double dividend) {
        return collisionTimer.displayAndReset("Collision Time: ", dividend);
    }

    public void addPhysicsObject(PhysicsObject object) {
        objects.add(object);
    }

    public ArrayList<PhysicsObject> getObjects() {
        return objects;
    }

    public int getNumObjects() {
        return objects.size();
    }
}
