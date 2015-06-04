package com.ClassicRockFan.ShockWave.engine.physics;


import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
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

    private Vector3f gravity;

    public PhysicsEngine(CoreEngine engine) {
        this.engine = engine;
        this.simulateTimer = new ProfileTimer();
        this.collisionTimer = new ProfileTimer();
        this.octree = new Octree(10000, 2);
        this.gravity = new Vector3f(0, -9.81f, 0);
        CoreEngine.getConsole().addConsoleText("Creating the Physics Engine!");
    }

    public void doPhyiscs(float delta) {
        octree.clean();
        ArrayList<Entity> loadedEntities = engine.getGame().getEntityManager().getLoadedEntities();
        ArrayList<PhysicsComponent> objects = new ArrayList<PhysicsComponent>();


        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).isHasPhysics()) {
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


        HashMap<PhysicsComponent, ArrayList<Vector3f>> collisionMap = new HashMap<PhysicsComponent, ArrayList<Vector3f>>();

        //register all of the objects to the collision map
        for(int i = 0; i < objects.size(); i++){
            collisionMap.put(objects.get(i), new ArrayList<Vector3f>());
        }

        for (int i = 0; i < objects.size(); i++) {
            //define the obj1
            PhysicsComponent obj1 = objects.get(i);
            for (int j = i + 1; j < objects.size(); j++) {
                //Define the new object
                PhysicsComponent obj2 = objects.get(j);

                IntersectData data = obj1.getCollider().intersect(obj2.getCollider());
                if (data.isDoesIntersect()) {
                    if (obj1.getCollider().getResponse().getPriority() >= obj2.getCollider().getResponse().getPriority())
                        obj1.getCollider().getResponse().respond(obj1, obj2, data, delta);
                    else
                        obj2.getCollider().getResponse().respond(obj2, obj1, data, delta);

                    collisionMap.get(obj1).add(data.getDirection());
                    collisionMap.get(obj2).add(data.getDirection());
                } else {
                    obj1.getCollider().getResponse().resetTime(obj1, obj2);
                }
            }
            if(collisionMap.get(obj1).size() == 0){
                obj1.addForce(gravity.mul(obj1.getMass()));
            }else{
                Vector3f basicForce = gravity.mul(obj1.getMass());
                Vector3f appliedForce = basicForce;
                for(int k = 0; k < collisionMap.get(obj1).size(); k++){
                    Vector3f direction = new Vector3f(collisionMap.get(obj1).get(k).getX(),collisionMap.get(obj1).get(k).getY(), collisionMap.get(obj1).get(k).getZ()).normalized();
                    if(obj1.getParent().getName().equals("gumBall_0"))
                        direction.printVector("Direction");
                    float angle =  direction.angle(new Vector3f(0f, 1f, 0f));

                    float sinAngle = (float) Math.sin(angle);
                    float cosAngle = (float) Math.cos(angle);

                    float xyTheta = (float) Math.toDegrees(direction.getXY().angle(new Vector2f(0f, 1f)));
                    float zyTheta = (float) Math.toDegrees(direction.getZY().angle(new Vector2f(0f,1f)));
                    if(obj1.getParent().getName().equals("gumBall_0")){
                        System.out.println("angle = " + angle);
                        System.out.println("sinAngle = " + sinAngle);
                        System.out.println("cosAngle = " + cosAngle);
                        System.out.println("XY Theta = " + xyTheta);
                        System.out.println("ZY Theta = " + zyTheta);
                    }

                    Vector3f xyEffect = basicForce.mul((float) Math.cos(xyTheta));
                    Vector3f zyEffect = basicForce.mul((float) Math.cos(zyTheta));

                    appliedForce = appliedForce.add(xyEffect).add(zyEffect).add(new Vector3f(xyEffect.length(), 0, zyEffect.length()));
                }
                obj1.addForce(appliedForce);
                if(obj1.getParent().getName().equals("gumBall_0")){
                    appliedForce.printVector("Applied force on gumball_0");
                }

            }
        }

//        for (int i = 0; i < objects.size(); i++) {
//            boolean collided = false;
//            for (int j = i + 1; j < objects.size(); j++) {
//                IntersectData data = datas.get(i);
//                if(data.isDoesIntersect()){
//                    collided = true;
//
//                    Vector3f customGrav = calcGravityAccel(data);
//                    objects.get(i).addForce(customGrav.mul(objects.get(i).getMass()));
//                    objects.get(j).addForce(customGrav.mul(objects.get(j).getMass()));
//                }
//            }
//        }
        collisionTimer.stopInvocation();
    }

    private Vector3f calcGravityAccel(IntersectData data){
        if(data.isDoesIntersect()){
            float y = Math.abs(data.getDirection().normalized().getY());

            if(y != 1) {
                Vector3f direction = new Vector3f(data.getDirection().getX(), y, data.getDirection().getZ()).normalized();
                float angle = direction.dot(new Vector3f(0, 1, 0));

                float sinAngle = (float) Math.sin(angle);

                return gravity.mul(sinAngle);
            }else{
                return new Vector3f(0,0,0);
            }
        }else{
            return gravity;
        }
    }
    public double displaySimulateTime(double dividend) {
        return simulateTimer.displayAndReset("Simulation Time: ", dividend);
    }

    public double displayCollisionTime(double dividend) {
        return collisionTimer.displayAndReset("Collision Time: ", dividend);
    }
}
