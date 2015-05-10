package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc;

import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;

public class RenderPos extends EntityComponent{

    private boolean added;

    public RenderPos() {
        super();

        added = false;

    }

    public void update(float delta){
        if(!added){
            Item item = new Item("Pos Location");
            item.addComponent(new MeshRender(new Mesh("sphere.obj"), new Material("color_0.png", 1.0f, 1.0f)));
            item.getTransform().setScale(0.1f);

            item.finalizeSetup(getParent().getEngine());

            getParent().addChild(item);
            added = true;
        }
    }
}
