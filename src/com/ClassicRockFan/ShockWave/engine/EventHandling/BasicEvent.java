package com.ClassicRockFan.ShockWave.engine.EventHandling;


public class BasicEvent implements iEvent{

    private int type;
    private String errorMessage;

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
