package com.ClassicRockFan.ShockWave.engine.phyics.response;


import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsComponent;

public class ReflectResponse extends Response {

    public ReflectResponse() {
        super(ResponsePriorities.REFLECT_RESPONSE);
    }

    @Override
    public void respond(PhysicsComponent obj1, PhysicsComponent obj2, IntersectData data, double delta) {
        super.respond(obj1, obj2, data, delta);
        obj1.setVelocity(obj1.getVelocity().reflect(data.getDirection()));
        obj2.setVelocity(obj2.getVelocity().reflect(data.getDirection().mul(1)));
    }
}
