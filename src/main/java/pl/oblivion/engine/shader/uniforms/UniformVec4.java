package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;

public class UniformVec4 extends Uniform {

    private float currentX;
    private float currentY;
    private float currentZ;
    private float currentW;
    private boolean used = false;

    public UniformVec4(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadVec4(Vector4f vector4f) {
        this.loadVec4(vector4f.x, vector4f.y, vector4f.z, vector4f.w);
    }

    public void loadVec4(float x, float y, float z, float w) {
        if (!used || x != currentX || y != currentY || z != currentZ || w != currentW) {
            this.currentX = x;
            this.currentY = y;
            this.currentZ = z;
            this.currentW = w;
            this.used = true;
            GL20.glUniform4f(super.getLocation(), x, y, z, w);
        }
    }
}
