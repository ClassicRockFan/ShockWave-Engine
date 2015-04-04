package com.ClassicRockFan.ShockWave.engine.entities.light.lights;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;
import com.ClassicRockFan.ShockWave.engine.core.math.Vector3f;
import com.ClassicRockFan.ShockWave.engine.entities.light.Light;
import com.ClassicRockFan.ShockWave.engine.rendering.Attenuation;
import com.ClassicRockFan.ShockWave.engine.rendering.Shader;

public class PointLightEntity extends Light {

    private static final int COLOR_DEPTH = 256;

    private float range;
    private Attenuation attenuation;


    public PointLightEntity(CoreEngine engine, Vector3f color, float intensity, Attenuation attenuation) {
        super(color, intensity);
        this.attenuation = attenuation;

        float a = attenuation.getExponent();
        float b = attenuation.getLinear();
        float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * getColor().maxVal();

        this.range = (float) ((-b + Math.sqrt(b * b - 4 * a * c)) / 2 * a);

        //System.out.println("The range is: " + this.getRange());
        setShader(new Shader("Forward-Point"));
    }

    //Getters and Setters below
    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }
}
