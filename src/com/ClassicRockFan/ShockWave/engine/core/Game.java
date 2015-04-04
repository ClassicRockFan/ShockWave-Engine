package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;

import java.util.ArrayList;

public abstract class Game {

    private Entity root;

    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    private CoreEngine engine;

    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine, CoreEngine engine) {
        this.engine = engine;
        this.renderingEngine = renderingEngine;
        this.physicsEngine = physicsEngine;
    }

    public void input(float delta) {
        ArrayList<Character> loadedCharacters = engine.getEntityManager().getLoadedCharacters();
        ArrayList<Item> loadedItems = engine.getEntityManager().getLoadedItems();

        for(int i = 0; i < loadedCharacters.size(); i ++)
            loadedCharacters.get(i).input(delta);

        for (int i = 0; i < loadedItems.size(); i++)
            loadedItems.get(i).input(delta);
    }

    public void update(float delta) {
        ArrayList<Character> loadedCharacters = engine.getEntityManager().getLoadedCharacters();
        ArrayList<Item> loadedItems = engine.getEntityManager().getLoadedItems();

        for(int i = 0; i < loadedCharacters.size(); i ++)
            loadedCharacters.get(i).update(delta);

        for (int i = 0; i < loadedItems.size(); i++)
            loadedItems.get(i).update(delta);
    }

    public void render(CoreEngine engine, RenderingEngine renderingEngine) {
        renderingEngine.render(engine);
    }


    private Entity getRootEntity() {
        if (root == null) {
            root = new Entity("rootEntity");
        }
        return root;
    }

    public void addItem(Item item){
        engine.getEntityManager().loadItem(item);
    }

    public void addCharacter(Character character){
        engine.getEntityManager().loadCharacter(character);
        Logging.printLog("Adding A Character!!!", Logging.LEVEL_INFO);
    }

    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }
}
