package com.ClassicRockFan.ShockWave.engine.entities.characters;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;

public class Character extends Entity{

    private int baseHealth;
    private int health;

    public Character(){
        this(50);
    }

    public Character(int baseHealth){
        this(baseHealth, baseHealth);
    }

    public Character(int baseHealth, int health){
        super();
        this.baseHealth = baseHealth;
        this.health = health;
    }

    public Character(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
    }

    @Override
    public void init(CoreEngine engine) {
        super.init(engine);
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getHealth() {
        return health;
    }

    public void adjustHealth(int changedHealth){
        this.health+=changedHealth;
    }
}
