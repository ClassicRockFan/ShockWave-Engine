package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.animation;

import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;

import java.util.ArrayList;

public class CycleAnimation extends EntityComponent{

    private boolean playing;
    private ArrayList<Mesh> meshes;
    private int iteration;
    private float meshPerTime;
    private float timePerMesh;
    private float time;
    private float elapsedTime;
    private MeshRender meshRender;

    public CycleAnimation(ArrayList<Mesh> meshes, float time, MeshRender meshRender) {
        super();
        this.time = time;
        this.meshes = meshes;
        this.meshPerTime = meshes.size()/this.time;
        this.timePerMesh = this.time/meshes.size();
        this.meshRender = meshRender;

        this.elapsedTime = 0;
        this.iteration = 0;
        this.playing = false;
    }

    @Override
    public void update(float delta){
        if(playing){
            elapsedTime += delta;
            if(elapsedTime >= time)
                elapsedTime = 0;

            iteration =  (int) Math.floor(meshPerTime * elapsedTime);

            meshRender.setMesh(meshes.get(iteration));


        }else{
            iteration = 0;
            elapsedTime = 0;
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void toggle(){
        if(isPlaying())
            setPlaying(false);
        else
            setPlaying(true);
    }
}
