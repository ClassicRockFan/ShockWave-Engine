package com.ClassicRockFan.ShockWave.engine.components.effects;

import com.base.engine.components.coreComponents.GameComponent;
import com.base.engine.core.Quaternion;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Shader;

public class LookAtComponent extends GameComponent {
    RenderingEngine renderingEngine;
    int method;
    GameComponent component;

    public LookAtComponent(GameComponent component) {
        this(0, component);
    }

    public LookAtComponent(int method, GameComponent component) {
        this.method = method;
        this.component = component;
    }

    @Override
    public void update(float delta) {
        if (renderingEngine != null) {
            Quaternion newRot = getTransform().getLookAtDirection(component.getTransform().getTransformedPos(), new Vector3f(0, 1, 0));

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
