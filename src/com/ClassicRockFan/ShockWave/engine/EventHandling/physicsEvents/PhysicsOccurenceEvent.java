package com.ClassicRockFan.ShockWave.engine.EventHandling.physicsEvents;


import com.ClassicRockFan.ShockWave.engine.EventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.EventHandling.core.EventTyping;
import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;

public class PhysicsOccurenceEvent extends Event{

    private PhysicsEngine physicsEngine;
    public PhysicsOccurenceEvent(PhysicsEngine physicsEngine) {
        super(EventTyping.PHYSICS_OCCURENCE_EVENT_TYPE, "", Logging.LEVEL_NULL);
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void handle(double frameTime) {
        physicsEngine.simulate((float)frameTime);
        physicsEngine.handleCollisions((float)frameTime);
    }
}
