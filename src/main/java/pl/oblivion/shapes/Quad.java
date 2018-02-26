package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public class Quad extends Model {

    private static final Mesh mesh = AssetLoader.loadMesh("primitives/quad.obj");


    public Quad() {
        super("Quad", mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Quad(Transform transform) {
        super("Quad", transform, mesh, AssetLoader.loadMaterial(AssetLoader.DEFAULT_MATERIAL));
    }

    public Quad(Transform transform, Material material) {
        super("Quad", transform, mesh, material);
    }
}
