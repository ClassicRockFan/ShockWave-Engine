package game.customEntities;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.animation.CycleAnimation;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;

import java.util.ArrayList;

public class AnimationTest extends com.ClassicRockFan.ShockWave.engine.entities.characters.Character{

    public AnimationTest(){
        Mesh coffeeCupMesh = new Mesh("coffeeCup.obj");
        Mesh monkey = new Mesh("TylerMonkey.obj");
        Material ceramic = new Material("white.jpg", 3f, 10f, "white_normal.jpg", "default_disp.jpg", 0.4f, 0.0f);
        MeshRender animationRender = new MeshRender(monkey, ceramic);
        ArrayList<Mesh> animationMeshes = new ArrayList<Mesh>();
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);animationMeshes.add(monkey);
        animationMeshes.add(coffeeCupMesh);
        CycleAnimation animation = new CycleAnimation(animationMeshes, 5f, animationRender);
        //animation.setPlaying(true);
        this.addComponent(animation).addComponent(animationRender);
    }

}
