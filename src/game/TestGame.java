package game;

import com.ClassicRockFan.ShockWave.engine.components.coreComponents.GameObject;
import com.ClassicRockFan.ShockWave.engine.components.lighting.DirectionalLight;
import com.ClassicRockFan.ShockWave.engine.components.lighting.PointLight;
import com.ClassicRockFan.ShockWave.engine.components.lighting.SpotLight;
import com.ClassicRockFan.ShockWave.engine.components.rendering.Camera;
import com.ClassicRockFan.ShockWave.engine.components.rendering.FreeLook;
import com.ClassicRockFan.ShockWave.engine.components.rendering.FreeMove;
import com.ClassicRockFan.ShockWave.engine.components.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.core.Game;
import com.ClassicRockFan.ShockWave.engine.core.math.Quaternion;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.phyics.Bounding.BoundingSphere;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsObject;
import com.ClassicRockFan.ShockWave.engine.rendering.*;

public class TestGame extends Game {

    public Material bricks;
    public Material stoneBricks;
    public Material stoneBricks2;
    public Material ceramic;
    public Material wood;
    public Material drumStick;
    public Material earthTexture;

    @Override
    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine) {
        super.init(renderingEngine, physicsEngine);

        bricks = new Material("bricks.jpg", 0.5f, 4f, "bricks_normal.jpg", "bricks_disp.jpg", 0.03f, -0.5f);
        stoneBricks = new Material("stoneBricks.jpg", 0.5f, 4f, "stoneBricks_normal.jpg", "stoneBricks_disp.jpg", 0.04f, -1.0f);
        stoneBricks2 = new Material("stoneBricks.jpg", 0.5f, 4f, "stoneBricks_normal.jpg", "stoneBricks_disp.jpg", 0.f, 0.0f);
        ceramic = new Material("white.jpg", 3f, 10f, "white_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);
        wood = new Material("wood.jpg", 3f, 10f, "wood_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);
        drumStick = new Material("drumStickTexture.jpg", 3f, 10f, "drumStickTexture_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);
        earthTexture = new Material("earthTexture.jpg", 3f, 10f, "earthTexture_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);

        //Misc. Meshes
        Mesh groundMesh = new Mesh("plane3.obj");
        Mesh coffeeCupMesh = new Mesh("coffeeCup.obj");
        Mesh drumstickMesh = new Mesh("drumstick.obj");
        Mesh monkey = new Mesh("TylerMonkey.obj");
        Mesh sphere = new Mesh("sphere.obj");
        Mesh cube = new Mesh("cube.obj");

        GameObject ground = new GameObject();
        ground.addComponent(new MeshRender(groundMesh, bricks));
        ground.getTransform().getPos().set(5, -1, 10);
        ground.getTransform().getScale().set(2, 2, 2);

        GameObject testMesh = new GameObject();
        testMesh.addComponent(new MeshRender(groundMesh, bricks));
        testMesh.getTransform().getPos().set(0, 2, 0);
        testMesh.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));
        testMesh.getTransform().getScale().set(0.2f, 0.2f, 0.2f);

        GameObject testMesh2 = new GameObject();
        testMesh2.addComponent(new MeshRender(groundMesh, bricks));
        //testMesh2.addComponent(new LookAtCamera());
        testMesh2.getTransform().getPos().set(0, 0, 25);
        //testMesh2.getTransform().getScale().set(1,1,1);

        GameObject coffeeCup = new GameObject();
        coffeeCup.addComponent(new MeshRender(coffeeCupMesh, ceramic));
        coffeeCup.getTransform().getPos().set(3f, 0f, 3f);

        GameObject drumstick = new GameObject();
        drumstick.addComponent(new MeshRender(drumstickMesh, wood));
        drumstick.getTransform().getPos().set(10, 20, 10);
        drumstick.getTransform().getScale().set(0.2f, 0.2f, 0.2f);
        drumstick.getTransform().getRot().set(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(60)));

        GameObject drumstick1 = new GameObject();
        drumstick1.addComponent(new MeshRender(drumstickMesh, wood));
        drumstick1.getTransform().getPos().set(4, 0, 0);
        drumstick1.getTransform().getRot().set(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-120)));

        GameObject skull = new GameObject();
        skull.addComponent(new MeshRender(monkey, stoneBricks));
        skull.getTransform().getPos().set(10, 25, 10);
        skull.getTransform().getScale().set(3, 3, 3);
        skull.getTransform().getRot().set(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-90)));

        GameObject skullLight = new GameObject();
        skullLight.addComponent(new PointLight(new Vector3f(0, 1, 0), 100f, new Attenuation(0, 1, 1)));
        skullLight.getTransform().getPos().set(0, 30, 10);

        GameObject earth = new GameObject();
        earth.addComponent(new MeshRender(sphere, earthTexture));
        earth.getTransform().getScale().set(5, 5, 5);
        earth.getTransform().getPos().set(0, 10, 0);


        PhysicsObject sphere1 = new PhysicsObject(new BoundingSphere(new Vector3f(0, 0, 0), 1), new Vector3f(0, 0, 0),  10, 0.025f);
        PhysicsObject sphere2 = new PhysicsObject(new BoundingSphere(
                //new Vector3f(1.414f/2.0f * 7.0f, 0.0f, 1.414f/2.0f * 7.0f)
                new Vector3f(10,0,0), 1
                ),
                //new Vector3f(3*-1.414f/2.0f, 0.0f, 3*-1.414f/2.0f)
                new Vector3f(0,0,0)
                , 3, 0.5f
                );
        sphere1.addComponent(new MeshRender(sphere, stoneBricks2))
                //.addComponent(new LookAtObject(sphere2))
//                //.addComponent(new Orbit(sphere2, sphere1, new Vector3f(1, 1, 1), true))
                ;
        sphere2.addComponent(new MeshRender(sphere, stoneBricks2))
//                //.addComponent(new PhysicsMove(2f, sphere2))
//                //.addComponent(new LookAtObject(sphere1))
                ;
//        GameObject physicsObject1 = new PhysicsObject(new AABB(new Vector3f(0,0,0), new Vector3f(10,10,10)), new Vector3f(0,0,0), 10);
//        physicsObject1.addComponent(new MeshRender(cube, bricks));
//        physicsObject1.getTransform().setRot(new Quaternion(new Vector3f(0,1,0), Math.toRadians(45)));
//        sphere1.getTransform().getPos().set(5, 0, 5);
        addPhysicsObject(sphere1);
        addPhysicsObject(sphere2);

        for(int i = 0; i < 5; i ++){
            PhysicsObject test = new PhysicsObject(new BoundingSphere(
                    //new Vector3f(1.414f/2.0f * 7.0f, 0.0f, 1.414f/2.0f * 7.0f)
                    new Vector3f(10,0,0), 1
            ),
                    //new Vector3f(3*-1.414f/2.0f, 0.0f, 3*-1.414f/2.0f)
                    new Vector3f((float)Math.sin(i),(float) Math.cos(i),(float) Math.tan(i))
                    , 3, 0.5f
            );
            test.addComponent(new MeshRender(sphere, stoneBricks2));
            addPhysicsObject(test);
        }


        //Lights Below
        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3f(0, 1, 0), 2f, new Attenuation(0, 0, 1));
        pointLightObject.addComponent(pointLight);
        pointLightObject.getTransform().setParent(skull.getTransform());

        GameObject spotLightObject = new GameObject();
        SpotLight spotLight = new SpotLight(new Vector3f(0, 1, 1), 20f, new Attenuation(0, 0, 1), 0.7f);
        spotLightObject.addComponent(spotLight);
        spotLightObject.getTransform().getPos().set(5, 0, 5);
        spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90.0)));

        GameObject directionLightObject = new GameObject();
        DirectionalLight directionalLight1 = new DirectionalLight(new Vector3f(0, 0, 1), 1f);
        GameObject directionLightObject2 = new GameObject();
        DirectionalLight directionalLight2 = new DirectionalLight(new Vector3f(1, 0, 0), 1f);
        directionLightObject.addComponent(directionalLight1);
        directionLightObject2.addComponent(directionalLight2);
        directionLightObject2.getTransform().getRot().set(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(180)));

        //Adding Lights
        addObject(directionLightObject2);
        addObject(pointLightObject);
        addObject(spotLightObject);
        skullLight.addChild(directionLightObject);

        //Create Camera
        Camera camera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / Window.getHeight(), 0.01f, 1000.0f);
        GameObject cameraObject = new GameObject();
                //new BoundingSphere(new Vector3f(5,5,5), 0.5f), new Vector3f(0,0,0), PhysicsEngine.NULL_MASS);
        cameraObject.addComponent(camera)
                .addComponent(new FreeLook(0.5f))
                .addComponent(new FreeMove(10))
                //.addComponent(new PhysicsMove(500f, cameraObject))
        ;
        //addPhysicsObject(cameraObject);
        addObject(cameraObject);

        //cameraObject.getPosition().set(0, 3, 0);
        //cameraObject.getTransform().getRot().set(new Quaternion(new Vector3f(0, 1,0), Math.toRadians(210)));
        renderingEngine.setMainCamera(camera);
        addObject(skullLight);
        addObject(skull);
        drumstick.addChild(drumstick1);

        addObject(ground);
        testMesh.addChild(testMesh2);
        addObject(testMesh);
//        addObject(coffeeCup);
        addObject(drumstick);
        addObject(earth);

    }
}
