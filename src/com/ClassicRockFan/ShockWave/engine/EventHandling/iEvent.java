package com.ClassicRockFan.ShockWave.engine.EventHandling;


public interface iEvent {

    public int type = -1;
    public String errorMessage = "";

    public int getType();
    public String getErrorMessage();
    public void setType(int type);
    public void setErrorMessage(String errorMessage);

}
