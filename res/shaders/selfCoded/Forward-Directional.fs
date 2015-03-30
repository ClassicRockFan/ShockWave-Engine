#version 120
#include "lighting.glh"

uniform DirectionalLight R_directionalLight;

vec4 calcLightingEffect(vec3 normal, vec3 worldPos){
    return calcDirectionLight(R_directionalLight, normalize(normal), worldPos);
}

#include "lighting.fsh"