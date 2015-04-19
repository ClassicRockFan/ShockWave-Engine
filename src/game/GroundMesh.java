package game;

import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.entityComponent.rendering.MeshRender;
import com.ClassicRockFan.ShockWave.engine.rendering.Material;
import com.ClassicRockFan.ShockWave.engine.rendering.Mesh;
import com.ClassicRockFan.ShockWave.engine.rendering.Vertex;

public class GroundMesh {

    private MeshRender meshRender;

    private float fieldDepth;
    private float fieldWidth;

    public GroundMesh(float depth, float width, Material mat) {
        this.fieldDepth = depth;
        this.fieldWidth = width;

        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        int[] indices = new int[]{
                0, 1, 2,
                2, 1, 3
        };

        Mesh mesh = new Mesh(vertices, indices, true);

        setMeshRender(new MeshRender(mesh, mat));

    }

    public MeshRender getMeshRender() {
        return meshRender;
    }

    public void setMeshRender(MeshRender meshRender) {
        this.meshRender = meshRender;
    }

    public float getFieldDepth() {
        return fieldDepth;
    }

    public void setFieldDepth(float fieldDepth) {
        this.fieldDepth = fieldDepth;
    }

    public float getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(float fieldWidth) {
        this.fieldWidth = fieldWidth;
    }
}
