package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformSampler extends Uniform {

    private int textureUnit;
    private boolean used = false;

    public UniformSampler(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadTexUnit(int textureUnit) {
        if (!used || this.textureUnit != textureUnit) {
            this.textureUnit = textureUnit;
            this.used = true;
            GL20.glUniform1i(super.getLocation(), textureUnit);
        }
    }
}