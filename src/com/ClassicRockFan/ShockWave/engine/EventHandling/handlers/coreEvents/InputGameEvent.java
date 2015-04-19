package com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.custom;



        import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.core.Game;
import com.ClassicRockFan.ShockWave.engine.core.Input;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.Event;
import com.ClassicRockFan.ShockWave.engine.eventHandling.core.EventTyping;

public class InputGameEvent extends Event {

    private Game game;

    public InputGameEvent(Game game) {
        super(EventTyping.INPUT_NECESSARY_EVENT_TYPE, "", Logging.LEVEL_NULL);
        this.game = game;
    }

    @Override
    public void handle(double frameTime) {
        super.handle(frameTime);
        game.input((float) frameTime);
        Input.update();
    }
}
