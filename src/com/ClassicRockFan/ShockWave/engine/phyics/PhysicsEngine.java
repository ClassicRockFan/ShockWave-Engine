package com.ClassicRockFan.ShockWave.engine.phyics;


import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;

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
        Vector3f nullVelocity = new Vector3f(0, 0, 0);
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                //Define the two new objects
                PhysicsObject obj1 = objects.get(i);
                PhysicsObject obj2 = objects.get(j);

                IntersectData data = obj1.getCollider().intersect(obj2.getCollider());
                if (data.isDoesIntersect()) {
                    obj1.getVelocity().printVector("OBJ1's velocity pre-collisions");
                    obj2.getVelocity().printVector("OBJ2's velocity pre-collisions");

                    Vector3f direction = data.getDirection();
                    float distance = direction.length();

                    System.out.println("Distance: " + distance);

                    //Calculate the energies of both objects to get the maximum distance
                    //Between the objects can be before bouncing apart
                    float obj1Kinetic = 0.5f * obj1.getVelocity().length() * obj1.getVelocity().length() * obj1.getMass();
                    float obj2Kinetic = 0.5f * obj2.getVelocity().length() * obj2.getVelocity().length() * obj2.getMass();

                    //Find the max compression based on Sigma Energy because maximum
                    //Compression occurs when both objects stop motion
                    float obj1MaxSpring = (float) Math.sqrt((obj1Kinetic + obj2Kinetic) * obj1.getK() / 0.5);
                    float obj2MaxSpring = (float) Math.sqrt((obj1Kinetic + obj2Kinetic) * obj2.getK() / 0.5);

                    System.out.println("OBJ1's Max Compression: " + obj1MaxSpring);
                    System.out.println("OBJ2's Max Compression: " + obj2MaxSpring);

                    if (distance >= obj1MaxSpring || distance >= obj2MaxSpring) {

                        //Calculate the momenta of each object
                        Vector3f momentumA1 = obj1.getVelocity().mul(obj1.getMass());
                        Vector3f momentumB1 = obj2.getVelocity().mul(obj2.getMass());

                        momentumA1.printVector("Initial Momentum A");
                        momentumB1.printVector("Initial Momentum B");

                        Vector3f additionalMomentumA = direction.mul(obj1.getK()).mul(delta);
                        Vector3f additionalMomentumB = direction.mul(obj2.getK()).mul(delta).mul(-1);

                        additionalMomentumA.printVector("Additional Momentum A");
                        additionalMomentumB.printVector("Additional Momentum B");

                        momentumA1.set(momentumA1.add(additionalMomentumA));
                        momentumB1.set(momentumB1.add(additionalMomentumB));

                        momentumA1.printVector("Final Momentum A");
                        momentumB1.printVector("Final Momentum B");

                        Vector3f obj1Velocity;
                        Vector3f obj2Velocity;

                        //Calculate new velocities
                        if (obj1.isImmovable() || obj2.isImmovable()) {
                            obj1Velocity = new Vector3f(0, 0, 0);
                            obj2Velocity = new Vector3f(0, 0, 0);
                        } else {
                            obj1Velocity = momentumB1.div(obj1.getMass());
                            obj2Velocity = momentumA1.div(obj2.getMass());
                        }

                        //Apply velocities
                        obj1.setVelocity((obj1Velocity));
                        obj2.setVelocity(obj2Velocity);

                        //Check if the objects have stopped and then add a little space
                        // to stop wasting time calculating stuff that doesn't need to happen
                        if (obj1.getVelocity().equals(nullVelocity) && obj2.getVelocity().equals(nullVelocity)) {
                            if (obj1.isImmovable()) {
                                obj1.setPosition(obj1.getPosition().sub(data.getDirection().mul(-1)));
                                obj1.setVelocity((obj1Velocity));
                            }
                            if (obj2.isImmovable()) {
                                obj2.setPosition(obj2.getPosition().sub(data.getDirection()));
                                obj2.setVelocity(obj2Velocity);
                            }
                            obj1.getPosition().printVector("OBJ1 pos");
                            obj2.getPosition().printVector("OBJ2 pos");
                        }
                    }else{
                        //Determine the force acting on each object as a scalar
                        float forceOnA = obj2.getK() * distance;
                        float forceOnB = obj1.getK() * distance;

                        //convert the scalars to vectors
                        Vector3f forceA = new Vector3f(0,0,0);
                        Vector3f forceB = new Vector3f(0,0,0);

                        /////////////////////////////////
                        if(obj1.getVelocity().getX() < 0)
                            forceA.setX(forceOnA);
                        else if(obj1.getVelocity().getX() == 0)
                            forceA.setX(0);
                        else
                            forceA.setX(-1*forceOnA);
                        ////////////////////////////////
                        if(obj1.getVelocity().getY() < 0)
                            forceA.setY(forceOnA);
                        else if(obj1.getVelocity().getY() == 0)
                            forceA.setY(0);
                        else
                            forceA.setY(-1*forceOnA);
                        ////////////////////////////////
                        if(obj1.getVelocity().getZ() < 0)
                            forceA.setZ(forceOnA);
                        else if(obj1.getVelocity().getZ() == 0)
                            forceA.setZ(0);
                        else
                            forceA.setZ(-1*forceOnA);

                        /////////////////////////////////
                        if(obj2.getVelocity().getX() < 0)
                            forceB.setX(forceOnB);
                        else if(obj2.getVelocity().getX() == 0)
                            forceB.setX(0);
                        else
                            forceB.setX(-1*forceOnB);
                        ////////////////////////////////
                        if(obj2.getVelocity().getY() < 0)
                            forceB.setY(forceOnB);
                        else if(obj2.getVelocity().getY() == 0)
                            forceB.setY(0);
                        else
                            forceB.setY(-1*forceOnB);
                        ////////////////////////////////
                        if(obj2.getVelocity().getZ() < 0)
                            forceB.setZ(forceOnB);
                        else if(obj2.getVelocity().getZ() == 0)
                            forceB.setZ(0);
                        else
                            forceB.setZ(-1*forceOnB);

                        ////////////////////////////////

                        //Print the Vectors for debugging
                        forceA.printVector("Force on obj1");
                        forceB.printVector("Force on obj2");

                        //Convert the force to a velocity and add it to the object
                        obj1.setVelocity(obj1.getVelocity().add(forceA.mul(delta).div(obj1.getMass())));
                        obj2.setVelocity(obj2.getVelocity().add(forceB.mul(delta).div(obj2.getMass())));
                    }
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
