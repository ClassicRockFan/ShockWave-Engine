package com.ClassicRockFan.ShockWave.engine.rendering;

import com.ClassicRockFan.ShockWave.engine.administrative.ProfileTimer;
import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Time;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.core.math.Matrix4f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.Entity;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
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

    //clear color
    private float clearR;
    private float clearG;
    private float clearB;
    private float clearA;

    //Frustum Culling
    private FrustumCulling frustum;

    static Texture g_tempTarget;
    static Mesh g_mesh;
    static Transform g_transform;
    static Material g_material;
    static EntityCamera g_camera;
    static Entity g_cameraObject;

    public RenderingEngine(CoreEngine engine) {
        super();
        this.coreEngine = engine;
        CoreEngine.getConsole().addConsoleText("Creating the Rendering Engine!");

        this.entityLights = new ArrayList<Light>();
        this.samplerMap = new HashMap<String, Integer>();
        samplerMap.put("diffuse", 0);
        samplerMap.put("normalMap", 1);
        samplerMap.put("dispMap", 2);

        this.clearA = 0.0f;
        this.clearB = 0.0f;
        this.clearG = 0.0f;
        this.clearR = 0.0f;

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


        int width = Window.getWidth();
        int height = Window.getHeight();
        int dataSize = width * height * 4;

        char[] data = new char[dataSize];
        System.out.println(width);
        System.out.println(height);
        System.out.println(dataSize);
       // g_tempTarget = new Texture("renderToTexture", Window.getWidth(), Window.getHeight(), data, GL_TEXTURE_2D, GL_NEAREST);

        Vertex vertices[] = { new Vertex(new Vector3f(-1,-1,0),new Vector2f(1,0)),
                new Vertex(new Vector3f(-1,1,0),new Vector2f(1,1)),
                new Vertex(new Vector3f(1,1,0),new Vector2f(0,1)),
                new Vertex(new Vector3f(1, -1,0),new Vector2f(0,0)) };

        int indices[] = { 2, 1, 0,
                3, 2, 0 };
       // g_material = new Material(g_tempTarget, 1, 8);
        g_transform = new Transform();
        g_transform.setScale(0.9f);
        g_mesh = new Mesh(vertices, indices, true);

        g_camera = new EntityCamera(new Matrix4f().initIdentity());
        g_cameraObject = (new Entity()).addComponent(g_camera);

        g_cameraObject.getTransform().rotate(new Vector3f(0, 1, 0), (float) Math.toRadians(180.0f));

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
        renderTimer.startInvocation();
        ArrayList<Entity> loadedEntities = engine.getEntityManager().getAllLoadedEntites();

        Window.bindAsRenderTarget();

        glClearColor(clearR, clearG, clearB, clearA);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < loadedEntities.size(); i++) {
            if (loadedEntities.get(i).getClass().getSuperclass() != Light.class)
                loadedEntities.get(i).render(forwardAmbient, this);
        }

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (Light light : entityLights) {
            activeEntityLight = light;
            for (int i = 0; i < loadedEntities.size(); i++) {
                if (loadedEntities.get(i).getClass().getSuperclass() != Light.class) {
                    loadedEntities.get(i).render(activeEntityLight.getShader(), this);
                }
            }
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
        glClearColor(0.0f, 0.0f, 0.5f, 1.0f);

        renderTimer.stopInvocation();
    }

    public void setClearColor(float clearR, float clearG, float clearB, float clearA) {
        this.clearA = clearA;
        this.clearB = clearB;
        this.clearG = clearG;
        this.clearR = clearR;
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

    public EntityCamera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(EntityCamera entityCamera) {
        this.mainCamera = entityCamera;
    }

    public void addLight(Light light) {
        entityLights.add(light);
    }

    public void setAmbient(Vector3f ambient){
        removeVector("ambient");
        addVector("ambient", ambient);
    }
}
