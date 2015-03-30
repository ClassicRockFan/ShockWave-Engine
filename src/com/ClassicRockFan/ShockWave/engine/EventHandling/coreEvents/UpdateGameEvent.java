package com.ClassicRockFan.ShockWave.engine.EventHandling.coreEvents;


import com.ClassicRockFan.ShockWave.engine.EventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.EventHandling.core.EventTyping;
import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.Game;

public class UpdateGameEvent extends Event{

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
