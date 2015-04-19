package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;
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
        ArrayList<Entity> loadedEntities = engine.getEntityManager().getAllLoadedEntites();

        for (int i = 0; i < loadedEntities.size(); i++)
            loadedEntities.get(i).inputAll(delta);
    }

    public void update(float delta) {
        ArrayList<Entity> loadedEntities = engine.getEntityManager().getAllLoadedEntites();

        for (int i = 0; i < loadedEntities.size(); i++)
            loadedEntities.get(i).updateAll(delta);
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

    public void addItem(Item item) {
        engine.getEntityManager().getItemManager().load(item);
        Logging.printLog("Adding an Item to the game!!!", Logging.LEVEL_INFO);
    }

    public void addCharacter(Character character) {
        engine.getEntityManager().getCharacterManager().load(character);
        Logging.printLog("Adding A Character to the game!!!", Logging.LEVEL_INFO);
    }

    public void addLight(Light light) {
        engine.getEntityManager().getLightManager().load(light);
        Logging.printLog("Adding a Light to the game!!!", Logging.LEVEL_INFO);
    }

    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }
}
