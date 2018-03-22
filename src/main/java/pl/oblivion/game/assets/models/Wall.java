package pl.oblivion.game.assets.models;

import pl.oblivion.math.Transform;
import pl.oblivion.shapes.Cube;

public class Wall extends Cube {

    public Wall(float width, float height, float depth, Transform transform) {
        super("Wall", width, height, depth, transform);
    }

    @Override
    public void update(float delta) {

    }
}
