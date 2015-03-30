package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.EventHandling.EventManager;
import com.ClassicRockFan.ShockWave.engine.administrative.StateManager;
import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;

public class CoreEngine {

    private EventManager eventManager;
    private StateManager stateManager;
    private Game game;

    private double frameTime;

    public CoreEngine(Game game, double frameCap) {
        this.game = game;
        this.frameTime = frameCap;
        this.eventManager = new EventManager(this);
        this.stateManager = new StateManager(StateManager.STATE_INIT);

    }

    public void start() {
        if (stateManager.getCurrentState() != StateManager.STATE_INIT)
            return;

        Logging.printLog("Starting the CoreEngine");
        stateManager.setCurrentState(StateManager.STATE_RUNNING);
        run();
    }

    public void stop() {
        if (stateManager.getCurrentState() != StateManager.STATE_RUNNING)
            return;
    }

    public void run() {

        if (stateManager.getCurrentState() == StateManager.STATE_RUNNING) {
            game.init();
            while (true) {
                int frames = 0;
                double frameCounter = 0;

                double lastTime = Time.getTime();
                double unprocessedTime = 0;

                boolean render = false;

                double startTime = Time.getTime();
                double passedTime = startTime - lastTime;
                lastTime = startTime;

                unprocessedTime += passedTime;
                frameCounter += passedTime;

                while (unprocessedTime > frameTime) {
                    render = true;

                    unprocessedTime -= frameTime;

//                    if (Window.isCloseRequested()) {
//                        stop();
//                    }

                    if (frameCounter >= 1.0) {
                        double totalTime = (1000.0 * frameCounter) / (double) frames;
                        double recordedTime = 0;
                        System.out.println("Other Time: " + (totalTime - recordedTime) + " ms");
                        System.out.println("Total Time: " + (totalTime) + " ms");
                        System.out.println("");
                        frames = 0;
                        frameCounter = 0;
                    }
                }

                if (render) {
                    frames++;

                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (stateManager.getCurrentState() != StateManager.STATE_RUNNING)
                    break;
            }
        }
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }
}
