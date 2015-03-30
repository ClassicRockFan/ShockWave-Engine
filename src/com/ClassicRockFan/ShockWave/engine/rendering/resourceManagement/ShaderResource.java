package com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement;

import com.base.engine.administrative.ReferenceCounter;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

public class ShaderResource {

    private int program;
    private HashMap<String, Integer> uniforms;
    private ArrayList<String> uniformNames;
    private ArrayList<String> uniformTypes;
    private ReferenceCounter refCounter;

    public ShaderResource() {
        this.program = glCreateProgram();
        this.refCounter = new ReferenceCounter();

        uniforms = new HashMap<String, Integer>();
        uniformNames = new ArrayList<String>();
        uniformTypes = new ArrayList<String>();


        if (program == 0) {
            System.err.println("Shader creation failed: Couldn't find valid memory location in Shader constructor.");
            System.exit(1);
        }
    }

    @Override
    protected void finalize() {
        if(refCounter.getRefCount() <= 0)
            glDeleteBuffers(program);
    }

    public ReferenceCounter getReferenceCounter(){return this.refCounter;}

    public int getProgram() {
        return program;
    }

    public HashMap<String, Integer> getUniforms() {
        return uniforms;
    }

    public void setUniforms(HashMap<String, Integer> uniforms) {
        this.uniforms = uniforms;
    }

    public ArrayList<String> getUniformNames() {
        return uniformNames;
    }

    public void setUniformNames(ArrayList<String> uniformNames) {
        this.uniformNames = uniformNames;
    }

    public ArrayList<String> getUniformTypes() {
        return uniformTypes;
    }

    public void setUniformTypes(ArrayList<String> uniformTypes) {
        this.uniformTypes = uniformTypes;
    }

}
