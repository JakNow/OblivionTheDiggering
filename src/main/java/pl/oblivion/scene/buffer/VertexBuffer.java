package pl.oblivion.scene.buffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBuffer {

    private final int id;
    private final Type type;
    private VertexBuffer(int id, Type type) {
        this.id = id;
        this.type = type;
    }

    public static VertexBuffer create(Type type) {
        int id = GL15.glGenBuffers();
        return new VertexBuffer(id, type);
    }

    public void bind(int type) {
        GL15.glBindBuffer(type, id);
    }

    public void unbind(int type) {
        GL15.glBindBuffer(type, 0);
    }

    public void storeData(int type, float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        storeData(type, buffer);
    }

    public void storeData(int type, FloatBuffer buffer) {
        GL15.glBufferData(type, buffer, GL15.GL_STATIC_DRAW);
    }

    public void storeData(int type, int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        storeData(type, buffer);
    }

    public void storeData(int type, IntBuffer buffer) {
        GL15.glBufferData(type, buffer, GL15.GL_STATIC_DRAW);
    }

    public void delete() {
        GL15.glDeleteBuffers(id);
    }

    public enum Type {
        Index,
        Position,
        Size,
        Normal,
        TextureCoord,
        Color,
        Tangent
    }


}
