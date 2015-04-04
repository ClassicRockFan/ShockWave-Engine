package com.ClassicRockFan.ShockWave.engine.rendering;

import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.administrative.logging.Logging;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Time;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;
import com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement.MappedValues;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine extends MappedValues {
    private HashMap<String, Integer> samplerMap;
    private ArrayList<Light> entityLights;
    private Light activeEntityLight;
    private Shader forwardAmbient;
    private EntityCamera mainCamera;
    private ProfileTimer renderTimer;
    private CoreEngine coreEngine;

    //Frustum Culling
    private FrustumCulling frustum;

    public RenderingEngine(CoreEngine engine) {
        super();
        this.coreEngine = engine;
        engine.getConsole().addConsoleText("Creating the Rendering Engine!");

        this.entityLights = new ArrayList<Light>();
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



    public void render(CoreEngine engine) {
        ArrayList<com.ClassicRockFan.ShockWave.engine.entities.characters.Character> loadedCharacters = engine.getEntityManager().getLoadedCharacters();
        ArrayList<Item> loadedItems = engine.getEntityManager().getLoadedItems();

        renderTimer.startInvocation();
        Window.bindAsRenderTarget();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for(int i = 0; i < loadedCharacters.size(); i++) {
            loadedCharacters.get(i).render(forwardAmbient, this);
        }

        for(int i = 0; i < loadedItems.size(); i++){
            if(loadedItems.get(i).getName() != "light")
                loadedItems.get(i).render(forwardAmbient, this);
            else
                Logging.printLog("Found a light", Logging.LEVEL_DEBUG);
        }
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (Light light : entityLights) {
            activeEntityLight = light;
            for(int i = 0; i < loadedCharacters.size(); i++) {
                loadedCharacters.get(i).render(activeEntityLight.getShader(), this);
            }

            for(int i = 0; i < loadedItems.size(); i++){
                if(loadedItems.get(i).getName() != "light")
                    loadedItems.get(i).render(activeEntityLight.getShader(), this);
            }
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

    public Light getActiveLight() {
        return activeEntityLight;
    }

    public EntityCamera getMainCamera() { return mainCamera; }
    public void addLight(Light light){entityLights.add(light);}
    public void setMainCamera(EntityCamera entityCamera){this.mainCamera = entityCamera;}
}
