package com.ClassicRockFan.ShockWave.engine.eventHandling.core;

import java.util.ArrayList;

public class EventManager {

    private ArrayList<Event> events;

    public EventManager() {
        this.events = new ArrayList<Event>();
    }

    public void doEvents(double frameTime) {
        for (int i = 0; i < events.size(); i++)
            events.get(i).handle(frameTime);

        events.clear();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    private void clearEventsList() {
        events.clear();
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
