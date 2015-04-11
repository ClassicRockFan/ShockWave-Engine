package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class CommonManager {

    private ArrayList<Entity> initializedEntities;
    private ArrayList<Entity> loadedEntities;
    private CoreEngine engine;
    private int numEntities;

    public CommonManager(CoreEngine engine) {
        this.initializedEntities = new ArrayList<Entity>();
        this.loadedEntities = new ArrayList<Entity>();
        this.engine = engine;
        this.numEntities = 0;
    }

    public void register(Entity entity){
        if(!initializedEntities.contains(entity)) {
            initializedEntities.add(entity);
            entity.init(engine);
            entity.setId(getInstance());
        }
    }

    public void load(Entity entity){
        if(!initializedEntities.contains(entity))
            register(entity);
        if(!loadedEntities.contains(entity)) {
            entity.load();
            loadedEntities.add(entity);
        }
    }

    public void unloadAll(){
        loadedEntities.clear();
    }
    public void unload(Entity entity){
        if(isLoaded(entity))loadedEntities.remove(entity);
    }

    public boolean isLoaded(Entity entity){
        return loadedEntities.contains(entity);
    }

    public ArrayList<Entity> getRegisteredEntities() {
        return initializedEntities;
    }
    public ArrayList<Entity> getLoadedEntities() {
        return loadedEntities;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public int getInstance(){
        numEntities += 1;
        return numEntities;
    }
}
