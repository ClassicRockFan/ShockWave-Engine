package com.ClassicRockFan.ShockWave.engine.components.rendering;


import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameComponent;
import com.ClassicRockFan.ShockWave.engine.core.Input;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import org.lwjgl.input.Keyboard;

public class FreeMove extends GameComponent {
    private final int UP_KEY;
    private final int DOWN_KEY;
    private final int LEFT_KEY;
    private final int RIGHT_KEY;
    private float moveSpeed;

    public FreeMove(float moveSpeed) {
        this(moveSpeed, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D);
    }

    public FreeMove(float moveSpeed, int UP_KEY, int DOWN_KEY, int LEFT_KEY, int RIGHT_KEY) {
        this.moveSpeed = moveSpeed;
        this.UP_KEY = UP_KEY;
        this.DOWN_KEY = DOWN_KEY;
        this.LEFT_KEY = LEFT_KEY;
        this.RIGHT_KEY = RIGHT_KEY;
    }

    @Override
    public void input(float delta) {
        float moveAmount = (float) (moveSpeed * delta);

        if (Input.getKey(UP_KEY)) {
            move(getTransform().getRot().getForward(), moveAmount);
        }
        if (Input.getKey(DOWN_KEY)) {
            move(getTransform().getRot().getForward(), -moveAmount);
        }
        if (Input.getKey(LEFT_KEY)) {
            move(getTransform().getRot().getLeft(), moveAmount);
        }
        if (Input.getKey(RIGHT_KEY)) {
            move(getTransform().getRot().getRight(), moveAmount);
        }
    }

    private void move(Vector3f dir, float amt) {
        getTransform().getPos().set(getTransform().getPos().add(dir.mul(amt)));
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getUP_KEY() {
        return UP_KEY;
    }

    public int getDOWN_KEY() {
        return DOWN_KEY;
    }

    public int getLEFT_KEY() {
        return LEFT_KEY;
    }

    public int getRIGHT_KEY() {
        return RIGHT_KEY;
    }

}
