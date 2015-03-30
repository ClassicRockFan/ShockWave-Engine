#version 120
#include "lighting.glh"

uniform SpotLight R_spotLight;

vec4 calcLightingEffect(vec3 normal, vec3 worldPos){
    return calcSpotLight(R_spotLight, normalize(normal), worldPos);
}


#include "lighting.fsh"