package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformInt extends Uniform {

    private int currentValue;
    private boolean used = false;

    public UniformInt(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadInt(int value) {
        if (!used || currentValue != value) {
            this.currentValue = value;
            this.used = true;
            GL20.glUniform1i(super.getLocation(), value);
        }
    }
}
