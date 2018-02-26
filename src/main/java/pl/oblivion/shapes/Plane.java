package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public class Plane extends Model {

    private static final Mesh mesh = AssetLoader.loadMesh("primitives/plane.obj");

    public Plane() {
        super("Plane", mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Plane(Transform transform) {
        super("Plane", transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Plane(Transform transform, Material material) {
        super("Plane", transform, mesh, material);
    }
}
