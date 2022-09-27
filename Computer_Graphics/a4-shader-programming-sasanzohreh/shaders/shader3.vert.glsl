// Automatically provided by three.js
//
// uniform mat4 modelMatrix;
// uniform mat4 modelViewMatrix;
// uniform mat4 projectionMatrix;
// uniform mat4 viewMatrix;
// uniform mat3 normalMatrix;
// uniform vec3 cameraPosition;
// uniform bool isOrthographic;
// attribute vec3 position;
// attribute vec3 normal;
// attribute vec2 uv;

// interpolate the normal and texture coordinates across the surface
varying vec3 v_normal;
varying vec2 v_texcoord;

uniform float u_time;

void main() {
    vec3 normal_new = vec3(-(cos(position.x + u_time)) / sqrt(pow(cos(position.x + u_time), 2.0) + 1.0), 1.0 / sqrt(pow(cos(position.x + u_time), 2.0) + 1.0), 1.0 / sqrt(pow(cos(position.x + u_time), 2.0) + 1.0));
    //vec3 normal_new = vec3(1.0 / sqrt(pow(cos(position.x + u_time), 2.0) + 1.0), 1.0 / sqrt(pow(cos(position.x + u_time), 2.0) + 1.0), -(cos(position.x + u_time)) / sqrt(pow(cos(position.z + u_time), 2.0) + 1.0));
    v_normal = mat3(normalMatrix) * normal_new;
    v_texcoord = uv;
    //v_normal.z = sin(position.x + u_time);
    gl_Position = projectionMatrix * modelViewMatrix * vec4( position.x, position.y, sin(position.x + u_time), 1.0 );
}