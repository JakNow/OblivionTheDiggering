package pl.oblivion.light.types;

import org.joml.Vector4f;
import pl.oblivion.light.Light;

public class AmbientLight extends Light {


    public AmbientLight(Vector4f color, float intensity) {
        super(null, null, color, LightType.Ambient, null, 0, intensity);
    }
}
