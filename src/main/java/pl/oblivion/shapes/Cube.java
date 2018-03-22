package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.assets.ShapeCache;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Model;

public abstract class Cube extends Model {

    public Cube(String name, float width, float height, float depth, Transform transform) {
        super(name, transform, ShapeCache.getInstance().getCubeShape(name, width, height, depth), AssetLoader.loadMaterial
                (AssetLoader
                        .DEFAULT_MATERIAL));

    }

}
