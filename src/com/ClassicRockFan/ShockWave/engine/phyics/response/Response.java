package com.ClassicRockFan.ShockWave.engine.phyics.response;


import com.ClassicRockFan.ShockWave.engine.phyics.IntersectData;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsComponent;

public abstract class Response {

    private int priority;

    public Response(){
        this(ResponsePriorities.BASE_RESPONSE_LEVEL);
    }

    public Response(int priority) {
        this.priority = priority;
    }

    public void respond(PhysicsComponent obj1, PhysicsComponent obj2, IntersectData data, double delta){
        //Stopped doing this to prevent console spam
        //Logging.printLog("Two Objects just collided.");
    }
    public int getPriority(){
        return priority;
    }

}
