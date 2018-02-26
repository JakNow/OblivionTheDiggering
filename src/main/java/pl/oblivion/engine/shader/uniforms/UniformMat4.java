package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class UniformMat4 extends Uniform {

    private static FloatBuffer mat4Buffer = BufferUtils.createFloatBuffer(16);

    public UniformMat4(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadMat4(Matrix4f matrix4f) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            matrix4f.get(buffer);
            GL20.glUniformMatrix4fv(super.getLocation(), false, buffer);
        }
    }
}
