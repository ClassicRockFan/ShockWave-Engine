package com.ClassicRockFan.ShockWave.engine.physics.response;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;

public class PerfectlyElasticResponse extends Response {

    private boolean printed = false;

    public PerfectlyElasticResponse() {
        super(ResponsePriorities.PERFECTLY_ELASTIC_RESPONSE);
    }

    @Override
    public void respond(PhysicsComponent obj1, PhysicsComponent obj2, IntersectData data, double delta) {
        super.respond(obj1, obj2, data, delta);

        Vector3f obj1Direction = obj1.getVelocity().normalized();
        Vector3f obj2Direction = obj2.getVelocity().normalized();

        float obj1Kinetic = 0.5f * obj1.getMass() * obj1.getVelocity().mul(obj1.getVelocity()).length();
        float obj2Kinetic = 0.5f * obj1.getMass() * obj2.getVelocity().mul(obj2.getVelocity()).length();

        Vector3f obj1Momentum = obj1.getVelocity().mul(obj1.getMass());
        Vector3f obj2Momentum = obj2.getVelocity().mul(obj2.getMass());
        Vector3f totalMomentum = obj1Momentum.add(obj2Momentum);

        Vector3f finalVelocity = totalMomentum.div(obj1.getMass() + obj2.getMass());

        if(obj2.isImmovable())
            obj1.setVelocity(finalVelocity.mul(-1));
        else
            obj1.setVelocity(finalVelocity);

        if(obj1.isImmovable())
            obj2.setVelocity(finalVelocity.mul(-1));
        else
            obj2.setVelocity(finalVelocity);


        Vector3f obj1Momentumf = obj1.getVelocity().mul(obj1.getMass());
        Vector3f obj2Momentumf = obj2.getVelocity().mul(obj2.getMass());

        Vector3f finalMomentum = obj1Momentumf.add(obj2Momentumf);

        if(!printed){
            totalMomentum.printVector("Initial Momentum");
            finalVelocity.printVector("Final Velocity");
            obj1.getVelocity().printVector("OBJ1's velocity");
            obj2.getVelocity().printVector("OBJ2's velocity");
            finalMomentum.printVector("Final Momentum");
            System.out.println("No Momentum gained or lost in collision = " + totalMomentum.abs().equals(finalMomentum.abs()));
            printed = true;
        }
    }
}
