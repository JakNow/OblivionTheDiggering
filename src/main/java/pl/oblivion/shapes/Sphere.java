package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.assets.ShapeCache;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public abstract class Sphere extends Model {



    public Sphere(String name, float radius, Transform transform) {
        super("Sphere", transform, ShapeCache.getInstance().getSphereShape(name,radius), AssetLoader.loadMaterial
                (AssetLoader
                .DEFAULT_MATERIAL));
    }

}
