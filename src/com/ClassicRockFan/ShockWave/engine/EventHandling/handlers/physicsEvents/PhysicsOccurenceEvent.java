package com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.physicsEvents;


import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.EventTyping;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;

public class PhysicsOccurenceEvent extends Event {

    private PhysicsEngine physicsEngine;

    public PhysicsOccurenceEvent(PhysicsEngine physicsEngine) {
        super(EventTyping.PHYSICS_OCCURENCE_EVENT_TYPE, "", Logging.LEVEL_NULL);
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void handle(double frameTime) {
        physicsEngine.doPhyiscs((float) frameTime);
    }
}
