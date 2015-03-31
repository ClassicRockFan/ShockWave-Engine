package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.HashMap;

public class CharacterManager {

    private HashMap<Character, Integer> initializedCharacters;
    private HashMap<Character, Integer> loadedCharacters;
    private CoreEngine engine;

    public CharacterManager(CoreEngine engine) {
        initializedCharacters = new HashMap<Character, Integer>();
        loadedCharacters = new HashMap<Character, Integer>();
        this.engine = engine;
    }

    public void registerCharacter(Character character){
        initializedCharacters.put(character, initializedCharacters.size());
    }

    public void loadCharacter(Character character){
        if(!initializedCharacters.containsKey(character))
            registerCharacter(character);
        loadCharacterData(character);
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

    public boolean loaded(Character character){
        return loadedCharacters.containsKey(character);
    }

    public HashMap<Character, Integer> getRegisteredCharacters() {
        return initializedCharacters;
    }

    public HashMap<Character, Integer> getLoadedCharacters() {
        return loadedCharacters;
    }

    public CoreEngine getEngine() {
        return engine;
    }
}
