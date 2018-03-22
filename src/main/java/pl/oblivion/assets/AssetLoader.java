package pl.oblivion.assets;

import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import pl.oblivion.material.Material;
import pl.oblivion.material.Texture;
import pl.oblivion.math.Maths;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.*;

public class AssetLoader {
    public static final String TEXTURES_PATH = "assets/textures/";
    public static final String MATERIALS_PATH = "assets/materials/";
    public static final String DEFAULT_MATERIAL = "Default Material";
    private static final Logger logger = Logger.getLogger(AssetLoader.class.getName());
    private static final String MODELS_PATH = "src/main/resources/assets/models/";
    private static final ModelCache modelCache = ModelCache.getInstance();
    private static final MeshCache meshCache = MeshCache.getInstance();
    private static final MaterialCache materialCache = MaterialCache.getInstance();
    private static final TextureCache textureCache = TextureCache.getInstance();

    public static Model loadModel(String name) {
        return modelCache.getModel(name);
    }

    public static Mesh loadMesh(String name) {
        return meshCache.getMesh(name);
    }

    public static Material loadMaterial(String name) {
        return materialCache.getMaterial(name);
    }

    public static Texture loadTexture(String name, String path) {
        return textureCache.getTexture(name, TEXTURES_PATH + path);
    }

    static Model importModel(String modelName) {
        AIScene aiScene = aiImportFile(MODELS_PATH + modelName, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
        if (aiScene == null) {
            logger.error("Couldn't import model from file: " + modelName, new Exception());
            System.exit(-1);
        }
        PointerBuffer aiMaterials = aiScene.mMaterials();
        Material material;
        if (aiMaterials.sizeof() > 0) {
            material = processMaterial(AIMaterial.create(aiMaterials.get(0)));
        } else {
            material = materialCache.getMaterial(DEFAULT_MATERIAL);
        }

        PointerBuffer aiMeshes = aiScene.mMeshes();
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float furthestPoint = 0;

        while (aiMeshes.hasRemaining()) {
            float currentFurthestPoint = groupAllMeshes(AIMesh.create(aiMeshes.get()), vertices, textures, normals, indices);
            if (currentFurthestPoint > furthestPoint) {
                furthestPoint = currentFurthestPoint;
            }
        }

        return new Model(modelName, importMesh(aiScene, modelName), material);
    }

    private static Material processMaterial(AIMaterial aiMaterial) {
        AIString aiName = AIString.create();
        String name = null;
        int result = aiGetMaterialString(aiMaterial, AI_MATKEY_NAME, aiTextureType_NONE, 0, aiName);
        if (result == 0) {
            name = aiName.dataString();
        }
        return materialCache.createMaterial(name, aiMaterial, textureCache);
    }

    private static float groupAllMeshes(AIMesh aiMesh, List<Float> vertices, List<Float> textures, List<Float> normals, List<Integer> indices) {
        processTextures(aiMesh, textures);
        processNormals(aiMesh, normals);
        processIndices(aiMesh, indices);

        return processVertices(aiMesh, vertices);
    }

    private static Mesh importMesh(AIScene aiScene, String meshName) {
        PointerBuffer aiMeshes = aiScene.mMeshes();
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float furthestPoint = 0;

        while (aiMeshes.hasRemaining()) {
            float currentFurthestPoint = groupAllMeshes(AIMesh.create(aiMeshes.get()), vertices, textures, normals, indices);
            if (currentFurthestPoint > furthestPoint) {
                furthestPoint = currentFurthestPoint;
            }
        }

        return Mesh.create(new Mesh.MeshData(meshName, Maths.listIntToArray(indices), Maths.listFloatToArray(vertices), Maths.listFloatToArray(textures), Maths.listFloatToArray(normals), furthestPoint));
    }

    private static void processTextures(AIMesh aiMesh, List<Float> textures) {
        AIVector3D.Buffer aiTextures = aiMesh.mTextureCoords(0);
        while (aiTextures != null && aiTextures.hasRemaining()) {
            AIVector3D aiTexture = aiTextures.get();
            textures.add(aiTexture.x());
            textures.add(1 - aiTexture.y());
        }
    }

    private static void processNormals(AIMesh aiMesh, List<Float> normals) {
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        while (aiNormals != null && aiNormals.hasRemaining()) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }
    }

    private static void processIndices(AIMesh aiMesh, List<Integer> indices) {
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        while (aiFaces != null && aiFaces.hasRemaining()) {
            AIFace aiFace = aiFaces.get();
            IntBuffer buffer = aiFace.mIndices();
            while (buffer != null && buffer.hasRemaining()) {
                indices.add(buffer.get());
            }
        }
    }

    private static float processVertices(AIMesh aiMesh, List<Float> vertices) {
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        float furthestPoint = 0;
        while (aiVertices != null && aiVertices.hasRemaining()) {
            AIVector3D aiVertex = aiVertices.get();
            Vector3f vertex = new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z());
            furthestPoint = vertex.length() > furthestPoint ? vertex.length() : furthestPoint;
            Maths.vector3fToList(vertex, vertices);
        }
        return furthestPoint;
    }

    public static void importFile(String fileName) {
        AIScene aiScene = aiImportFile(MODELS_PATH + fileName, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
        if (aiScene == null) {
            logger.error("Couldn't import scene from file: " + fileName, new Exception());
            System.exit(-1);
        }

        List<Material> materials = new ArrayList<>();
        PointerBuffer aiMaterials = aiScene.mMaterials();
        while (aiMaterials.hasRemaining()) {
            materials.add(processMaterial(AIMaterial.create(aiMaterials.get())));
        }

        PointerBuffer aiMeshes = aiScene.mMeshes();
        while (aiMeshes.hasRemaining()) {
            processModel(AIMesh.create(aiMeshes.get()), materials);
        }
    }

    private static void processModel(AIMesh aiMesh, List<Material> materials) {
        Mesh mesh = processMesh(aiMesh);

        int materialIndex = aiMesh.mMaterialIndex();
        if (materialIndex > 0 && materialIndex < materials.size()) {
            modelCache.createModel(mesh, materials.get(materialIndex));
        } else {
            modelCache.createModel(mesh, new Material(mesh.getMeshData().getName()));
        }
    }

    private static Mesh processMesh(AIMesh aiMesh) {
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float furthestPoint = processVertices(aiMesh, vertices);
        processTextures(aiMesh, textures);
        processNormals(aiMesh, normals);
        processIndices(aiMesh, indices);
        String name = aiMesh.mName().dataString();

        return meshCache.createMesh(name, vertices, textures, normals, indices, furthestPoint);
    }

    static Mesh importMesh(String modelName) {
        AIScene aiScene = aiImportFile(MODELS_PATH + modelName, aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals);
        if (aiScene == null) {
            logger.error("Couldn't import model from file: " + modelName, new Exception());
            System.exit(-1);
        }

        PointerBuffer aiMeshes = aiScene.mMeshes();
        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Float> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float furthestPoint = 0;

        while (aiMeshes.hasRemaining()) {
            float currentFurthestPoint = groupAllMeshes(AIMesh.create(aiMeshes.get()), vertices, textures, normals, indices);
            if (currentFurthestPoint > furthestPoint) {
                furthestPoint = currentFurthestPoint;
            }
        }

        return Mesh.create(new Mesh.MeshData(modelName, Maths.listIntToArray(indices), Maths.listFloatToArray(vertices), Maths.listFloatToArray(textures), Maths.listFloatToArray(normals), furthestPoint));
    }

    static Material importMaterial(String materialName) {
        MaterialParser.loadMaterial(materialName);
        return materialCache.getMaterial(materialName);
    }

    public static void cleanUp() {
        modelCache.cleanUp();
        meshCache.cleanUp();
        materialCache.cleanUp();
        textureCache.cleanUp();

    }
}
