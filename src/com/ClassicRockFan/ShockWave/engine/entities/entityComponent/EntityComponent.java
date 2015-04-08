package com.ClassicRockFan.ShockWave.engine.entities.entityComponent;


import com.ClassicRockFan.ShockWave.engine.administrative.Naming;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public abstract class EntityComponent{

    private Entity parent;
    private String name;

    public EntityComponent(){
        this.name = Naming.getReccomendedName(this.getClass());
    }

    public EntityComponent(String name) {
        this.name = name;
    }

    public void input(float delta) {
    }

    public void update(float delta) {
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
    }


    public Transform getTransform() {
        return parent.getTransform();
    }

    public void setParent(Entity parent){
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }
}
