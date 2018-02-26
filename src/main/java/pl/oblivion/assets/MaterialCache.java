package pl.oblivion.assets;

import org.apache.log4j.Logger;
import org.joml.Vector4f;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIString;
import pl.oblivion.material.Material;
import pl.oblivion.material.MaterialDataType;
import pl.oblivion.material.Texture;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.assimp.Assimp.*;

class MaterialCache {

    private static final Logger logger = Logger.getLogger(TextureCache.class.getName());
    private static final String TEXTURES_PATH = AssetLoader.TEXTURES_PATH;
    private static MaterialCache INSTANCE;
    private Map<String, Material> materialMap;

    private MaterialCache() {
        materialMap = new HashMap<>();
        createDefaultMaterial(AssetLoader.DEFAULT_MATERIAL);
    }

    private void createDefaultMaterial(String name) {
        Material material = new Material(name);

        material.setMaterialParam(MaterialDataType.AmbientColor, new Vector4f(Material.DEFAULT_COLOR));
        material.setMaterialParam(MaterialDataType.DiffuseColor, new Vector4f(Material.DEFAULT_COLOR));
        material.setMaterialParam(MaterialDataType.SpecularColor, new Vector4f(Material.DEFAULT_COLOR));
        material.setMaterialParam(MaterialDataType.EmissiveColor, new Vector4f(Material.DEFAULT_COLOR));
        material.setMaterialParam(MaterialDataType.ReflectiveColor, new Vector4f(Material.DEFAULT_COLOR));

        material.setMaterialParam(MaterialDataType.Shininess, 100.0f);
        material.setMaterialParam(MaterialDataType.Reflectivity, 1.0f);

        materialMap.put(name, material);
    }

    public static synchronized MaterialCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MaterialCache();
            logger.info("Creating a new instance of " + MaterialCache.class.getName());
        }
        return INSTANCE;
    }

    public Material getMaterial(String name) {
        Material material = materialMap.get(name);
        if (material == null) {
            material = AssetLoader.importMaterial(name);
            materialMap.put(name, material);
        }
        return material;
    }

    Material createMaterial(String name, AIMaterial aiMaterial, TextureCache textureCache) {
        Material material = materialMap.get(name);
        if (material == null) {
            material = new Material(name);
            AIColor4D aiColor4D = AIColor4D.create();
            material.setAmbientColour(getMaterialColors(aiMaterial, AI_MATKEY_COLOR_AMBIENT, aiColor4D));
            material.setDiffuseColour(getMaterialColors(aiMaterial, AI_MATKEY_COLOR_DIFFUSE, aiColor4D));
            material.setSpecularColour(getMaterialColors(aiMaterial, AI_MATKEY_COLOR_SPECULAR, aiColor4D));
            material.setEmissiveColour(getMaterialColors(aiMaterial, AI_MATKEY_COLOR_EMISSIVE, aiColor4D));
            material.setReflectiveColour(getMaterialColors(aiMaterial, AI_MATKEY_COLOR_REFLECTIVE, aiColor4D));

            setTexture(material, getTexture(aiMaterial, aiTextureType_DIFFUSE, textureCache), MaterialDataType.DiffuseTexture);
            setTexture(material, getTexture(aiMaterial, aiTextureType_NORMALS, textureCache), MaterialDataType.NormalTexture);
            setTexture(material, getTexture(aiMaterial, aiTextureType_AMBIENT, textureCache), MaterialDataType.AmbientTexture);
            setTexture(material, getTexture(aiMaterial, aiTextureType_SPECULAR, textureCache), MaterialDataType.SpecularTexture);
            setTexture(material, getTexture(aiMaterial, aiTextureType_OPACITY, textureCache), MaterialDataType.AlphaTexture);

            material.setShininess(100.0f);
            material.setReflectivity(1.0f);

            materialMap.put(name, material);
        }

        return material;
    }

    private static Vector4f getMaterialColors(AIMaterial aiMaterial, String colorType, AIColor4D aiColor4D) {
        int result = aiGetMaterialColor(aiMaterial, colorType, aiTextureType_NONE, 0, aiColor4D);
        if (result == 0) {
            return new Vector4f(aiColor4D.r(), aiColor4D.g(), aiColor4D.b(), aiColor4D.a());
        }
        return new Vector4f(Material.DEFAULT_COLOR);

    }

    private static void setTexture(Material material, Texture texture, MaterialDataType materialDataType) {
        if (texture != null) {
            switch (materialDataType) {
                case DiffuseTexture:
                    material.setDiffuseTexture(texture);
                    break;
                case NormalTexture:
                    material.setNormalTexture(texture);
                    break;
                case AmbientTexture:
                    material.setAmbientTexture(texture);
                    break;
                case SpecularTexture:
                    material.setSpecularTexture(texture);
                    break;
                case AlphaTexture:
                    material.setAlphaTexture(texture);
                    break;
            }
        }
    }

    private static Texture getTexture(AIMaterial aiMaterial,
                                      int
                                              aiTextureType_, TextureCache textureCache) {
        AIString path = AIString.calloc();
        aiGetMaterialTexture(aiMaterial, aiTextureType_, 0, path, (IntBuffer) null, null, null, null, null, null);
        if (path.dataString().length() > 0) {
            return textureCache.getTexture(TEXTURES_PATH, path.dataString());
        } else {
            logger.error("Couldn't find the texture" + path.dataString() + " for material " + aiMaterial.toString());
            return null;
        }
    }

    public Map<String, Material> getMaterialMap() {
        return materialMap;
    }

    public void addMaterialToMap(Material material) {
        materialMap.put(material.getName(), material);
    }

    public void cleanUp() {
        for (String key : materialMap.keySet()) {
            //TODO material clean up
        }
    }
}
