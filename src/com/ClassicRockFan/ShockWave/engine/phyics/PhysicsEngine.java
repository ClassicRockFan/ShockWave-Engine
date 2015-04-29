package com.ClassicRockFan.ShockWave.engine.phyics;


import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

import java.util.ArrayList;

public class PhysicsEngine {
    private CoreEngine engine;
    private ProfileTimer simulateTimer;
    private ProfileTimer collisionTimer;

    public PhysicsEngine(CoreEngine engine) {
        this.engine = engine;
        this.simulateTimer = new ProfileTimer();
        this.collisionTimer = new ProfileTimer();
        CoreEngine.getConsole().addConsoleText("Creating the Physics Engine!");
    }

    public void doPhyiscs(float delta) {
        ArrayList<Entity> loadedEntities = engine.getEntityManager().getAllLoadedEntites();
        ArrayList<PhysicsComponent> objects = new ArrayList<PhysicsComponent>();

        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).isHasPhysics())
                objects.add(loadedEntities.get(i).getPhysicsComponent());
        }

        //simulating
        simulateTimer.startInvocation();
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update(delta);
            if (objects.get(i).isHasCamera()) {
                objects.get(i).getVelocity().set(0, 0, 0);
            }
        }
        simulateTimer.stopInvocation();

        //Handling collisions
        collisionTimer.startInvocation();
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                //Define the two new objects
                PhysicsComponent obj1 = objects.get(i);
                PhysicsComponent obj2 = objects.get(j);

                IntersectData data = obj1.getCollider().intersect(obj2.getCollider());
                if (data.isDoesIntersect()) {
                    if(obj1.getCollider().getResponse().getPriority() >= obj2.getCollider().getResponse().getPriority())
                        obj1.getCollider().getResponse().respond(obj1, obj2, data, delta);
                    else
                        obj2.getCollider().getResponse().respond(obj2, obj1, data, delta);
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
}
