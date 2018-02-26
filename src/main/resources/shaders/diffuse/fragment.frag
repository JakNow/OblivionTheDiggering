#version 400

const int MAX_LIGHTS = 10;

in vec2 out_texture;
in vec3 out_vertexNormal;
in vec3 out_vertexPosition;
in mat4 out_modelViewMatrix;

out vec4 outColour;

struct Material
{
    int hasDiffuseTexture;
    int hasAmbientTexture;
    int hasNormalTexture;
    int hasSpecularTexture;
    int hasAlphaTexture;
    int hasReflectionTexture;

    vec4 diffuseColor;
    vec4 ambientColor;
    vec4 specularColor;
    vec4 emissiveColor;
    vec4 reflectiveColor;

    float reflectivity;
    float shininess;
};
struct AmbientLight{
    vec4 color;
    float intensity;
};

struct Attenuation {
    float constant;
    float linear;
    float exponent;
};

struct DirectLight {
    vec3 direction;
    vec4 color;
    float intensity;
};

struct PointLight{
    vec3 position;
    vec4 color;
    Attenuation att;
    float intensity;
};

struct SpotLight{
    vec3 position;
    vec3 direction;
    vec4 color;
    float angle;
    Attenuation att;
    float intensity;
};

struct Light {
  int useDirectLight;
  int usePointLight;
  int useSpotLight;

  DirectLight directLight;
  PointLight pointLight;
  SpotLight spotLight;
};

uniform sampler2D diffuseTexture;
uniform sampler2D normalTexture;
uniform sampler2D ambientTexture;
uniform sampler2D specularTexture;
uniform sampler2D alphaTexture;
uniform sampler2D reflectionTexture;

uniform Material material;

uniform int numberOfLights;
uniform Light light[MAX_LIGHTS];

uniform AmbientLight ambientLight;

vec4 diffuseC;
vec4 ambientC;
float specularC;
vec4 emissiveC;
vec4 reflectiveC;

float reflection;

vec3 calculateNormal(Material material, vec3 normal, vec2 text_coord, mat4 modelViewMatrix){
    vec3 newNormal = normal;
    if(material.hasNormalTexture == 1){
        newNormal = texture(normalTexture,text_coord).rgb;
        newNormal = normalize(newNormal * 2 - 1);
        newNormal = normalize(out_modelViewMatrix * vec4(newNormal,0.0)).xyz;
    }

    return newNormal;
}

void setupColours(Material material, vec2 textCoord){
    if (material.hasDiffuseTexture == 1){
        diffuseC = texture(diffuseTexture, textCoord);
    }else{
        diffuseC = material.diffuseColor;
    }
    if(material.hasAmbientTexture == 1){
        ambientC = texture(ambientTexture,textCoord);
    }else{
        ambientC = material.ambientColor;
    }
    if(material.hasSpecularTexture == 1){
        specularC = texture(specularTexture,textCoord).r;
    }else{
        specularC = material.specularColor.r;
    }
    if(material.hasReflectionTexture == 1){
        reflection = normalize(texture(reflectionTexture,textCoord) * 2 -1).r;
    }else{
        reflection = material.reflectivity;
    }
}

vec4 calcLightColor(vec4 light_colour, float light_intensity, vec3 position, vec3 to_light_dir, vec3 normal)
{
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    vec4 specColor = vec4(0, 0, 0, 0);

    // Diffuse Light
    float diffuseFactor = max(dot(normal, to_light_dir), 0.0);
    diffuseColor = diffuseC * light_colour * light_intensity * diffuseFactor;

    // Specular Light
    vec3 camera_direction = normalize(-position);
    vec3 from_light_dir = -to_light_dir;
    vec3 reflected_light = normalize(reflect(from_light_dir , normal));
    float specularFactor = max( dot(camera_direction, reflected_light), 0.0);
    specularFactor = pow(specularFactor, material.shininess);
    specColor = specularC * light_intensity  * specularFactor * reflection * light_colour;

    return (diffuseColor + specColor);
}


vec4 calcPointLight(PointLight pointLight, vec3 position, vec3 normal){

    vec3 light_direction = pointLight.position - position;
    vec3 to_light_dir = normalize(light_direction);

    vec4 light_color = calcLightColor(pointLight.color,pointLight.intensity,position,to_light_dir,normal);

    // Attenuation
     float distance = length(light_direction);
     float attenuationInv = pointLight.att.constant + pointLight.att.linear * distance + pointLight.att.exponent *
     distance * distance;

    return light_color/ attenuationInv;
}

vec4 calcDirectLight(DirectLight directLight, vec3 position, vec3 normal){
   return calcLightColor(directLight.color,directLight.intensity,position,normalize(directLight.direction),
   normal);
}

vec4 calcSpotLight(SpotLight spotLight, vec3 position, vec3 normal){
   vec3 light_direction = spotLight.position - position;
   vec3 to_light_dir = normalize(light_direction);
   vec3 from_light_dir = -to_light_dir;
   float spot_alfa = dot(from_light_dir,normalize(spotLight.direction));

   vec4 color = vec4(0,0,0,0);
   if(spot_alfa > spotLight.angle){
        color = calcPointLight(PointLight(spotLight.position,spotLight.color,spotLight.att,spotLight.intensity),
        position,normal);
        color *= (1.0-(1.0 - spot_alfa)/(1.0-spotLight.angle));
   }

   return color;
}

vec4 calculateSpecularComp(Light light, vec3 position, vec3 normal){
    if(light.useDirectLight == 1){
        return calcDirectLight(light.directLight,position,normal);
    }else if (light.usePointLight == 1){
       return calcPointLight(light.pointLight,position,normal);
    }else if (light.useSpotLight == 1){
        return calcSpotLight(light.spotLight,position,normal);
    }
    return vec4(0,0,0,0);
}

vec4 calculateLight(){
    vec4 totalSpecularComp = vec4(0,0,0,0);
    vec3 currNormal = calculateNormal(material,out_vertexNormal,out_texture,out_modelViewMatrix);
    for (int i = 0; i < numberOfLights ; i++){

           totalSpecularComp+=calculateSpecularComp(light[i],out_vertexPosition,currNormal);
    }
    return totalSpecularComp;
}

void main(void){

    setupColours(material,out_texture);
    outColour = ambientC*ambientLight.color*ambientLight.intensity+calculateLight();

}