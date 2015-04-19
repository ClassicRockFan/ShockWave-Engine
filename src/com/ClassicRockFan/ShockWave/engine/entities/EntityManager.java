package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class EntityManager {
    //In order to add more types of entities to the manager, just
    // add a CommonManager for it
    //add a getter for it
    //add it to the getAllLoadedEntites() 'for' loops

    private CoreEngine engine;
    private CommonManager itemManager;
    private CommonManager characterManager;
    private CommonManager lightManager;

    public EntityManager(CoreEngine engine) {
        this.engine = engine;
        this.itemManager = new CommonManager(engine);
        this.characterManager = new CommonManager(engine);
        this.lightManager = new CommonManager(engine);
    }

    public ArrayList<Entity> getAllLoadedEntites() {
        ArrayList<Entity> result = new ArrayList<Entity>();


        for (int i = 0; i < itemManager.getLoadedEntities().size(); i++)
            result.add(itemManager.getLoadedEntities().get(i));

        for (int i = 0; i < characterManager.getLoadedEntities().size(); i++)
            result.add(characterManager.getLoadedEntities().get(i));

        for (int i = 0; i < lightManager.getLoadedEntities().size(); i++)
            result.add(lightManager.getLoadedEntities().get(i));

        return result;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public CommonManager getItemManager() {
        return itemManager;
    }

    public CommonManager getCharacterManager() {
        return characterManager;
    }

    public CommonManager getLightManager() {
        return lightManager;
    }
}
