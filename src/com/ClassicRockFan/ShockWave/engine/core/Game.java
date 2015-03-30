package com.ClassicRockFan.ShockWave.engine.core;


import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameObject;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;

public abstract class Game {
    private GameObject root;

    private ProfileTimer inputTimer = new ProfileTimer();
    private ProfileTimer updateTimer = new ProfileTimer();

    RenderingEngine renderingEngine;
    PhysicsEngine physicsEngine;

    public double dislayInputTime(double dividend) {
        return inputTimer.displayAndReset("Input time:  ", dividend);
    }

    public double dislayUpdateTime(double dividend) {
        return updateTimer.displayAndReset("Update time:  ", dividend);
    }

    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine) {
        this.renderingEngine = renderingEngine;
        this.physicsEngine = physicsEngine;
    }

    public void input(float delta) {
        inputTimer.startInvocation();
        getRootObject().inputAll(delta);
        inputTimer.stopInvocation();
    }

    public void update(float delta) {
        updateTimer.startInvocation();
        getRootObject().updateAll(delta);
        updateTimer.stopInvocation();
    }

    public void render(RenderingEngine renderingEngine) {
        renderingEngine.render(getRootObject());
    }

    public void addObject(GameObject object) {
        getRootObject().addChild(object);
    }
    public void addPhysicsObject(PhysicsObject object){
        getRootObject().addChild((GameObject)object);
        physicsEngine.addPhysicsObject(object);
    }

    private GameObject getRootObject() {
        if (root == null) {
            root = new GameObject();
        }
        return root;
    }

    public void setEngine(CoreEngine engine) {
        getRootObject().setEngine(engine);
    }

    public RenderingEngine getRenderingEngine() {
        return renderingEngine;
    }

    public void setRenderingEngine(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }
}
