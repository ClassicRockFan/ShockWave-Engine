package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering;

import com.ClassicRockFan.ShockWave.engine.core.Input;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.rendering.Window;

public class FreeLook extends EntityComponent {
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);

    private float sensitivity;
    private boolean mouseLocked = false;
    private Vector2f centerPos = new Vector2f((float) Window.getWidth() / 2, (float) Window.getHeight() / 2);

    public FreeLook(float sensitivity) {
        super("freeLook");
        this.sensitivity = sensitivity;
    }

    @Override
    public void input(float delta) {
        float rotateAmount = (float) (sensitivity);

        if (Input.getMouseDown(0)) {
            Input.setMousePosition(centerPos);
            if (!mouseLocked) {
                mouseLocked = true;
                Input.setCursor(false);
            } else {
                mouseLocked = false;
                Input.setCursor(true);
            }
        }

        if (mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPos);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if (rotY) {
                getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * rotateAmount));
            }
            if (rotX) {
                getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * rotateAmount));
            }

            if (rotY || rotX) {
                Input.setMousePosition(centerPos);
            }
        }
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

}