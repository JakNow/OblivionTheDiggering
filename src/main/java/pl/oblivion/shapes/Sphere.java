package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public class Sphere extends Model {

    private static final Mesh mesh = AssetLoader.loadMesh("primitives/sphere.obj");

    public Sphere() {
        super("Sphere", mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Sphere(Transform transform) {
        super("Sphere", transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Sphere(Transform transform, Material material) {
        super("Sphere", transform, mesh, material);
    }
}
