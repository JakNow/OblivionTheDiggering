package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform {

    private boolean currentBool;
    private boolean used = false;

    public UniformBoolean(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadBoolean(boolean bool) {
        if (!used || currentBool != bool) {
            this.currentBool = bool;
            this.used = true;
            GL20.glUniform1i(super.getLocation(), bool ? 1 : 0);
        }
    }
}
