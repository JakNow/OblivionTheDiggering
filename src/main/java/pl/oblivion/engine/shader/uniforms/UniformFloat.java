package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

/**
 * @author jakubnowakowski
 * Created at 24.01.2018
 */
public class UniformFloat extends Uniform {

    private float currentValue;
    private boolean used = false;

    public UniformFloat(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadFloat(float value) {
        if (!used || currentValue != value) {
            this.currentValue = value;
            this.used = true;
            GL20.glUniform1f(super.getLocation(), value);
        }
    }
}
