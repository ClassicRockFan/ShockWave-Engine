package com.ClassicRockFan.ShockWave.engine.rendering;


import com.ClassicRockFan.ShockWave.engine.core.Util;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.rendering.meshLoading.model.IndexedModel;
import com.ClassicRockFan.ShockWave.engine.rendering.meshLoading.model.OBJModel;
import com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement.MeshResource;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh {
    private static HashMap<String, MeshResource> loadedModels = new HashMap<String, MeshResource>();
    private MeshResource resource;
    private String fileName;
    private Vertex[] vertices;
    private int[] indices;

    public Mesh(String fileName) {
        this.fileName = fileName;
        MeshResource oldResource = loadedModels.get(fileName);
        if (oldResource != null) {
            resource = oldResource;
            resource.addReference();
        } else {
            loadMesh(fileName);
            loadedModels.put(fileName, resource);
        }

    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean normals) {
        fileName = "";

        addVertices(vertices, indices, normals, false);
    }

    @Override
    protected void finalize() {
        if (resource.removeReference() && !fileName.isEmpty()) {
            loadedModels.remove(fileName);
        }
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean normals, boolean tangents) {
        this.indices = indices;
        this.vertices = vertices;
        if (normals) {
            calcNormals(vertices, indices);
        }
        if (tangents) {
            calcTangents(vertices, indices);
        }

        resource = new MeshResource(indices.length);

        glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
        glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
    }

    public void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalized();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setNormal(vertices[i].getNormal().normalized());
        }
    }

    public void calcTangents(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f edge1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f edge2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            float deltaU1 = vertices[i1].getPos().getX() - vertices[i0].getPos().getX();
            float deltaU2 = vertices[i2].getPos().getX() - vertices[i0].getPos().getX();
            float deltaV1 = vertices[i1].getPos().getY() - vertices[i0].getPos().getY();
            float deltaV2 = vertices[i2].getPos().getY() - vertices[i0].getPos().getY();

            float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

            Vector3f tangent = new Vector3f(0, 0, 0);
            tangent.setX(f * (edge1.getX() * deltaV2 - edge2.getX() * deltaV1));
            tangent.setY(f * (edge1.getY() * deltaV2 - edge2.getY() * deltaV1));
            tangent.setZ(f * (edge1.getZ() * deltaV2 - edge2.getZ() * deltaV1));

            vertices[i0].setTangent(vertices[i0].getTangent().add(tangent));
            vertices[i1].setTangent(vertices[i1].getTangent().add(tangent));
            vertices[i2].setTangent(vertices[i2].getTangent().add(tangent));

        }
        for (int i = 0; i < vertices.length; i++)
            vertices[i].setTangent(vertices[i].getTangent().normalized());
    }

    private Mesh loadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];


        if (!ext.equals("obj")) {
            System.err.println("Error: File format not supported for mesh data: " + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        OBJModel obj = new OBJModel("./res/models/" + fileName);

        IndexedModel model = obj.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        for (int i = 0; i < model.getPositions().size(); i++) {
            vertices.add(new Vertex(model.getPositions().get(i),
                    model.getTextCoords().get(i),
                    model.getNormals().get(i),
                    model.getTangents().get(i)));
        }
        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);

        Integer[] indexData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(indexData);

        addVertices(vertexData, Util.toIntArray(indexData), false, true);

        return this;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }
}
