package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.assets.ShapeCache;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Model;

public abstract class Capsule extends Model {

    private float height;
    private float radius;

    public Capsule(String name, float height, float radius, Transform transform) {
        super(name, transform, ShapeCache.getInstance().getCapsuleShape(name, height, radius), AssetLoader
                .loadMaterial
                        (AssetLoader
                                .DEFAULT_MATERIAL));
        this.height = height;
        this.radius = radius;
    }
}
