package game;

import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.Game;
import com.ClassicRockFan.ShockWave.engine.core.math.Quaternion;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.misc.RenderPos;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.entities.items.Item;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsComponent;
import com.ClassicRockFan.ShockWave.engine.physics.PhysicsEngine;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.BoundingPlane;
import com.ClassicRockFan.ShockWave.engine.physics.bounding.BoundingSphere;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;

import java.util.Random;

public class BubbleGumSimulator extends Game{


    private final int height = 12;
    private final float distance = 2f;

    @Override
    public void init(RenderingEngine renderingEngine, PhysicsEngine physicsEngine, CoreEngine engine) {
        super.init(renderingEngine, physicsEngine, engine);
        renderingEngine.setAmbient(new Vector3f(1, 1, 1));

        Mesh groundMesh = new Mesh("plainPlane.obj");
        Mesh sphere = new Mesh("gumball.obj");

        Material floorMat = new Material("white.jpg", 3f, 10f, "white_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);

        float numPerAxis = height/distance;

        double numBalls = Math.pow(numPerAxis, 3);

        System.out.println("Num balls = " + numBalls);

        Item floor = new Item("floor");
        floor.addComponent(new MeshRender(groundMesh, floorMat))
                .addPhysicsComponent(
                        new PhysicsComponent(
                                new BoundingPlane(new Vector3f(0, 1, 0), -0.1f),
                                new Vector3f(0, 0, 0),
                                1000f,
                                1000f,
                                true
                        ));
        floor.getTransform().getPos().set(numPerAxis, -0.1f, numPerAxis);
        floor.getTransform().setScale(7.5f);

        Item ceil = new Item("ceil");
        ceil.addComponent(new MeshRender(groundMesh, floorMat))
                .addPhysicsComponent(
                        new PhysicsComponent(
                                new BoundingPlane(new Vector3f(0, -1, 0), height),
                                new Vector3f(0, 0, 0),
                                1000f,
                                1000f,
                                true
                        ));
        ceil.getTransform().getPos().set(numPerAxis, height, numPerAxis);
        ceil.getTransform().setScale(7.5f);


        int counter = 0;
        for(int i = 0; i < numPerAxis; i ++){
            for(int j = 0;  j < numPerAxis; j++){
                for (int k = 0; k < numPerAxis; k++){
                    //PointLightEntity ball = new PointLightEntity(engine, new Vector3f(0.5f, 0, 0), 10, new Attenuation(10, 10, 10));

                    Item ball = new Item("gumBall_" + (counter));
                    Material ballMat = new Material("color_" + randomColor() +".png", 10f, 10f);
                    ball.addComponent(new MeshRender(sphere, ballMat)).addPhysicsComponent(new PhysicsComponent(new BoundingSphere(new Vector3f(distance * i + random(), distance * j + 1 + random()/2, distance * k + random()), 0.3f), new Vector3f(0, 0, 0), 5, 5));
                    //ball.getPhysicsComponent().setConstantAcceleration(new Vector3f(0, -9.8f, 0.0f));
                    //ball.getPhysicsComponent().getCollider().setResponse(new PerfectlyElasticResponse());
                   // ball.getTransform().getPos().set(distance * i, distance * j + 1, distance * k);
                    ball.getTransform().setScale(0.3f);
                    addEntity(ball);

                    counter++;
                }
            }
        }

        addEntity(floor);
        addEntity(ceil);

        //getPlayer().addPhysicsComponent(new PhysicsComponent(new BoundingSphere(getPlayer().getTransform().getPos(), 1), new Vector3f(0,0,0), 100));
        //getPlayer().getPhysicsComponent().setConstantAcceleration(new Vector3f(0, -9.8f, 0));

        Mesh project= new Mesh("eagleProject.obj");

        Item projectItem = new Item("ProjectItem");

        projectItem.addComponent(new MeshRender(project, new Material("stoneBricks.jpg", 0.5f, 4f, "stoneBricks_normal.jpg", "stoneBricks_disp.jpg", 0.04f, -1.0f))).addComponent(new RenderPos());

        projectItem.getTransform().getPos().set(-10, 0, -10);
        projectItem.getTransform().getRot().set(new Quaternion(new Vector3f(0,1,0), 3.14/2));

        addEntity(projectItem);

    }

    public float random(){
        Random rand = new Random();
        double min = -2.0;
        double max = 2;
        double randomValue = min + (max - min) * rand.nextDouble();
        return (float) randomValue;
    }

    public int randomColor(){
        Random rand = new Random();
        int min = 0;
        int max = 5;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
