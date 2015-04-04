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

    //Item management
    public void registerItem(Item item){
        initializedItems.add(item);
        item.init();
        Logging.printLog("Registering an item");
    }

    public void loadItem(Item item){
        if(!initializedItems.contains(item))
            registerItem(item);
        loadItemData(item);
    }

    private void loadItemData(Item item) {
        item.load();
    }

    public void unloadAllItems(){
        loadedItems.clear();
    }

    public void unload(Item item){
        loadedItems.remove(item);
    }

    public boolean isLoaded(Item item){
        return loadedItems.contains(item);
    }

    public ArrayList<Item> getRegisteredItems() {
        return initializedItems;
    }

    public ArrayList<Item> getLoadedItems() {
        return loadedItems;
    }

    //Character Management
    public void registerCharacter(Character character){
        initializedCharacters.add(character);
        character.init();
        Logging.printLog("Registering a character");
    }

    public void loadCharacter(Character character){
        if(!isLoaded(character))
            registerCharacter(character);
        loadCharacterData(character);
        loadedCharacters.add(character);
    }

    private void loadCharacterData(Character character) {
        character.load();
    }

    public void unloadAll(){
        loadedCharacters.clear();
    }

    public void unload(Character character){
        loadedCharacters.remove(character);
    }

    public boolean isLoaded(Character character){
        return loadedCharacters.contains(character);
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
