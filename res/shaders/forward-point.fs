#version 120
#include "lighting.glh"

uniform PointLight R_pointLight;

vec4 calcLightingEffect(vec3 normal, vec3 worldPos){
    return calcPointLight(R_pointLight,normalize(normal),worldPos);
}

#include "lighting.fsh"