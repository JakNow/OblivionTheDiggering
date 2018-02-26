package pl.oblivion.assets;

import org.apache.log4j.Logger;
import pl.oblivion.math.Maths;
import pl.oblivion.scene.Mesh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeshCache {
    private static final Logger logger = Logger.getLogger(MeshCache.class.getName());
    private static MeshCache INSTANCE;

    private Map<String, Mesh> meshMap;

    private MeshCache() {
        meshMap = new HashMap<>();
    }

    public static synchronized MeshCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MeshCache();
            logger.info("Creating a new instance of " + MeshCache.class.getName() + ".");
        }
        return INSTANCE;
    }

    public Mesh getMesh(String name) {
        Mesh mesh = meshMap.get(name);
        if (mesh == null) {
            logger.info("Importing a new mesh.");
            mesh = AssetLoader.importMesh(name);
            meshMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading mesh from " + MeshCache.class.getName());
            return mesh;
        }
    }

    Mesh createMesh(String name, List<Float> vertices, List<Float> textures, List<Float> normals, List<Integer> indices, float furthestPoint) {
        Mesh mesh = meshMap.get(name);
        if (mesh == null) {
            Mesh.MeshData meshData = new Mesh.MeshData(name, Maths.listIntToArray(indices), Maths.listFloatToArray(vertices), Maths.listFloatToArray(textures), Maths.listFloatToArray(normals), furthestPoint);
            mesh = Mesh.create(meshData);

            meshMap.put(name, mesh);
        }

        return mesh;
    }

    public void cleanUp() {
        for (String key : meshMap.keySet()) {
            meshMap.get(key).delete();
        }
    }
}
