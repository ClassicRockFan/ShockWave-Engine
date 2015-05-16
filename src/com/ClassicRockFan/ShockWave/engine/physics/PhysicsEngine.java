package com.ClassicRockFan.ShockWave.engine.physics;


import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.physics.trees.Octree;

import java.util.ArrayList;
import java.util.HashMap;

public class PhysicsEngine {
    private CoreEngine engine;
    private ProfileTimer simulateTimer;
    private ProfileTimer collisionTimer;
    private Octree octree;
    private Gravity grav;

    public PhysicsEngine(CoreEngine engine) {
        this.engine = engine;
        this.simulateTimer = new ProfileTimer();
        this.collisionTimer = new ProfileTimer();
        this.octree = new Octree(10000,2);
        this.grav = new Gravity(new Vector3f(0, -9.8f, 0));
        CoreEngine.getConsole().addConsoleText("Creating the Physics Engine!");
    }

    public void doPhyiscs(float delta) {
        octree.clean();
        ArrayList<Entity> loadedEntities = engine.getGame().getEntityManager().getLoadedEntities();
        ArrayList<PhysicsComponent> objects = new ArrayList<PhysicsComponent>();


        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).isHasPhysics()){
                objects.add(loadedEntities.get(i).getPhysicsComponent());
                octree.insertObject(loadedEntities.get(i));
            }
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


        //Logging.printLog("Number of child nodes in the octree = " + octree.getInitialNode().getNumChildren(), Logging.LEVEL_DEBUG);

        HashMap<PhysicsComponent, Boolean> gravity = new HashMap<PhysicsComponent, Boolean>();

        for(int i = 0; i < objects.size(); i++){
            gravity.put(objects.get(i), true);
        }

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

                    if(data.getDirection().normalized().getY() < 0 ){
                        gravity.remove(obj1);
                        gravity.remove(obj2);
                        gravity.put(obj1, false);
                        gravity.put(obj2, false);
                    }
                }else{
                    obj1.getCollider().getResponse().resetTime(obj1, obj2);
                }
            }
        }

        ArrayList<PhysicsComponent> gravityList = new ArrayList<PhysicsComponent>();
        for(int i = 0; i < objects.size(); i++){
            if(gravity.get(objects.get(i)))
                gravityList.add(objects.get(i));
        }
        grav.doGravity(gravityList);
        collisionTimer.stopInvocation();
    }

    public double displaySimulateTime(double dividend) {
        return simulateTimer.displayAndReset("Simulation Time: ", dividend);
    }

    public double displayCollisionTime(double dividend) {
        return collisionTimer.displayAndReset("Collision Time: ", dividend);
    }
}
