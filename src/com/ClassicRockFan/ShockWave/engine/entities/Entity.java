package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.administrative.Logging;
import com.ClassicRockFan.ShockWave.engine.administrative.Naming;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsComponent;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

import java.util.ArrayList;

public class Entity extends Object{

    private Transform transform;
    private ArrayList<EntityComponent> components;
    private CoreEngine engine;
    private String name;
    private EntityManager entityManager;
    private int id;
    private boolean hasPhysics;
    private PhysicsComponent physicsComponent;

    public Entity(){
        this.name = Naming.getReccomendedName(this);
    }

    public Entity(String name) {
        this.name = name;
    }

    public void init(CoreEngine engine){
        this.transform = new Transform();
        this.components = new ArrayList<EntityComponent>();
        this.hasPhysics = false;
        this.engine = engine;
        this.entityManager = engine.getEntityManager();
        this.physicsComponent = null;
    }

    public void load(){
        Logging.printLog("Loading an entity named: " + name);
    }

    public Entity addComponent(EntityComponent component){
        components.add(component);
        component.setParent(this);
        return this;
    }

    public Entity setPhysicsComponent(PhysicsComponent component){
        component.setParent(this);
        this.setPhysicsComponent(component);
        hasPhysics = true;
        return this;
    }

    public Entity removeComponent(EntityComponent component){
        components.remove(component);
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isHasPhysics() {
        return hasPhysics;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }
}
