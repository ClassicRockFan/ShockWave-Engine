package com.ClassicRockFan.ShockWave.engine.components.rendering;


import com.base.engine.components.coreComponents.GameComponent;
import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent {
    public static final Vector3f yAxis = new Vector3f(0, 1, 0);

    private float sensitivity;
    private boolean mouseLocked = false;
    private Vector2f centerPos = new Vector2f((float) Window.getWidth() / 2, (float) Window.getHeight() / 2);

    public FreeLook(float sensitivity) {
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