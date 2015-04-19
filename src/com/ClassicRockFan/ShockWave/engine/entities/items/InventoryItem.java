package com.ClassicRockFan.ShockWave.engine.entities.items;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;
import com.ClassicRockFan.ShockWave.engine.rendering.Texture;

public class InventoryItem extends Item{

    private int value;
    private MeshRender meshRender;

    public InventoryItem() {
        this(1000);
    }

    public InventoryItem(int value) {
        this(value, new MeshRender(new Mesh("sphere.obj"), new Material(new Texture("default.png"), 1.0f, 1.0f)));
    }

    public InventoryItem(int value, MeshRender meshRender){
        super();
        this.value = value;
        this.meshRender = meshRender;
        this.getTransform().getScale().set(0.3f, 0.3f, 0.3f);
    }

    @Override
    public void load(){
        addComponent(meshRender);
    }


    public MeshRender getMeshRender() {
        return meshRender;
    }

    public void setMeshRender(MeshRender meshRender) {
        removeComponent(this.meshRender);
        addComponent(meshRender);
        this.meshRender = meshRender;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
