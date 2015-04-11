package game;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Game;
import com.ClassicRockFan.ShockWave.engine.core.math.Quaternion;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.characters.Character;
import com.ClassicRockFan.ShockWave.engine.entities.characters.player.Player;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.effects.LookAtCamera;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.EntityCamera;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.entities.items.InventoryItem;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.DirectionalLightEntity;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.PointLightEntity;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.SpotLightEntity;
import com.ClassicRockFan.ShockWave.engine.phyics.PhysicsEngine;
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
    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine, CoreEngine engine){
        super.init(renderingEngine, physicsEngine, engine);

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

        Character ground = new Character("ground");
        ground.addComponent(new MeshRender(groundMesh, bricks));
        ground.getTransform().getPos().set(5, -1, 10);
        ground.getTransform().getScale().set(2, 2, 2);

        Character testMesh = new Character("testMesh1");
        testMesh.addComponent(new MeshRender(groundMesh, bricks));
        testMesh.getTransform().getPos().set(0, 2, 0);
        testMesh.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));
        testMesh.getTransform().getScale().set(0.2f, 0.2f, 0.2f);

        Character testMesh2 = new Character("testMesh2");
        testMesh2.addComponent(new MeshRender(groundMesh, bricks));
        testMesh2.getTransform().setParent(testMesh.getTransform());
        testMesh2.getTransform().getPos().set(testMesh2.getTransform().getPos().add(new Vector3f(0, 0, 25)));

        Character coffeeCup = new Character("coffeeCup");
        coffeeCup.addComponent(new MeshRender(coffeeCupMesh, ceramic));
        coffeeCup.getTransform().getPos().set(3f, 0f, 3f);

        Character drumstick = new Character("drumstick1");
        drumstick.addComponent(new MeshRender(drumstickMesh, wood));
        drumstick.getTransform().getPos().set(10, 20, 10);
        drumstick.getTransform().getScale().set(0.2f, 0.2f, 0.2f);
        drumstick.getTransform().getRot().set(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(60)));

        Character drumstick1 = new Character("drumstick2");
        drumstick1.addComponent(new MeshRender(drumstickMesh, wood));
        drumstick1.getTransform().getPos().set(11, 20, 10);
        drumstick1.getTransform().getScale().set(0.2f, 0.2f, 0.2f);
        drumstick1.getTransform().getRot().set(new Quaternion(new Vector3f(1, 0, 0), (float) Math.toRadians(-60)));

        Character skull = new Character("skull");
        skull.addComponent(new MeshRender(monkey, stoneBricks)).addComponent(new LookAtCamera());
        skull.getTransform().getPos().set(10, 25, 10);
        skull.getTransform().getScale().set(3, 3, 3);
        skull.getTransform().getRot().set(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(-90)));

        PointLightEntity skullLight = new PointLightEntity(engine, new Vector3f(0, 1, 0), 100f, new Attenuation(0, 1, 1));
        skullLight.getTransform().getPos().set(0, 30, 10);

        Character earth = new Character("earth");
        earth.addComponent(new MeshRender(sphere, earthTexture));
        earth.getTransform().getScale().set(5, 5, 5);
        earth.getTransform().getPos().set(0, 10, 50);

        //Lights Below
        PointLightEntity pointLight = new PointLightEntity(engine, new Vector3f(0, 1, 0), 2f, new Attenuation(0, 0, 1));
        pointLight.getTransform().getPos().set(skullLight.getTransform().getPos());

        SpotLightEntity spotLight = new SpotLightEntity(engine, new Vector3f(0, 1, 1), 20f, new Attenuation(0, 0, 1), 0.7f);
        spotLight.getTransform().getPos().set(5, 0, 5);
        spotLight.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float) Math.toRadians(90.0)));

        DirectionalLightEntity directionalLight1 = new DirectionalLightEntity(engine, new Vector3f(0, 0, 1), 1f);
        DirectionalLightEntity directionalLight2 = new DirectionalLightEntity(engine, new Vector3f(1, 0, 0), 1f);
        directionalLight2.getTransform().getRot().set(new Quaternion(new Vector3f(0, 1, 0), Math.toRadians(180)));


        //Create Camera
        EntityCamera entityCamera = new EntityCamera((float) Math.toRadians(70.0f), (float) Window.getWidth() / Window.getHeight(), 0.01f, 1000.0f);
        Player player = new Player(entityCamera, 8, 0.5f);
        //player.addComponent(new SkyColor());

        addLight(skullLight);
        addLight(directionalLight1);
        addLight(directionalLight2);
        addLight(spotLight);
        addLight(pointLight);
        addCharacter(player);
        addCharacter(skull);
        addCharacter(ground);
        addCharacter(testMesh);
        addCharacter(testMesh2);
        addCharacter(drumstick);
        addCharacter(drumstick1);
        addCharacter(earth);
        addCharacter(coffeeCup);

        InventoryItem testItem = new InventoryItem();
        testItem.getTransform().getPos().set(0,1,0);
        testItem.setMeshRender(new MeshRender(monkey, stoneBricks));

        addItem(testItem);
    }

}
