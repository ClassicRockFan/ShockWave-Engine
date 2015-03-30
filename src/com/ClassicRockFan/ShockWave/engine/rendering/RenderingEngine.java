package com.ClassicRockFan.ShockWave.engine.rendering;

import com.base.engine.components.lighting.BaseLight;
import com.base.engine.components.rendering.Camera;
import com.base.engine.components.coreComponents.GameObject;
import com.base.engine.core.*;
import com.base.engine.administrative.ProfileTimer;
import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderingEngine extends MappedValues {
    private HashMap<String, Integer> samplerMap;
    private ArrayList<BaseLight> lights;
    private BaseLight activeLight;
    private Shader forwardAmbient;
    private Camera mainCamera;
    private ProfileTimer renderTimer;
    private CoreEngine coreEngine;

    //Frustum Culling
    private FrustumCulling frustum;

    public RenderingEngine(CoreEngine engine) {
        super();
        this.coreEngine = engine;
        engine.getConsole().addConsoleText("Creating the Rendering Engine!");

        this.lights = new ArrayList<BaseLight>();
        this.samplerMap = new HashMap<String, Integer>();
        samplerMap.put("diffuse", 0);
        samplerMap.put("normalMap", 1);
        samplerMap.put("dispMap", 2);

        frustum = new FrustumCulling();

        addVector("ambient", new Vector3f(0.2f, 0.2f, 0.2f));

        this.forwardAmbient = new Shader("Forward-Ambient");
        this.renderTimer = new ProfileTimer();

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);
        glEnable(GL_MULTISAMPLE);
        glEnable(GL_FRAMEBUFFER_SRGB);

        glEnable(GL_TEXTURE_2D);

        System.out.println(getOpenGlVersion());
    }

    public static String getOpenGlVersion() {
        return glGetString(GL_VERSION);
    }

    public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType) {
        if (uniformType.equals("vec2")) {
            if (uniformName.equals("time")) {
                shader.setUniform("time", new Vector2f((float) Time.getTime(), (float) Time.getTime()));
            }
        } else {
            System.out.println(uniformType + " is not a supported type.");
            coreEngine.setRunning(false);
        }
    }

    public void render(GameObject object) {
        renderTimer.startInvocation();
        Window.bindAsRenderTarget();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        object.renderAll(forwardAmbient, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (BaseLight light : lights) {
            activeLight = light;
            object.renderAll(light.getShader(), this);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glClearColor(0.0f, 0.0f, 0.5f, 1.0f);

        renderTimer.stopInvocation();
    }

    public double dislayRenderTime(double dividend) {
        return renderTimer.displayAndReset("Render time:  ", dividend);
    }

    //Getters and Setters Below
    public int getSamplerSlot(String name) {
        int samplerSlot = samplerMap.get(name);
        if (samplerSlot == -1) {
            return 0;
        }
        return samplerSlot;
    }

    public BaseLight getActiveLight() {
        return activeLight;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public void addLight(BaseLight light) {
        lights.add(light);
    }

    public void addCamera(Camera camera) {
        this.mainCamera = camera;
    }
}
