package com.ClassicRockFan.ShockWave.engine.administrative.logging;


import com.ClassicRockFan.ShockWave.engine.core.Time;

public class Logging {

    public static final int LEVEL_ERROR = -1;
    public static final int LEVEL_INFO = 0;

    public static void printLog(String message){
        printLog(message, LEVEL_INFO);
    }

    public static void printLog(String message, int level){
        if(level == LEVEL_ERROR)
            System.err.println(Time.getFormatTime() + " - " + message);
        else if (level == LEVEL_INFO)
            System.out.println(Time.getFormatTime() + " - " + message);
        else {
            System.err.println("THERE WAS AN INVALID TYPE ENTERED.  PRINTING THE MESSAGE ANYWAY BELOW.");
            System.out.println(Time.getFormatTime() + " - " + message);
        }
    }

}
