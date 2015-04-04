package com.ClassicRockFan.ShockWave.engine.entities;


import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

import java.util.ArrayList;

public class Entity {

    private Transform transform;
    private ArrayList<EntityComponent> components;
    private ArrayList<Item> connectedItems;
    private String name;

    public Entity(String name) {
        this.name = name;
        this.connectedItems = new ArrayList<Item>();
        this.transform = new Transform();
        this.components = new ArrayList<EntityComponent>();
    }

    public void init(){
        Logging.printLog("Initializing some entity");
    }
    public void load(){
        Logging.printLog("Loading some Entity");
    }

    public Entity addComponent(EntityComponent component){
        components.add(component);
        component.setParent(this);
        return this;
    }

    public Item addComponent(Item item){
        connectedItems.add(item);
        return item;
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
}
