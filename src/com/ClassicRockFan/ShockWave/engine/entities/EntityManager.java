package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;

import java.util.ArrayList;

public class EntityManager {

    private ArrayList<Item> initializedItems;
    private ArrayList<Item> loadedItems;
    private ArrayList<Character> initializedCharacters;
    private ArrayList<Character> loadedCharacters;
    private ArrayList<Light> initializedLights;
    private ArrayList<Light> loadedLights;
    private CoreEngine engine;

    public EntityManager(CoreEngine engine) {
        initializedCharacters = new ArrayList<Character>();
        loadedCharacters = new ArrayList<Character>();
        initializedItems = new ArrayList<Item>();
        loadedItems = new ArrayList<Item>();
        initializedLights = new ArrayList<Light>();
        loadedLights = new ArrayList<Light>();
        this.engine = engine;
    }

    //Registration
    public void register(Item item){
        initializedItems.add(item);
        item.init(engine);
    }

    public void register(Character character){
        initializedCharacters.add(character);
        character.init(engine);
    }

    public void register(Light light){
        initializedLights.add(light);
        light.init(engine);
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

    public void load(Light light){
        if(!isLoaded(light))
            register(light);
        loadData(light);
        loadedLights.add(light);
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
    public void unloadAllLights(){
        loadedLights.clear();
    }
    public void unload(Light light){
        if(isLoaded(light))loadedLights.remove(light);
    }

    //Checking
    public boolean isLoaded(Item item){
        return loadedItems.contains(item);
    }
    public boolean isLoaded(Character character){
        return loadedCharacters.contains(character);
    }
    public boolean isLoaded(Light light){
        return loadedCharacters.contains(light);
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
    public ArrayList<Light> getRegisteredLights() {
        return initializedLights;
    }
    public ArrayList<Light> getLoadedLights() {
        return loadedLights;
    }

    //EntityManager functionality
    public CoreEngine getEngine() {
        return engine;
    }
}
