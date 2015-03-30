package com.ClassicRockFan.ShockWave.engine.rendering;

import com.base.engine.rendering.resourceManagement.MappedValues;

import java.util.HashMap;

public class Material extends MappedValues {
    private HashMap<String, Texture> textureHashmap;

    public Material(Texture texture, float specularIntensity, float specularPower) {
        super();
        textureHashmap = new HashMap<String, Texture>();
        addTexture("diffuse", texture);
        addTexture("normalMap", new Texture("default_normal.jpg"));
        addTexture("dispMap", new Texture("default_disp.jpg"));

        addFloat("dispMapScale", 0.0f);
        addFloat("dispMapBias", 0.0f);

        addFloat("specularIntensity", specularIntensity);
        addFloat("specularPower", specularPower);
    }

    public Material(String texturePath, float specularIntensity, float specularPower) {
        this(texturePath, specularIntensity, specularPower, "default_normal.jpg");
    }

    public Material(String texturePath, float specularIntensity, float specularPower, String normalMapFile) {
        this(texturePath, specularIntensity, specularPower, normalMapFile, "default_disp.jpg", 0.0f, 0.0f);
    }

    public Material(String texturePath, float specularIntensity, float specularPower, String normalMapFile, String dispMapFile, float dispMapScale, float dispMapOffset) {
        super();
        textureHashmap = new HashMap<String, Texture>();
        addTexture("diffuse", new Texture(texturePath));
        addTexture("normalMap", new Texture(normalMapFile));
        addTexture("dispMap", new Texture(dispMapFile));

        float baseBias = dispMapScale / 2.0f;
        addFloat("dispMapScale", dispMapScale);
        addFloat("dispMapBias", -baseBias + baseBias * dispMapOffset);

        addFloat("specularIntensity", specularIntensity);
        addFloat("specularPower", specularPower);
    }

    public void addTexture(String name, Texture texture) {
        textureHashmap.put(name, texture);
    }

    public Texture getTexture(String name) {
        Texture result = textureHashmap.get(name);
        if (result != null) {
            return result;
        }
        if (name.contains("_normal")) {
            return new Texture("default_normal.jpg");
        }

        if (name.contains("_disp")) {
            return new Texture("default_disp.jpg");
        }

        return new Texture("default.png");
    }
}
