package pl.oblivion.light;

import org.joml.Vector4f;
import pl.oblivion.light.types.LightType;
import pl.oblivion.light.utils.Attenuation;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.GameObject;

public abstract class Light extends GameObject {

    private Vector4f color;
    private Attenuation attenuation;
    private LightType lightType;
    private float angle;
    private float intensity;


    public Light(Light light) {
        this(light.getName(), light.transform, new Vector4f(light
                        .getColor
                                ()),
                light
                        .getLightType(), light.getAttenuation(),
                light.getAngle(), light.getIntensity());
    }

    public Light(String name, Transform transform, Vector4f color, LightType lightType, Attenuation
            attenuation,
                 float angle, float intensity) {
        super(name, Light.class);
        this.color = color;
        this.lightType = lightType;
        this.attenuation = attenuation;
        this.angle = (float) Math.cos(Math.toRadians(angle));
        this.intensity = intensity;
        this.transform = transform;
    }

    public Vector4f getColor() {
        return color;
    }

    public LightType getLightType() {
        return lightType;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public float getAngle() {
        return angle;
    }

    public float getIntensity() {
        return intensity;
    }

}
