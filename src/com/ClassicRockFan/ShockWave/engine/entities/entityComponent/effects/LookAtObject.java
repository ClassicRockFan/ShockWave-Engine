package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.effects;


import com.ClassicRockFan.ShockWave.engine.core.math.Quaternion;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.coreComponents.GameObject;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class LookAtObject extends EntityComponent {
    RenderingEngine renderingEngine;
    int method;
    GameObject object;

    public LookAtObject(GameObject object) {
        this(0, object);
    }

    public LookAtObject(int method, GameObject object) {
        super("lookAtObject");
        this.method = method;
        this.object = object;
    }

    @Override
    public void update(float delta) {
        if (renderingEngine != null) {
            Quaternion newRot = getTransform().getLookAtDirection(object.getTransform().getTransformedPos(), new Vector3f(0, 1, 0));

            if (method == 0)
                getTransform().setRot(getTransform().getRot().SLerp(newRot, delta * 5.0f, true));
            else if (method == 1)
                getTransform().setRot(getTransform().getRot().NLerp(newRot, delta * 5.0f, true));

        }
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }


}
