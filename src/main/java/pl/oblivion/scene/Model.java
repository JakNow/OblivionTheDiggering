package pl.oblivion.scene;

import org.apache.log4j.Logger;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Quaternion;
import pl.oblivion.math.Transform;

public class Model extends GameObject implements Moveable{

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
        this.transform = transform;
        this.worldTransform = new Transform(new Vector3f(transform.translation),new Quaternion(transform
                .rotation),new Vector3f(1,1,1));
        logger.info("Created Model \'" + name + "\' with mesh and material.");
    }

    public MatMesh getMatMesh() {
        return matMesh;
    }

    @Override
    public void rotate(float dx, float dy, float dz, float delta) {
        this.transform.rotation.add(dx*delta,dy*delta, dz*delta);
        calculatePosition(dx*delta,dy*delta,dz*delta);

    }

    @Override
    public void rotate(Vector3f dp, float delta) {
        this.transform.rotation.add(dp.x*delta,dp.y*delta,dp.z*delta);

    }

    @Override
    public void translate(float dx, float dy, float dz, float delta) {
        this.transform.translation.x += dx * delta;
        this.transform.translation.y += dy * delta;
        this.transform.translation.z += dz * delta;
    }

    @Override
    public void translate(Vector3f dp, float delta) {
        this.transform.translation.x += dp.x * delta;
        this.transform.translation.y += dp.y * delta;
        this.transform.translation.z += dp.z * delta;
    }


    /**
     * ROTATING AROUND 3 POINTS CREATES GIMBAL LOCK! << TODO fix
     * @param dx
     * @param dy
     * @param dz
     */
    private void calculatePosition(float dx, float dy, float dz){
        for(GameObject child : getChildren()){
            child.transform.rotateAroundPoint(dx,dy,dz,this.transform);
        }
    }
}
