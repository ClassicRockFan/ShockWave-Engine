package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

import java.util.ArrayList;

public class CharacterManager {

    private ArrayList<Character> initializedCharacters;
    private ArrayList<Character> loadedCharacters;
    private CoreEngine engine;

    public CharacterManager(CoreEngine engine) {
        initializedCharacters = new ArrayList<Character>();
        loadedCharacters = new ArrayList<Character>();
        this.engine = engine;
    }

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

    public CoreEngine getEngine() {
        return engine;
    }
}
