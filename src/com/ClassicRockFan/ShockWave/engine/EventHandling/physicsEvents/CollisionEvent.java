package com.ClassicRockFan.ShockWave.engine.EventHandling.physicsEvents;


import com.ClassicRockFan.ShockWave.engine.EventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.EventHandling.core.EventTyping;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;

public class CollisionEvent extends Event {

    private PhysicsObject obj1;
    private PhysicsObject obj2;
    private IntersectData data;

    public CollisionEvent(PhysicsObject obj1, PhysicsObject obj2, IntersectData data) {
        super(EventTyping.PHYSICS_COLLISION_EVENT_TYPE);
        this.obj1 = obj1;
        this.data = data;
        this.obj2 = obj2;
    }

    @Override
    public void handle(double delta) {
        super.handle(delta);

        Vector3f nullVelocity = new Vector3f(0, 0, 0);

        Vector3f direction = data.getDirection();
        float distance = direction.length();


        //Calculate the energies of both objects to get the maximum distance
        //Between the objects can be before bouncing apart
        float obj1Kinetic = 0.5f * obj1.getVelocity().length() * obj1.getVelocity().length() * obj1.getMass();
        float obj2Kinetic = 0.5f * obj2.getVelocity().length() * obj2.getVelocity().length() * obj2.getMass();

        //Find the max compression based on Sigma Energy because maximum
        //Compression occurs when both objects stop motion
        float obj1MaxSpring = (float) Math.sqrt((obj1Kinetic + obj2Kinetic) * obj1.getK() / 0.5);
        float obj2MaxSpring = (float) Math.sqrt((obj1Kinetic + obj2Kinetic) * obj2.getK() / 0.5);

        if (distance >= obj1MaxSpring || distance >= obj2MaxSpring) {

            //Calculate the momenta of each object
            Vector3f momentumA1 = obj1.getVelocity().mul(obj1.getMass());
            Vector3f momentumB1 = obj2.getVelocity().mul(obj2.getMass());

            Vector3f additionalMomentumA = direction.mul(obj1.getK()).mul((float) delta);
            Vector3f additionalMomentumB = direction.mul(obj2.getK()).mul((float) delta).mul(-1);

            momentumA1.set(momentumA1.add(additionalMomentumA));
            momentumB1.set(momentumB1.add(additionalMomentumB));

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
            }
        } else {
            //Determine the force acting on each object as a scalar
            float forceOnA = obj2.getK() * distance;
            float forceOnB = obj1.getK() * distance;

            //convert the scalars to vectors
            Vector3f forceA = new Vector3f(0, 0, 0);
            Vector3f forceB = new Vector3f(0, 0, 0);

            /////////////////////////////////
            if (obj1.getVelocity().getX() < 0)
                forceA.setX(forceOnA);
            else if (obj1.getVelocity().getX() == 0)
                forceA.setX(0);
            else
                forceA.setX(-1 * forceOnA);
            ////////////////////////////////
            if (obj1.getVelocity().getY() < 0)
                forceA.setY(forceOnA);
            else if (obj1.getVelocity().getY() == 0)
                forceA.setY(0);
            else
                forceA.setY(-1 * forceOnA);
            ////////////////////////////////
            if (obj1.getVelocity().getZ() < 0)
                forceA.setZ(forceOnA);
            else if (obj1.getVelocity().getZ() == 0)
                forceA.setZ(0);
            else
                forceA.setZ(-1 * forceOnA);

            /////////////////////////////////
            if (obj2.getVelocity().getX() < 0)
                forceB.setX(forceOnB);
            else if (obj2.getVelocity().getX() == 0)
                forceB.setX(0);
            else
                forceB.setX(-1 * forceOnB);
            ////////////////////////////////
            if (obj2.getVelocity().getY() < 0)
                forceB.setY(forceOnB);
            else if (obj2.getVelocity().getY() == 0)
                forceB.setY(0);
            else
                forceB.setY(-1 * forceOnB);
            ////////////////////////////////
            if (obj2.getVelocity().getZ() < 0)
                forceB.setZ(forceOnB);
            else if (obj2.getVelocity().getZ() == 0)
                forceB.setZ(0);
            else
                forceB.setZ(-1 * forceOnB);

            ////////////////////////////////

            //Convert the force to an acceleration and add it to the object
            obj1.setAcceleration((forceA.div(obj1.getMass())));
            obj2.setAcceleration((forceB.div(obj2.getMass())));
        }
    }
}