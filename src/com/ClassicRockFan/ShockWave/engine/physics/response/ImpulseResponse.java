package com.ClassicRockFan.ShockWave.engine.physics.response;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;

public class ImpulseResponse extends Response{

    private boolean printed = false;

    public ImpulseResponse() {
        super(ResponsePriorities.IMPULSE_BASED_RESPONSE);
    }


    @Override
    public void respond(PhysicsComponent obj1, PhysicsComponent obj2, IntersectData data, double delta) {
        super.respond(obj1, obj2, data, delta);

        //Calculate the point at which all kinetic energy will have transferred to SPE
        float obj1Kin = obj1.getVelocity().mul(obj1.getVelocity()).mul(obj1.getMass()).mul(0.5f).length();
        float obj2Kin = obj2.getVelocity().mul(obj2.getVelocity()).mul(obj2.getMass()).mul(0.5f).length();

        float obj1Compress = (float) Math.sqrt((obj1Kin*2) / obj1.getK());
        float obj2Compress = (float) Math.sqrt((obj2Kin*2) / obj2.getK());

        if(obj1Compress <= data.getDistance() || obj2Compress <= data.getDistance()){
            Vector3f obj1Momentum = obj1.getVelocity().mul(obj1.getMass());
            Vector3f obj2Momentum = obj2.getVelocity().mul(obj2.getMass());
            Vector3f totalMomentum = obj1Momentum.add(obj2Momentum);

            if(totalMomentum.equals(new Vector3f(0, 0, 0))){
                obj1.setVelocity(new Vector3f(0,0,0));
                obj2.setVelocity(new Vector3f(0,0,0));
            }

            Vector3f force = totalMomentum.div((float) Math.pow(Math.sqrt(getInteractions().get(obj2)), 2));

            if(obj2.isImmovable()) {
                obj1.addForce(force.mul(-1));
                //obj1.setVelocity(new Vector3f(0, 0, 0));
                //obj1.setConstantAcceleration(new Vector3f(0,0,0));
            }else
                obj1.addForce(force);

            if(obj1.isImmovable()) {
                obj2.addForce(force.mul(-1));
                //obj2.setVelocity(new Vector3f(0, 0, 0));
                //obj2.setConstantAcceleration(new Vector3f(0, 0, 0));
            }else
                obj2.addForce(force.mul(1));

//            if(!printed){
//                totalMomentum.printVector("Initial Momentum");
//                force.printVector("Force imparted on the objects during the collision");
//                System.out.println("Delta = " + getInteractions().get(obj2));
//                printed = true;
//            }

            resetTime(obj1, obj2);
        }
    }
}
