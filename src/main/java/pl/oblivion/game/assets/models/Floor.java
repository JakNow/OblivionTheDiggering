package pl.oblivion.game.assets.models;

import pl.oblivion.math.Transform;
import pl.oblivion.shapes.Plane;

public class Floor extends Plane {

    public Floor(float width, float depth, Transform transform) {
        super("Floor", width, depth, transform);
    }

    @Override
    public void update(float delta) {

    }
}
