package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.assets.ShapeCache;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public abstract class Quad extends Model {


    public Quad(String name, float width, float depth,Transform transform) {
        super("Quad", transform, ShapeCache.getInstance().getQuadShape(name,width,depth), AssetLoader.loadMaterial
                (AssetLoader
                .DEFAULT_MATERIAL));
    }

}
