package com.base.game;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

public class Main {

    public static void main(String[] args){

        System.out.println("program started");

        CoreEngine engine = new CoreEngine(60);
        engine.start();

        System.out.println("program ended");
    }

}
