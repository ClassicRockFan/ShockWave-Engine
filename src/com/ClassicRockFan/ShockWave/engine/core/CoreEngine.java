package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.eventHandling.core.EventManager;
import com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.coreEvents.InputGameEvent;
import com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.coreEvents.UpdateGameEvent;
import com.ClassicRockFan.ShockWave.engine.eventHandling.handlers.physicsEvents.PhysicsOccurenceEvent;
import com.ClassicRockFan.ShockWave.engine.administrative.ConsoleWindow;
import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.administrative.StateManager;
import com.ClassicRockFan.ShockWave.engine.entities.EntityManager;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Window;
import game.LauncherWindow;

public class CoreEngine {
    private static ConsoleWindow console = new ConsoleWindow();
    private boolean isRunning;
    private Game game;
    private int width;
    private int height;
    private String title;
    private double frameTime;
    //Secondary Engines
    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;

    //Managers
    private static StateManager stateManager = new StateManager(StateManager.STATE_INIT);
    private EventManager eventManager;
    private EntityManager entityManager;

    private ProfileTimer sleepTimer = new ProfileTimer();
    private ProfileTimer windowSyncTimer = new ProfileTimer();

    public CoreEngine(int width, int height, double frameCap, Game game, String title) {
        //Setup
        this.isRunning = false;
        this.width = width;
        this.height = height;
        this.title = title;
        this.frameTime = 1 / frameCap;
        this.game = game;
        this.game.setEngine(this);

        //Managers
        this.eventManager = new EventManager();
        this.entityManager = new EntityManager(this);
    }

    public static ConsoleWindow getConsole() {
        return console;
    }

    public void createConsole() {
        console.setVisible(true);
    }

    public void start() {
        if (isRunning) {
            return;
        }
        System.out.println("Starting Core functionality");
        console.addConsoleText("Starting Core functionality");
        run();
    }

    public void stop() {

        if (!isRunning) {
            return;
        }

        isRunning = false;
        System.out.println("Stopping CoreEngine");
        console.addConsoleText("Stopping the CoreEngine");
    }

    private void run() {
        ProfileTimer eventTimer = new ProfileTimer();
        while(true) {
            if (stateManager.getCurrentState() == StateManager.STATE_RUNNING) {
                System.out.println("Starting the CoreEngine");
                console.addConsoleText("Starting CoreEngine");

                System.out.println("Creating Game Window");
                console.addConsoleText("Creating Game Window");

                Window.createWindow(width, height, title);

                this.physicsEngine = new PhysicsEngine(this);
                this.renderingEngine = new RenderingEngine(this);
                isRunning = true;
                int frames = 0;
                double frameCounter = 0;

                game.init(renderingEngine, physicsEngine, this);

                double lastTime = Time.getTime();
                double unprocessedTime = 0;

                while (isRunning) {
                    boolean render = false;

                    double startTime = Time.getTime();
                    double passedTime = startTime - lastTime;
                    lastTime = startTime;

                    unprocessedTime += passedTime;
                    frameCounter += passedTime;

                    while (unprocessedTime > frameTime) {
                        render = true;

                        unprocessedTime -= frameTime;

                        if (Window.isCloseRequested()) {
                            stop();
                        }


                        eventTimer.startInvocation();
                        eventManager.addEvent(new InputGameEvent(game));
                        eventManager.addEvent(new PhysicsOccurenceEvent(physicsEngine));
                        eventManager.addEvent(new UpdateGameEvent(game));

                        eventManager.doEvents(frameTime);
                        eventTimer.stopInvocation();

                        if (frameCounter >= 1.0) {
                            double totalTime = (1000.0 * frameCounter) / (double) frames;
                            double recordedTime = 0;

                            console.addConsoleText("");
                            console.addConsoleText("Running at ", 55, frames + " FPS");
                            recordedTime += eventTimer.displayAndReset("Event Handling: ", (double) frames);
                            double timeRendering = renderingEngine.dislayRenderTime((double)frames);
                            recordedTime += timeRendering;
                            double sleepTime = displaySleepTime((double) frames);
                            recordedTime += sleepTime;
                            recordedTime += displayWindowSyncTime((double) frames);
                            System.out.println("Other Time: " + (totalTime - recordedTime) + " ms");
                            System.out.println("Total Time: " + (totalTime) + " ms");
                            System.out.println("");

                            frames = 0;
                            frameCounter = 0;

                        }
                    }

                    if (render) {
                        game.render(this, renderingEngine);
                        windowSyncTimer.startInvocation();
                        Window.render();
                        windowSyncTimer.stopInvocation();
                        frames++;
                    } else {
                        try {
                            sleepTimer.startInvocation();
                            Thread.sleep(1);
                            sleepTimer.stopInvocation();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                cleanUp();
            } else if (stateManager.getCurrentState() == StateManager.STATE_INIT) {
                LauncherWindow launcherWindow = new LauncherWindow(width, height, title, "LoadScreen.jpg", this);
                stateManager.setCurrentState(StateManager.STATE_MENU);

            } else if (stateManager.getCurrentState() == StateManager.STATE_MENU) {
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                    System.err.println("Error waiting on the main loading screen");

                    System.exit(1);
                }
            }
        }
    }

    private void cleanUp() {
        System.out.println("In Cleanup");
        Window.dispose();
        System.gc();
        System.exit(1);
    }

    public double displaySleepTime(double dividend) {
        return sleepTimer.reset(dividend);
    }

    public double displayWindowSyncTime(double dividend) {
        return windowSyncTimer.displayAndReset("Window Render Time: ", dividend);
    }

    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public static StateManager getStateManager() {
        return stateManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public EntityManager getEntityManager() { return entityManager; }
}
