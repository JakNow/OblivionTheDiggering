package pl.oblivion.assets;

import org.apache.log4j.Logger;
import pl.oblivion.scene.Mesh;

import java.util.HashMap;
import java.util.Map;

public class ShapeCache {

    private static final Logger logger = Logger.getLogger(ShapeCache.class.getName());
    private static ShapeCache INSTANCE;

    private Map<String, Mesh> shapeMap;

    private ShapeCache() {
        shapeMap = new HashMap<>();
    }

    public static synchronized ShapeCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShapeCache();
            logger.info("Creating a new instnace of " + ShapeCache.class.getName() + ".");
        }
        return INSTANCE;
    }


    public Mesh getCubeShape(String name, float width, float height, float depth) {
        Mesh mesh = shapeMap.get(name);
        if (mesh == null) {
            logger.info("Creating new cube shape [W:" + width + "; H:" + height + "; D: " + depth + "].");
            mesh = createCubeShape(name, width, height, depth);
            shapeMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading shape for: " + name + ".");
            return mesh;
        }
    }

    private Mesh createCubeShape(String name, float width, float height, float depth) {
        Mesh mesh = MeshCache.getInstance().getMesh("primitives/cube.obj");
        Mesh.MeshData meshData = mesh.getMeshData();
        float[] newVertices = new float[meshData.getVertices().length];
        float furthestPoint = 0;
        for (int i = 0; i < newVertices.length; i += 3) {

            furthestPoint = ajustNewVertices(i, newVertices, meshData.getVertices(), width, furthestPoint);
            furthestPoint = ajustNewVertices(i + 1, newVertices, meshData.getVertices(), height, furthestPoint);
            furthestPoint = ajustNewVertices(i + 2, newVertices, meshData.getVertices(), depth, furthestPoint);
        }

        return Mesh.create(new Mesh.MeshData(name, meshData.getIndices(), newVertices, meshData.getTextures(), meshData
                .getNormals(), furthestPoint));
    }

    private float ajustNewVertices(int index, float[] vertices, float[] data, float multiplayer, float furthestPoint) {
        vertices[index] = data[index] * multiplayer;
        return compare(vertices[index], furthestPoint);
    }

    private float compare(float comp1, float comp2) {
        if (comp1 > comp2) {
            return comp1;
        }
        return comp2;
    }

    public Mesh getCapsuleShape(String name, float height, float radius) {
        Mesh mesh = shapeMap.get(name);
        if (mesh == null) {
            logger.info("Creating new capsule shape [H:" + height + "; R:" + radius + "].");
            mesh = createCapsuleShape(name, height, radius);
            shapeMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading shape for: " + name + ".");
            return mesh;
        }
    }

    private Mesh createCapsuleShape(String name, float height, float radius) {
        Mesh mesh = MeshCache.getInstance().getMesh("primitives/capsule.obj");
        Mesh.MeshData meshData = mesh.getMeshData();
        float[] newVertices = new float[meshData.getVertices().length];
        float furthestPoint = 0;
        for (int i = 0; i < newVertices.length; i += 3) {

            furthestPoint = ajustNewVertices(i, newVertices, meshData.getVertices(), radius, furthestPoint);
            furthestPoint = ajustNewVertices(i + 1, newVertices, meshData.getVertices(), height, furthestPoint);
            furthestPoint = ajustNewVertices(i + 2, newVertices, meshData.getVertices(), radius, furthestPoint);

        }

        return Mesh.create(new Mesh.MeshData(name, meshData.getIndices(), newVertices, meshData.getTextures(), meshData
                .getNormals(), furthestPoint));
    }

    public Mesh getPlaneShape(String name, float width, float depth) {
        Mesh mesh = shapeMap.get(name);
        if (mesh == null) {
            logger.info("Creating new plane shape [W:" + width + "; D:" + depth + "].");
            mesh = createPlaneShape(name, width, depth);
            shapeMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading shape for: " + name + ".");
            return mesh;
        }
    }

    private Mesh createPlaneShape(String name, float width, float depth) {
        Mesh mesh = MeshCache.getInstance().getMesh("primitives/plane.obj");
        Mesh.MeshData meshData = mesh.getMeshData();
        float[] newVertices = new float[meshData.getVertices().length];
        float furthestPoint = 0;
        for (int i = 0; i < newVertices.length; i += 3) {

            furthestPoint = ajustNewVertices(i, newVertices, meshData.getVertices(), width / 10, furthestPoint);
            furthestPoint = ajustNewVertices(i + 1, newVertices, meshData.getVertices(), 0, furthestPoint);
            furthestPoint = ajustNewVertices(i + 2, newVertices, meshData.getVertices(), depth / 10, furthestPoint);

        }

        return Mesh.create(new Mesh.MeshData(name, meshData.getIndices(), newVertices, meshData.getTextures(), meshData
                .getNormals(), furthestPoint));
    }

    public Mesh getQuadShape(String name, float width, float depth) {
        Mesh mesh = shapeMap.get(name);
        if (mesh == null) {
            logger.info("Creating new quad shape [W:" + width + "; D:" + depth + "].");
            mesh = createQuadShape(name, width, depth);
            shapeMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading shape for: " + name + ".");
            return mesh;
        }
    }

    private Mesh createQuadShape(String name, float width, float depth) {
        Mesh mesh = MeshCache.getInstance().getMesh("primitives/quad.obj");
        Mesh.MeshData meshData = mesh.getMeshData();
        float[] newVertices = new float[meshData.getVertices().length];
        float furthestPoint = 0;
        for (int i = 0; i < newVertices.length; i += 3) {

            furthestPoint = ajustNewVertices(i, newVertices, meshData.getVertices(), width, furthestPoint);
            furthestPoint = ajustNewVertices(i + 1, newVertices, meshData.getVertices(), 0, furthestPoint);
            furthestPoint = ajustNewVertices(i + 2, newVertices, meshData.getVertices(), depth, furthestPoint);

        }

        return Mesh.create(new Mesh.MeshData(name, meshData.getIndices(), newVertices, meshData.getTextures(), meshData
                .getNormals(), furthestPoint));
    }

    public Mesh getSphereShape(String name, float radius) {
        Mesh mesh = shapeMap.get(name);
        if (mesh == null) {
            logger.info("Creating new sphere shape [R:" + radius + "].");
            mesh = createSphereShape(name, radius);
            shapeMap.put(name, mesh);
            return mesh;
        } else {
            logger.info("Loading shape for: " + name + ".");
            return mesh;
        }
    }

    private Mesh createSphereShape(String name, float radius) {
        Mesh mesh = MeshCache.getInstance().getMesh("primitives/sphere.obj");
        Mesh.MeshData meshData = mesh.getMeshData();
        float[] newVertices = new float[meshData.getVertices().length];
        float furthestPoint = 0;
        for (int i = 0; i < newVertices.length; i++) {
            furthestPoint = ajustNewVertices(i, newVertices, meshData.getVertices(), radius, furthestPoint);
        }

        return Mesh.create(new Mesh.MeshData(name, meshData.getIndices(), newVertices, meshData.getTextures(), meshData
                .getNormals(), furthestPoint));
    }

}
