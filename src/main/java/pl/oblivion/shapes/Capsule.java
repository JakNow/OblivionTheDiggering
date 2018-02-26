package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public class Capsule extends Model {

    private static final Mesh mesh = AssetLoader.loadMesh("primitives/capsule.obj");

    public Capsule() {
        super("Capsule", mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Capsule(Transform transform) {
        super("Capsule", transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Capsule(Transform transform, Material material) {
        super("Capsule", transform, mesh, material);
    }

}
