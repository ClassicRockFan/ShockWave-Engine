package com.ClassicRockFan.ShockWave.engine.components.coreComponents;

import com.base.engine.components.rendering.FrustumCuller;
import com.base.engine.core.CoreEngine;
import com.base.engine.core.Transform;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

import java.util.ArrayList;

public class GameObject {

    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;
    private CoreEngine engine;
    private boolean hasComponents;
    private GameObject parent;

    public GameObject() {
        children = new ArrayList<GameObject>();
        components = new ArrayList<GameComponent>();
        transform = new Transform();
        engine = null;
        hasComponents = false;
    }

    public GameObject addChild(GameObject child) {
        children.add(child);
        child.setParent(this);
        child.setEngine(engine);
        child.getTransform().setParent(transform);

        return this;
    }

    public GameObject addComponent(GameComponent component) {
        components.add(component);
        component.setParent(this);

        return this;
    }

    public void inputAll(float delta) {
        input(delta);
        for (GameObject child : children) {
            child.inputAll(delta);
        }
    }

    public void updateAll(float delta) {
        update(delta);
        for (GameObject child : children) {
            child.updateAll(delta);
        }
    }

    public void renderAll(Shader shader, RenderingEngine renderingEngine) {
        render(shader, renderingEngine);
        for (GameObject child : children) {
            child.renderAll(shader, renderingEngine);
        }
    }

    public void input(float delta) {
        transform.update();

        for (GameComponent component : components) {
            component.input(delta);
        }
    }

    public void update(float delta) {
        for (GameComponent component : components) {
            component.update(delta);
        }
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        for (GameComponent component : components) {
            component.render(shader, renderingEngine);
        }
    }

    public ArrayList<GameObject> getAllAttached() {
        ArrayList<GameObject> result = new ArrayList<GameObject>();
        for (GameObject child : children) {
            result.addAll(child.getAllAttached());
        }
        result.add(this);
        return result;
    }


    public Transform getTransform() {
        return transform;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        if (this.engine != engine) {
            this.engine = engine;

            for (GameComponent component : components) {
                component.addToEngine(engine);
            }
            for (GameObject child : children) {
                child.setEngine(engine);
            }
        }
    }

    public boolean isHasComponents() {
        return hasComponents;
    }

    public void setHasComponents(boolean hasComponents) {
        this.hasComponents = hasComponents;
    }

    public GameObject getParent() {
        if(parent != null)
            return parent;
        else
            return null;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

}
