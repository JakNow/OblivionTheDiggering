package pl.oblivion.scene;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.math.Transform;

import java.util.LinkedList;

public class Scene extends GameObject {


    public Scene(String name, Transform transform) {
        super(name, Scene.class);
        this.transform = new Transform(transform);

    }

    @Override
    public void update(float delta) {

    }

}
