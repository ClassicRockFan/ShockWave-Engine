package com.ClassicRockFan.ShockWave.engine.EventHandling.core;


import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;

public class Event {

    private int type;
    private int logLevel;
    private String message;

    public Event(int type) {
        this(type, Logging.LEVEL_NULL);
    }

    public Event(int type, int logLevel) {
        this(type, null, logLevel);
    }

    public Event(int type, String message, int logLevel) {
        this.type = type;
        this.message = message;
        this.logLevel = logLevel;
    }

    public void handle(double frameTime) {
        Logging.printLog(getMessage(), logLevel);
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        if (message != null)
            return message;
        else
            return "An Event of type " + type + " has not registered a message.  Please fix this issue for proper logging";
    }

    public void setMessage(String message) {
        this.message = message;
    }
}