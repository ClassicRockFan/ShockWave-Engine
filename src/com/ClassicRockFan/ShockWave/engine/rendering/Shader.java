package com.ClassicRockFan.ShockWave.engine.rendering;


import com.ClassicRockFan.ShockWave.engine.administrative.ReferenceCounter;
import com.ClassicRockFan.ShockWave.engine.core.Transform;
import com.ClassicRockFan.ShockWave.engine.core.Util;
import com.ClassicRockFan.ShockWave.engine.core.math.Matrix4f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector2f;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.DirectionalLightEntity;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.PointLightEntity;
import com.ClassicRockFan.ShockWave.engine.entities.light.lights.SpotLightEntity;
import com.ClassicRockFan.ShockWave.engine.rendering.resourceManagement.ShaderResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class Shader extends ReferenceCounter {
    private static HashMap<String, ShaderResource> loadedShaders = new HashMap<String, ShaderResource>();

    private String fileName;
    private ShaderResource resource;

    public Shader(String fileName) {
        this.fileName = fileName;
        System.out.println("Currently indexing shader: " + fileName);
        ShaderResource oldResource = loadedShaders.get(fileName);
        if (oldResource != null) {
            resource = oldResource;
            resource.getReferenceCounter().addReference();
        } else {
            resource = new ShaderResource();
            System.out.println("Currently indexing Vertex shader: " + fileName);
            String vertexShaderText = loadShader(fileName + ".vs");
            System.out.println("Currently indexing Fragment shader: " + fileName);
            String fragmentShaderText = loadShader(fileName + ".fs");

            addVertexShader(vertexShaderText);
            addFragmentShader(fragmentShaderText);
            addAllAttributes(vertexShaderText);
            addAllAttributes(fragmentShaderText);
            compileShader();


            addAllUniforms(vertexShaderText);
            addAllUniforms(fragmentShaderText);
            loadedShaders.put(fileName, resource);
        }

    }

    private static String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;
        final String INCLUDE_DIRECTIVE = "#include";
        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;
            while ((line = shaderReader.readLine()) != null) {
                if (line.startsWith(INCLUDE_DIRECTIVE)) {
                    shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.trim().length() - 1)));
                } else {
                    shaderSource.append(line.trim()).append("\n");
                }
            }
            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return shaderSource.toString();
    }

    @Override
    protected void finalize() {
        if (resource.getReferenceCounter().removeReference() && !fileName.isEmpty()) {
            loadedShaders.remove(fileName);
        }
    }

    public void bind() {
        glUseProgram(resource.getProgram());
    }

    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f MvPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);

        for (int i = 0; i < resource.getUniformNames().size(); i++) {
            String uniformName = resource.getUniformNames().get(i);
            String uniformType = resource.getUniformTypes().get(i);
            //Check if it's a Sampler2D
            if (uniformType.equals("sampler2D")) {
                int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
                material.getTexture(uniformName).bind(samplerSlot);
                setUniformi(uniformName, samplerSlot);
                //Check if it belongs as a member of "Transform"
            } else if (uniformName.startsWith("T_")) {
                if (uniformName.equals("T_MvP") || uniformName.equals("T_MVP"))
                    setUniform(uniformName, MvPMatrix);
                else if (uniformName.equals("T_model"))
                    setUniform(uniformName, worldMatrix);
                else
                    throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
                //Check if it belongs as a member of "RenderingEngine"
            } else if (uniformName.startsWith("R_")) {
                String unprefixedUniformName = uniformName.substring(2);
                if (uniformType.equals("vec3"))
                    setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
                else if (uniformType.equals("float"))
                    setUniformf(uniformName, renderingEngine.getFloat(unprefixedUniformName));
                else if (uniformType.equals("DirectionalLight"))
                    setUniformDirectionalLight(uniformName, (DirectionalLightEntity) renderingEngine.getActiveLight());
                else if (uniformType.equals("PointLight"))
                    setUniformPointLight(uniformName, (PointLightEntity) renderingEngine.getActiveLight());
                else if (uniformType.equals("SpotLight"))
                    setUniformSpotLight(uniformName, (SpotLightEntity) renderingEngine.getActiveLight());

                else
                    //Check if there is some other struct handled in "RenderingEngine"
                    renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);

                //Check if it belongs as a member of "camera"
            } else if (uniformName.startsWith("C_")) {
                if (uniformName.equals("C_eyePos"))
                    setUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
                else
                    throw new IllegalArgumentException(uniformName + " is not a valid component of Camera.");
            } else {
                if (uniformType.equals("vec3")) {
                    setUniform(uniformName, material.getVector3f(uniformName));
                } else if (uniformType.equals("float")) {
                    setUniformf(uniformName, material.getFloat(uniformName));
                } else
                    //Check if there is some other type specified in the renderingEngine
                    renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
            }
        }
    }

    private void addAllAttributes(String shaderText) {
        final String ATTRIBUTE_KEYWORD = "attribute";
        int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
        int attribNumber = 0;
        while (attributeStartLocation != -1) {
            if (!(attributeStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length())))) {
                attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
                continue;
            }

            int begin = ATTRIBUTE_KEYWORD.length() + attributeStartLocation + 1;
            int end = shaderText.indexOf(";", begin);

            String attributeLine = shaderText.substring(begin, end).trim();
            String attributeName = attributeLine.substring(attributeLine.indexOf(" ") + 1, attributeLine.length()).trim();

            setAttribLocation(attributeName, attribNumber);
            attribNumber++;

            attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
        }
    }

    private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText) {
        HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

        final String STRUCT_KEYWORD = "struct";
        int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
        while (structStartLocation != -1) {
            if (!(structStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_KEYWORD.length())))) {
                structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
                continue;
            }

            int nameBegin = STRUCT_KEYWORD.length() + structStartLocation + 1;
            int braceBegin = shaderText.indexOf("{", nameBegin);
            int braceEnd = shaderText.indexOf("}", braceBegin);

            String structName = shaderText.substring(nameBegin, braceBegin).trim();
            ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

            int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
            while (componentSemicolonPos != -1 && componentSemicolonPos < braceEnd) {
                int componentNameEnd = componentSemicolonPos + 1;

                while (Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';')
                    componentNameEnd--;

                int componentNameStart = componentSemicolonPos;

                while (!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
                    componentNameStart--;

                int componentTypeEnd = componentNameStart;

                while (Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
                    componentTypeEnd--;

                int componentTypeStart = componentTypeEnd;

                while (!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1))) {
                    componentTypeStart--;
                }
                String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
                String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

                GLSLStruct glslStruct = new GLSLStruct();
                glslStruct.name = componentName;
                glslStruct.type = componentType;

                glslStructs.add(glslStruct);

                componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
            }

            result.put(structName, glslStructs);

            structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
        }
        return result;
    }

    private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
        boolean addThis = true;
        ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

        if (structComponents != null) {
            addThis = false;

            for (GLSLStruct structure : structComponents) {
                addUniform(uniformName + "." + structure.name, structure.type, structs);
            }
        }

        if (!addThis)
            return;

        int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);
        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error couldn't find uniform: " + uniformName + " in the shader program: " + resource.getProgram());
            new Exception().printStackTrace();
            System.exit(1);
        }
        resource.getUniforms().put(uniformName, uniformLocation);

    }

    private void addAllUniforms(String shaderText) {
        HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);
        final String UNIFORM_KEYWORD = "uniform";
        int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
        while (uniformStartLocation != -1) {
            if (!(uniformStartLocation != 0
                    && (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';')
                    && Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_KEYWORD.length())))) {
                uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
                continue;
            }
            int begin = UNIFORM_KEYWORD.length() + uniformStartLocation + 1;
            int end = shaderText.indexOf(";", begin);

            String uniformLine = shaderText.substring(begin, end).trim();

            int whiteSpacePos = uniformLine.indexOf(" ");
            String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length()).trim();
            String uniformType = uniformLine.substring(0, whiteSpacePos).trim();

            resource.getUniformNames().add(uniformName);
            resource.getUniformTypes().add(uniformType);
            addUniform(uniformName, uniformType, structs);

            uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
        }
    }


    private void compileShader() {
        glLinkProgram(resource.getProgram());

        if (glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
            System.exit(1);
        }

        glValidateProgram(resource.getProgram());
        if (glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
            System.exit(1);
        }
    }


    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed: Couldn't find valid memory location when adding shader.");
            System.exit(1);
        }
        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }
        glAttachShader(resource.getProgram(), shader);
    }

    private void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    //private void addGeometryShader(String text){addProgram(text, GL_GEOMETRY_SHADER);}
    private void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    private void setAttribLocation(String attributeName, int location) {
        glBindAttribLocation(resource.getProgram(), location, attributeName);
    }

    public void setUniformi(String uniformName, int value) {
        glUniform1i(resource.getUniforms().get(uniformName), value);
    }

    public void setUniformf(String uniformName, float value) {
        glUniform1f(resource.getUniforms().get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String uniformName, Vector2f value) {
        glUniform2f(resource.getUniforms().get(uniformName), value.getX(), value.getY());
    }

    public void setUniform(String uniformName, Matrix4f value) {
        glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
    }

    public void setUniformDirectionalLight(String uniformName, DirectionalLightEntity directionalLight) {
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniformBaseLight(String uniformName, Light baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformPointLight(String uniformName, PointLightEntity pointLight) {
        setUniformBaseLight(uniformName + ".base", pointLight);
        setUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
        setUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
        setUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
        setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }

    public void setUniformSpotLight(String uniformName, SpotLightEntity spotLight) {
        setUniformPointLight(uniformName + ".pointLight", spotLight);
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniformf(uniformName + ".cutoff", spotLight.getConeRadius());
    }

    private class GLSLStruct {
        public String name;
        public String type;
    }
}
