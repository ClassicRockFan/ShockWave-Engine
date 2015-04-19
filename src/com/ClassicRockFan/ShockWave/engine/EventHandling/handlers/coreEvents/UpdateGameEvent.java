package com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.coreEvents;


import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.core.Game;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.EventTyping;

public class UpdateGameEvent extends Event {

    private Game game;

    public UpdateGameEvent(Game game) {
        super(EventTyping.UPDATE_NECESSSARY_EVENT_TYPE, "", Logging.LEVEL_NULL);
        this.game = game;
    }

    @Override
    public void handle(double frameTime) {
        super.handle(frameTime);
        game.update((float) frameTime);
    }

}
