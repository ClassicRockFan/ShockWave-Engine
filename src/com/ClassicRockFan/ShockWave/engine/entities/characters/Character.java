package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.EntityManager;

public class Character extends Entity{

    private EntityManager entityManager;

    public Character(CoreEngine engine, String name) {
        super(name);
        this.entityManager = engine.getEntityManager();
        entityManager.registerCharacter(this);
    }

    public EntityManager getCharacterManager() {
        return entityManager;
    }

}
