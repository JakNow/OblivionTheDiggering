package pl.oblivion.scene;

import pl.oblivion.math.Transform;

public class Scene extends GameObject {


    public Scene(String name, Transform transform) {
        super(name, Scene.class);
        this.transform = new Transform(transform);

    }

    @Override
    public void update(float delta) {

    }

}
