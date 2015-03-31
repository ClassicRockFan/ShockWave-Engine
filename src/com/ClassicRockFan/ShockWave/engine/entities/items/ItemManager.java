package com.ClassicRockFan.ShockWave.engine.entities.items;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.HashMap;

public class ItemManager {

    private HashMap<Item, Integer> initializedItems;
    private HashMap<Item, Integer> loadedItems;
    private CoreEngine engine;


    public ItemManager(CoreEngine engine) {
        initializedItems = new HashMap<Item, Integer>();
        loadedItems = new HashMap<Item, Integer>();
        this.engine = engine;
    }

    public void registerItem(Item item){
        initializedItems.put(item, initializedItems.size());
    }

    public void loadItem(Item item){
        if(!initializedItems.containsKey(item))
            registerItem(item);
        loadCharacterData(item);
    }

    private void loadCharacterData(Item item) {
        item.load();
    }

    public void unloadAll(){
        loadedItems.clear();
    }

    public void unload(Item item){
        loadedItems.remove(item);
    }

    public boolean loaded(Item item){
        return loadedItems.containsKey(item);
    }

    public HashMap<Item, Integer> getRegisteredCharacters() {
        return initializedItems;
    }

    public HashMap<Item, Integer> getLoadedCharacters() {
        return loadedItems;
    }

    public CoreEngine getEngine() {
        return engine;
    }
}
