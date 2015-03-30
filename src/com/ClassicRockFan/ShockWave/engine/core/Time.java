package com.ClassicRockFan.ShockWave.engine.core;

/**
 * Created by Tyler on 3/19/2015.
 */
public class Time {
    private static final long SECOND = (long) 1000000000.0;

    public static double getTime() {
        return (double) System.nanoTime() / (double) SECOND;
    }
}
