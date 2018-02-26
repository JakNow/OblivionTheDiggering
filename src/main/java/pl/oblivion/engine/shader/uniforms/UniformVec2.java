package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL20;

public class UniformVec2 extends Uniform {

    private float currentX;
    private float currentY;
    private boolean used = false;

    public UniformVec2(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadVec2(Vector2f vector2f) {
        this.loadVec2(vector2f.x, vector2f.y);
    }

    public void loadVec2(float x, float y) {
        if (!used || x != currentX || y != currentY) {
            this.currentX = x;
            this.currentY = y;
            this.used = true;
            GL20.glUniform2f(super.getLocation(), x, y);
        }
    }
}
