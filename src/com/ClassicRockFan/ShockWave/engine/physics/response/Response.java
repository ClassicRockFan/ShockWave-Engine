package com.ClassicRockFan.ShockWave.engine.physics.response;


import com.ClassicRockFan.ShockWave.engine.physics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;

import java.util.HashMap;

public abstract class Response {

    private int priority;
    private HashMap<PhysicsComponent, Double> interactions;

    public Response(){
        this(ResponsePriorities.BASE_RESPONSE_LEVEL);
    }

    public Response(int priority) {
        this.interactions = new HashMap<PhysicsComponent, Double>();
        this.priority = priority;
    }

    public void respond(PhysicsComponent obj1, PhysicsComponent obj2, IntersectData data, double delta){
        //Add time to the collision time
        if(!interactions.containsKey(obj2)){
            interactions.put(obj2, delta);
            obj2.getCollider().getResponse().getInteractions().put(obj1, delta);
        }else {
            Double elasped = interactions.get(obj2);
            interactions.remove(obj2);
            interactions.put(obj2, elasped + delta);
            obj2.getCollider().getResponse().getInteractions().remove(obj1);
            obj2.getCollider().getResponse().getInteractions().put(obj1, elasped + delta);
        }
    }
    public int getPriority(){
        return priority;
    }

    public HashMap<PhysicsComponent, Double> getInteractions() {
        return interactions;
    }

    public void resetTime(PhysicsComponent obj1, PhysicsComponent obj2){
        Double elasped = 0.0;
        interactions.remove(obj2);
        interactions.put(obj2, elasped);
        obj2.getCollider().getResponse().getInteractions().remove(obj1);
        obj2.getCollider().getResponse().getInteractions().put(obj1, elasped);
    }
}
