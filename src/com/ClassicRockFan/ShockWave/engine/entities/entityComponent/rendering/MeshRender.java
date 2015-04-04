package com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering;


import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.EntityComponent;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;
import com.ClassicRockFan.ShockWave.engine.rendering.RenderingEngine;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class MeshRender extends EntityComponent {

    private Mesh mesh;
    private Material material;

    public MeshRender(Mesh mesh, Material material) {
        super("meshRender");
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        shader.bind();
        shader.updateUniforms(getTransform(), material, renderingEngine);
        mesh.draw();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }
}
