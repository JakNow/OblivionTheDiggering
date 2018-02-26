package pl.oblivion.assets;

import org.apache.log4j.Logger;
import org.joml.Vector4f;
import pl.oblivion.export.OwnFile;
import pl.oblivion.material.Material;
import pl.oblivion.material.MaterialDataType;
import pl.oblivion.material.MaterialParam;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MaterialParser {
    private static final Logger logger = Logger.getLogger(MaterialParam.class.getName());
    private static final String NEWMTL = "newmtl ";
    private static final String AMBIENT_COLOUR = "Ka ";
    private static final String DIFFUSE_COLOUR = "Kd ";
    private static final String SPECULAR_COLOUR = "Ks ";
    private static final String EMISSIVE_COLOUR = "Ke ";
    private static final String SHININESS = "Ns ";
    private static final String REFLECTIVITY = "Ni ";
    private static final String AMBIENT_TEXTURE = "map_Ka ";
    private static final String DIFFUSE_TEXTURE = "map_Kd ";
    private static final String NORMAL_TEXTURE = "map_bump ";
    private static final String SPECULAR_TEXTURE = "map_Ks ";
    private static final String ALPHA_TEXTURE = "map_d ";
    private static MaterialCache materialCache = MaterialCache.getInstance();
    private static TextureCache textureCache = TextureCache.getInstance();
    private static int counter = -1;
    private static boolean alreadyInMaterials = false;
    private static List<Material> materialList;
    private static Consumer<String> processLines = (line) -> {
        if (line.startsWith(NEWMTL)) {
            String name = line.split(NEWMTL)[1];
            Material material = materialCache.getMaterialMap().get(name);
            if (material != null) {
                alreadyInMaterials = true;
                logger.info("Material: " + name + " is already on a list. Finding next.");
            } else {
                alreadyInMaterials = false;
                material = new Material(name);
            }
            counter++;
            materialList.add(counter, material);
        } else if (!alreadyInMaterials) {
            if (line.startsWith(AMBIENT_COLOUR)) {
                String[] splitedLine = line.split(" ");
                Vector4f vector4f = new Vector4f(Float.parseFloat(splitedLine[1]), Float.parseFloat(splitedLine[2]), Float.parseFloat(splitedLine[3]), 1.0f);
                materialList.get(counter).setMaterialParam(MaterialDataType.AmbientColor, vector4f);
            } else if (line.startsWith(DIFFUSE_COLOUR)) {
                String[] splitedLine = line.split(" ");
                Vector4f vector4f = new Vector4f(Float.parseFloat(splitedLine[1]), Float.parseFloat(splitedLine[2]), Float.parseFloat(splitedLine[3]), 1.0f);
                materialList.get(counter).setMaterialParam(MaterialDataType.DiffuseColor, vector4f);
            } else if (line.startsWith(SPECULAR_COLOUR)) {
                String[] splitedLine = line.split(" ");
                Vector4f vector4f = new Vector4f(Float.parseFloat(splitedLine[1]), Float.parseFloat(splitedLine[2]), Float.parseFloat(splitedLine[3]), 1.0f);
                materialList.get(counter).setMaterialParam(MaterialDataType.SpecularColor, vector4f);
            } else if (line.startsWith(EMISSIVE_COLOUR)) {
                String[] splitedLine = line.split(" ");
                Vector4f vector4f = new Vector4f(Float.parseFloat(splitedLine[1]), Float.parseFloat(splitedLine[2]), Float.parseFloat(splitedLine[3]), 1.0f);
                materialList.get(counter).setMaterialParam(MaterialDataType.EmissiveColor, vector4f);
            } else if (line.startsWith(SHININESS)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.Shininess, Float.parseFloat(splitedLine[1]));
            } else if (line.startsWith(REFLECTIVITY)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.Reflectivity, Float.parseFloat(splitedLine[1]));
            } else if (line.startsWith(DIFFUSE_TEXTURE)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.DiffuseTexture, textureCache.getTexture(AssetLoader.TEXTURES_PATH, splitedLine[1]));
            } else if (line.startsWith(NORMAL_TEXTURE)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.NormalTexture, textureCache.getTexture(AssetLoader.TEXTURES_PATH, splitedLine[1]));
            } else if (line.startsWith(AMBIENT_TEXTURE)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.AmbientTexture, textureCache.getTexture(AssetLoader.TEXTURES_PATH, splitedLine[1]));
            } else if (line.startsWith(SPECULAR_TEXTURE)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.SpecularTexture, textureCache.getTexture(AssetLoader.TEXTURES_PATH, splitedLine[1]));
            } else if (line.startsWith(ALPHA_TEXTURE)) {
                String[] splitedLine = line.split(" ");
                materialList.get(counter).setMaterialParam(MaterialDataType.AlphaTexture, textureCache.getTexture(AssetLoader.TEXTURES_PATH, splitedLine[1]));

            }
        }

    };

    public static void loadMaterial(String materialFile) {
        materialList = new ArrayList<>();
        counter = -1;
        alreadyInMaterials = false;
        OwnFile ownFile = new OwnFile(AssetLoader.MATERIALS_PATH.concat(materialFile));

        try (Stream<String> stream = ownFile.getReader().lines()) {
            stream.forEach(processLines);
        }

        for (Material material : materialList) {
            materialCache.addMaterialToMap(material);
        }
    }

}
