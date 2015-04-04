package com.ClassicRockFan.ShockWave.engine.entities.items;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class ItemManager {

    private ArrayList<Item> initializedItems;
    private ArrayList<Item> loadedItems;
    private CoreEngine engine;


    public ItemManager(CoreEngine engine) {
        initializedItems = new ArrayList<Item>();
        loadedItems = new ArrayList<Item>();
        this.engine = engine;
    }

    public void registerItem(Item item){
        initializedItems.add(item);
    }

    public void loadItem(Item item){
        if(!initializedItems.contains(item))
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
        return loadedItems.contains(item);
    }

    public ArrayList<Item> getRegisteredItems() {
        return initializedItems;
    }

    public ArrayList<Item> getLoadedItems() {
        return loadedItems;
    }

    public CoreEngine getEngine() {
        return engine;
    }
}
