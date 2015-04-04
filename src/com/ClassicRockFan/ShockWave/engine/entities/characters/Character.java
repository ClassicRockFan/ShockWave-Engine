package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Character extends Entity{

    private CharacterManager characterManager;

    public Character(CoreEngine engine, String name) {
        super(name);
        this.characterManager = engine.getCharacterManager();
        characterManager.registerCharacter(this);
    }

    public CharacterManager getCharacterManager() {
        return characterManager;
    }

}
