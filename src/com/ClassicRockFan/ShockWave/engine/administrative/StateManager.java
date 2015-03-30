package com.ClassicRockFan.ShockWave.engine.administrative;

/**
 * Created by Tyler on 3/19/2015.
 */
public class StateManager {

    public static final int STATE_INIT = 0;
    public static final int STATE_MENU = 1;
    public static final int STATE_RUNNING = 2;

    private static int currentState = STATE_INIT;

    public StateManager(int currentStateIn) {
        currentState = currentStateIn;
    }

    public static int getCurrentState() {return currentState;}
    public static void setCurrentState(int currentState) {StateManager.currentState = currentState;}
}
