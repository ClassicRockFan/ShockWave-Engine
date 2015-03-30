package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;

public abstract class Game {

    public void init() {
        Logging.printLog("Initializing the game.");
    }
}
