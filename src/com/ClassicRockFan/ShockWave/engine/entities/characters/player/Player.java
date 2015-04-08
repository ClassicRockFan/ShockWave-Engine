package com.ClassicRockFan.ShockWave.engine.entities.characters.player;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.FreeLook;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.FreeMove;

public class Player extends Character{

    private EntityCamera camera;
    private float moveSpeed;
    private float mouseSensitivity;
    private FreeLook freeLook;
    private FreeMove freeMove;

    public Player(EntityCamera camera, float moveSpeed, float mouseSensitivity) {
        super(50);
        this.camera = camera;
        this.moveSpeed = moveSpeed;
        this.mouseSensitivity = mouseSensitivity;
        this.freeLook = new FreeLook(mouseSensitivity);
        this.freeMove = new FreeMove(moveSpeed);
    }

    @Override
    public void init(CoreEngine engine){
        super.init(engine);
    }

    @Override
    public void load(){
        this.addComponent(freeLook).addComponent(freeMove).addComponent(camera);
        getEngine().getRenderingEngine().setMainCamera(camera);
    }

    public EntityCamera getCamera() {
        return camera;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        this.freeMove.setMoveSpeed(moveSpeed);
    }

    public float getMouseSensitivity() {
        return mouseSensitivity;
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
        this.freeLook.setSensitivity(mouseSensitivity);
    }
}
