package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public class Cube extends Model {

    private static final Mesh mesh = AssetLoader.loadMesh("primitives/cube.obj");

    public Cube() {
        super("Cube", mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Cube(Transform transform) {
        super("Cube", transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Cube(String name, Transform transform) {
        super(name, transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Cube(Transform transform, Material material) {
        super("Cube", transform, mesh, material);
    }
}
