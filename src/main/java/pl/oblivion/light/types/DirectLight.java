package pl.oblivion.light.types;

import org.joml.Quaternionf;
import org.joml.Vector4f;
import pl.oblivion.light.Light;
import pl.oblivion.math.Transform;

public abstract class DirectLight extends Light {


    public DirectLight(DirectLight directLight) {
        this(directLight.getName(), directLight.transform.rotation, directLight.getColor(), directLight.getIntensity());
    }

    public DirectLight(String name, Quaternionf direction, Vector4f colour, float intensity) {
        super(name, new Transform(null,direction,null), colour, LightType.DirectLight, null, 0, intensity);
    }
}
