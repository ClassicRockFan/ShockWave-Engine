package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering;


import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.coreComponents.GameObject;

public class FrustumCuller extends EntityComponent {

    public static final int SPHERE_CULLER = 1;
    public static final int CUBE_CULLER = 2;

    private Vector3f position;
    private int size;
    private int type;

    public FrustumCuller(Vector3f position, int type, int size, GameObject object) {
        super("frustumCuller");
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
