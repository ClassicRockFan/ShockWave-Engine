package com.ClassicRockFan.ShockWave.engine.components.coreComponents;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public abstract class GameComponent {
    private GameObject parent;

    public void input(float delta) {
    }

    public void update(float delta) {
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
    public GameObject getParent() {return parent;}

    public Transform getTransform() {
        return parent.getTransform();
    }

    public void addToEngine(CoreEngine engine) {}

}
