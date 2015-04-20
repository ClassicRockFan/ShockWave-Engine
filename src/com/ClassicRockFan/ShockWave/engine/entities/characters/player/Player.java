package com.ClassicRockFan.ShockWave.engine.entities.characters.player;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc.MaxLevel;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc.MinLevel;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.FreeLook;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.SmartMove;

public class Player extends Character {

    private EntityCamera camera;
    private float moveSpeed;
    private float mouseSensitivity;
    private FreeLook freeLook;
    private SmartMove freeMove;
    private MinLevel floorRestrictor;
    private MaxLevel ceilRestrictor;

    public Player(EntityCamera camera, float moveSpeed, float mouseSensitivity, int heightRestrictionBottom) {
        super(50);
        this.camera = camera;
        this.moveSpeed = moveSpeed;
        this.mouseSensitivity = mouseSensitivity;
        this.freeLook = new FreeLook(mouseSensitivity);
        this.freeMove = new SmartMove(moveSpeed);
        this.floorRestrictor = new MinLevel(heightRestrictionBottom);
        this.ceilRestrictor = new MaxLevel(heightRestrictionBottom + 1);
    }

    @Override
    public void finalizeSetup(CoreEngine engine) {
        super.finalizeSetup(engine);
    }

    @Override
    public void load() {
        this
                .addComponent(freeLook)
                .addComponent(freeMove)
                .addComponent(camera)
          //      .addComponent(floorRestrictor)
        //        .addComponent(ceilRestrictor)
        ;
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
