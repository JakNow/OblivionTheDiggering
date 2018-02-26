package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Matrix3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class UniformMat3 extends Uniform {

    private static FloatBuffer mat4Buffer = BufferUtils.createFloatBuffer(9);

    public UniformMat3(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadMat3(Matrix3f matrix3f) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(9);
            matrix3f.get(buffer);
            GL20.glUniformMatrix3fv(super.getLocation(), false, buffer);
        }
    }
}
