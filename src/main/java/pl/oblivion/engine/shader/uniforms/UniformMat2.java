package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.joml.Matrix3x2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class UniformMat2 extends Uniform {

    private static FloatBuffer mat2Buffer = BufferUtils.createFloatBuffer(4);

    public UniformMat2(String name) {
        super(name);
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadMat2(Matrix3x2f matrix3x2f) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4);
            matrix3x2f.get(buffer);
            GL20.glUniformMatrix2fv(super.getLocation(), false, buffer);
        }
    }
}
