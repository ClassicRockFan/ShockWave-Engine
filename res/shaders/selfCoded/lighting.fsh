#include "sampling.glh"

varying vec2 texCoord0;
varying mat3 tbnMatrix;
varying vec3 worldPos0;

void main()
{
	vec3 directionToEye = normalize(C_eyePos - worldPos0);
	vec2 texCoords = calcParallaxEffect(dispMap, tbnMatrix, directionToEye, texCoord0, dispMapScale, dispMapBias);

	vec3 normal = normalize(tbnMatrix * (255.0/128.0 * texture2D(normalMap, texCoord0).xyz - 1));
    gl_FragColor = texture2D(diffuse, texCoords) * calcLightingEffect(normal, worldPos0);
}