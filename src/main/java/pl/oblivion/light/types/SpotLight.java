package pl.oblivion.light.types;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.light.Light;
import pl.oblivion.light.utils.Attenuation;
import pl.oblivion.math.Transform;

public class SpotLight extends Light {


    public SpotLight(SpotLight spotLight) {
        this(spotLight.getName(), spotLight.transform.translation, spotLight.transform.rotation,
                spotLight
                        .getColor
                                (), spotLight
                        .getAngle(), spotLight.getAttenuation(), spotLight.getIntensity());
    }

    public SpotLight(String name, Vector3f position, Quaternionf direction, Vector4f color, float angle, Attenuation
            attenuation,
                     float intensity) {
        super(name, new Transform(position, direction, null), color, LightType.SpotLight, attenuation, angle, intensity);
    }

    @Override
    public void update(float delta) {

    }
}