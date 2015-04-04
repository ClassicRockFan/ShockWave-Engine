package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Item> initializedItems;
    private ArrayList<Item> loadedItems;
    private ArrayList<com.ClassicRockFan.ShockWave.engine.entities.characters.Character> initializedCharacters;
    private ArrayList<Character> loadedCharacters;
    private CoreEngine engine;

    public EntityManager(CoreEngine engine) {
        initializedCharacters = new ArrayList<Character>();
        loadedCharacters = new ArrayList<Character>();
        initializedItems = new ArrayList<Item>();
        loadedItems = new ArrayList<Item>();
        this.engine = engine;
    }

    //Registration
    public void register(Item item){
        initializedItems.add(item);
        item.init();
        Logging.printLog("Registering an item");
    }

    public void register(Character character){
        initializedCharacters.add(character);
        character.init();
        Logging.printLog("Registering a character");
    }

    //Loading
    public void load(Item item){
        if(!initializedItems.contains(item))
            register(item);
        loadData(item);
    }

    public void load(Character character){
        if(!isLoaded(character))
            register(character);
        loadData(character);
        loadedCharacters.add(character);
    }

    //Load data
    private void loadData(Entity entity) {
        entity.load();
    }

    //Unloading
    public void unloadAllItems(){
        loadedItems.clear();
    }
    public void unload(Item item){
        if(isLoaded(item))loadedItems.remove(item);
    }
    public void unloadAllCharacters(){
        loadedCharacters.clear();
    }
    public void unload(Character character){
        if(isLoaded(character))loadedCharacters.remove(character);
    }

    //Checking
    public boolean isLoaded(Item item){
        return loadedItems.contains(item);
    }
    public boolean isLoaded(Character character){
        return loadedCharacters.contains(character);
    }

    //Getting
    public ArrayList<Item> getRegisteredItems() {
        return initializedItems;
    }
    public ArrayList<Item> getLoadedItems() {
        return loadedItems;
    }
    public ArrayList<Character> getRegisteredCharacters() {
        return initializedCharacters;
    }
    public ArrayList<Character> getLoadedCharacters() {
        return loadedCharacters;
    }

    //EntityManager functionality
    public CoreEngine getEngine() {
        return engine;
    }
}
