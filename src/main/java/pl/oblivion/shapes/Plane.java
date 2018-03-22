package pl.oblivion.shapes;

import pl.oblivion.assets.AssetLoader;
import pl.oblivion.assets.ShapeCache;
import pl.oblivion.material.Material;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

public abstract class Plane extends Model {


    public Plane(String name, float width,float depth,Transform transform) {
        super("Plane", transform, ShapeCache.getInstance().getPlaneShape(name,width,depth), AssetLoader.loadMaterial
                (AssetLoader.DEFAULT_MATERIAL));
    }

}
