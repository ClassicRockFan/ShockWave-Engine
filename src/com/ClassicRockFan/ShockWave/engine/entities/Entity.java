package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

import java.util.ArrayList;

public class Entity {

    private Transform transform;
    private ArrayList<EntityComponent> components;
    private CoreEngine engine;
    private String name;
    private EntityManager entityManager;

    public Entity(String name) {
        this.name = name;
        this.transform = new Transform();
        this.components = new ArrayList<EntityComponent>();
    }

    public void init(CoreEngine engine){
        this.engine = engine;
        this.entityManager = engine.getEntityManager();
    }

    public void load(){

    }

    public Entity addComponent(EntityComponent component){
        components.add(component);
        component.setParent(this);
        return this;
    }

    public void input(float delta) {
        transform.update();

        for (int i = 0; i < components.size(); i++) {
            components.get(i).input(delta);
        }
    }

    public void update(float delta) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).update(delta);
        }
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).render(shader, renderingEngine);
        }
    }

    public Transform getTransform() {
        return transform;
    }
    public void setTransform(Transform transform) {
        this.transform = transform;
    }
    public ArrayList<EntityComponent> getComponents() {
        return components;
    }
    public String getName() {
        return name;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
