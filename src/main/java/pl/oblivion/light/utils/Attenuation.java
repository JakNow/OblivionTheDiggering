package pl.oblivion.light.utils;

public class Attenuation {

    private float constant;
    private float linear;
    private float exponent;

    public Attenuation(float constant, float linear, float exponent) {
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    public float getConstant() {
        return constant;
    }

    public float getLinear() {
        return linear;
    }

    public float getExponent() {
        return exponent;
    }
}
