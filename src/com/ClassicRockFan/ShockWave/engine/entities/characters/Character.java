package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public abstract class Character extends Entity{

    private CharacterManager characterManager;
    private Transform transform;
    private CoreEngine engine;

    public Character(CoreEngine engine) {
        super(engine);
        this.characterManager = engine.getCharacterManager();
        characterManager.registerCharacter(this);
        init();
    }

    public CharacterManager getCharacterManager() {
        return characterManager;
    }

}
