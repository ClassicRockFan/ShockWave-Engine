package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.core.math.Quaternion;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.CommonManager;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.characters.player.Player;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Window;

import java.util.ArrayList;

public abstract class Game {

    private Entity root;

    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    private CoreEngine engine;
    private CommonManager entityManager;

    private Player player;

    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine, CoreEngine engine) {
        this.engine = engine;
        this.renderingEngine = renderingEngine;
        this.physicsEngine = physicsEngine;

        this.entityManager = new CommonManager(engine);

        EntityCamera entityCamera = new EntityCamera((float) Math.toRadians(70.0f), (float) Window.getWidth() / Window.getHeight(), 0.01f, 500.0f);
        this.player = new Player(entityCamera, 8, 0.5f, 10);
        player.getTransform().getPos().set(0,0,5);
        player.getTransform().getRot().set(new Quaternion(new Vector3f(0,1,0), Math.toRadians(180)));

        addEntity(player);
    }

    public void input(float delta) {
        ArrayList<Entity> loadedEntities = entityManager.getLoadedEntities();

        for (int i = 0; i < loadedEntities.size(); i++)
            loadedEntities.get(i).inputAll(delta);
    }

    public void update(float delta) {
        ArrayList<Entity> loadedEntities = entityManager.getLoadedEntities();

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

    public Entity addEntity(Entity entity) {
        entityManager.load(entity);

        return entity;
    }

    public Entity unloadEntity(Entity entity){
        entityManager.unload(entity);
        return entity;
    }


    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    public PhysicsEngine getPhysicsEngine() {
        return physicsEngine;
    }

    public Player getPlayer() {
        return player;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public CommonManager getEntityManager() {
        return entityManager;
    }
}
