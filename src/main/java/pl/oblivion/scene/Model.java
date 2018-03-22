package pl.oblivion.scene;

import org.apache.log4j.Logger;
import org.joml.Quaternionf;
import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;

public class Model extends GameObject implements Moveable {

    private static final Logger logger = Logger.getLogger(Model.class.getName());
    private MatMesh matMesh;

    public Model(String name, Mesh mesh) {
        super(name, Model.class);
        if (mesh == null) {
            logger.error("Mesh can't be null", new IllegalStateException());
        }
        this.matMesh = new MatMesh(mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
        this.transform = new Transform();
        logger.info("Created Model \'" + name + "\' with mesh and material.");
    }

    public Model(String name, Mesh mesh, Material material) {
        super(name, Model.class);
        if (mesh == null) {
            logger.error("Mesh can't be null", new IllegalStateException());
        }
        if (material == null) {
            logger.error("Material can't be null", new IllegalStateException());
        }
        this.matMesh = new MatMesh(mesh, material);
        this.transform = new Transform();
        logger.info("Created Model \'" + name + "\' with mesh and material.");
    }

    public Model(String name, Transform transform, Mesh mesh, Material material) {
        super(name, Model.class);
        if (mesh == null) {
            logger.error("Mesh can't be null", new IllegalStateException());
        }
        if (material == null) {
            logger.error("Material can't be null", new IllegalStateException());
        }
        this.matMesh = new MatMesh(mesh, material);
        this.transform = new Transform(transform);

        logger.info("Created Model \'" + name + "\' with mesh and material.");
    }

    public MatMesh getMatMesh() {
        return matMesh;
    }

    @Override
    public void rotate(Quaternionf rotation, float delta) {
        this.transform.rotate(rotation, delta);
    }

    @Override
    public void translate(float dx, float dy, float dz, float delta) {

    }

    public void increaseRotation(float dt) {
        //this.transform.rotation.rotateY((float)Math.toRadians(dt));
    }

    public void update(float delta) {

    }
}
