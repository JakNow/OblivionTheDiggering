package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

public class UniformVec3 extends Uniform {

    private float currentX;
    private float currentY;
    private float currentZ;
    private boolean used = false;

    public UniformVec3(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadVec3(Vector3f vector3f) {
        this.loadVec3(vector3f.x, vector3f.y, vector3f.z);
    }

    public void loadVec3(float x, float y, float z) {
        if (!used || x != currentX || y != currentY || z != currentZ) {
            this.currentX = x;
            this.currentY = y;
            this.currentZ = z;
            this.used = true;
            GL20.glUniform3f(super.getLocation(), x, y, z);
        }
    }
}
