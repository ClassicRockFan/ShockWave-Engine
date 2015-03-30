package com.ClassicRockFan.ShockWave.engine.components.lighting;

import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.Shader;

public class PointLight extends BaseLight {

    private static final int COLOR_DEPTH = 256;

    private float range;
    private Attenuation attenuation;


    public PointLight(Vector3f color, float intensity, Attenuation attenuation) {
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
