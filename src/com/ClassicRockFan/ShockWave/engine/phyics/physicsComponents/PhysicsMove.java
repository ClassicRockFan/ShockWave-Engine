package com.ClassicRockFan.ShockWave.engine.phyics.physicsComponents;


import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameComponent;
import com.ClassicRockFan.ShockWave.engine.core.Input;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;
import com.ClassicRockFan.ShockWave.engine.phyics.calculations.Kinematics;
import org.lwjgl.input.Keyboard;

public class PhysicsMove extends GameComponent {

    private final int UP_KEY;
    private final int DOWN_KEY;
    private final int LEFT_KEY;
    private final int RIGHT_KEY;
    private float moveSpeed;
    private PhysicsObject object;

    public PhysicsMove(float moveSpeed, PhysicsObject object) {
        this(moveSpeed, Keyboard.KEY_W, Keyboard.KEY_S, Keyboard.KEY_A, Keyboard.KEY_D, object);
    }

    public PhysicsMove(float moveSpeed, int UP_KEY, int DOWN_KEY, int LEFT_KEY, int RIGHT_KEY, PhysicsObject object) {
        this.moveSpeed = moveSpeed;
        this.UP_KEY = UP_KEY;
        this.DOWN_KEY = DOWN_KEY;
        this.LEFT_KEY = LEFT_KEY;
        this.RIGHT_KEY = RIGHT_KEY;
        this.object = object;
        this.object.setHasCamera(true);
    }

    @Override
    public void input(float delta) {
        Vector3f direction = Kinematics.calcDistance(new Vector3f(moveSpeed, moveSpeed, moveSpeed), new Vector3f(0, 0, 0), delta);

        if (Input.getKey(UP_KEY)) {
            move(getTransform().getRot().getForward(), direction.length());
        }
        if (Input.getKey(DOWN_KEY)) {
            move(getTransform().getRot().getForward(), -direction.length());
        }
        if (Input.getKey(LEFT_KEY)) {
            move(getTransform().getRot().getLeft(), direction.length());
        }
        if (Input.getKey(RIGHT_KEY)) {
            move(getTransform().getRot().getRight(),direction.length());
        }
    }

    private void move(Vector3f dir, float amt) {
        object.setVelocity(dir.mul(amt));
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
