package com.ClassicRockFan.ShockWave.engine.EventHandling;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class EventManager {

    private CoreEngine engine;
    private ArrayList<Event> events;

    public EventManager(CoreEngine engine) {
        this.events = new ArrayList<Event>();
        this.engine = engine;
    }

    public void doEvents(){
        for(int i = 0; i < events.size(); i++)
            events.get(i).handle();
    }

    public void addEvent(Event event){events.add(event);}
    private void clearEventsList(){events.clear();}
    public ArrayList<Event> getEvents() {return events;}
}
