#version 400

layout(location = 0) in vec3 in_position;
layout(location = 1) in vec2 in_texture;
layout(location = 2) in vec3 in_normal;

out vec2 out_texture;
out vec3 out_vertexNormal;
out vec3 out_vertexPosition;
out mat4 out_modelViewMatrix;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main(){

    vec4 position = modelViewMatrix * vec4(in_position,1.0);
    gl_Position = projectionMatrix * position;

    out_texture = in_texture;

    out_vertexNormal = normalize(modelViewMatrix*vec4(in_normal,0.0)).xyz;
    out_vertexPosition = position.xyz;

    out_modelViewMatrix = modelViewMatrix;
}