package com.ClassicRockFan.ShockWave.engine.components.rendering;

import com.base.engine.components.coreComponents.GameComponent;
import com.base.engine.components.coreComponents.GameObject;
import com.base.engine.core.Vector3f;

public class FrustumCuller extends GameComponent{

    public static final int SPHERE_CULLER = 1;
    public static final int CUBE_CULLER = 2;

    private Vector3f position;
    private int size;
    private int type;

    public FrustumCuller(Vector3f position, int type, int size, GameObject object) {
        this.position = position;
        this.size = size;
        this.type = type;
    }

    @Override
    public void update(float delta){
        setPosition(getTransform().getPos());
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
