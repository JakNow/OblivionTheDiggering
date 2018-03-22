package pl.oblivion.light.types;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.light.Light;
import pl.oblivion.light.utils.Attenuation;
import pl.oblivion.math.Transform;

public class PointLight extends Light {

    public PointLight(String name, Vector3f position, Vector4f colour, Attenuation attenuation, float intensity) {
        super(name, new Transform(position,null,null), colour, LightType.PointLight, attenuation, 0, intensity);
    }

    public PointLight(Light light) {
        super(light.getName(), light.transform, light.getColor(), LightType.PointLight, light.getAttenuation(),
                0, light
                        .getIntensity());
    }

    @Override
    public void update(float delta) {

    }
}
