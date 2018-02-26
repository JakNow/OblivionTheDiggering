package pl.oblivion.scene;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import pl.oblivion.scene.buffer.VertexBuffer;

import java.util.HashMap;
import java.util.Map;

public class Mesh {

    private static final int BYTES_PER_FLOAT = 4;
    private static final int BYTES_PER_INT = 4;
    public final int id;
    private Map<VertexBuffer.Type, VertexBuffer> vertexBuffers = new HashMap<>();
    private VertexBuffer indexBuffer;
    private int indexCount;

    private MeshData meshData;

    private Mesh(int id, MeshData meshData) {
        this.id = id;
        this.meshData = meshData;
        this.initMesh();
    }

    private void initMesh() {
        this.bind();
        this.createIndexBuffer(meshData.getIndices());
        this.createAttribute(VertexBuffer.Type.Position, 0, meshData.getVertices(), 3);
        this.createAttribute(VertexBuffer.Type.TextureCoord, 1, meshData.getTextures(), 2);
        this.createAttribute(VertexBuffer.Type.Normal, 2, meshData.getNormals(), 3);
        this.unbind();
    }

    public void bind() {
        GL30.glBindVertexArray(id);
    }

    private void createIndexBuffer(int[] indices) {
        this.indexBuffer = VertexBuffer.create(VertexBuffer.Type.Index);
        this.indexBuffer.bind(GL15.GL_ELEMENT_ARRAY_BUFFER);
        this.indexBuffer.storeData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices);
        this.indexCount = indices.length;
    }

    private void createAttribute(VertexBuffer.Type type, int attribute, float[] data, int attributeSize) {
        VertexBuffer vertexBuffer = VertexBuffer.create(type);
        vertexBuffer.bind(GL15.GL_ARRAY_BUFFER);
        vertexBuffer.storeData(GL15.GL_ARRAY_BUFFER, data);
        GL20.glVertexAttribPointer(attribute, attributeSize, GL11.GL_FLOAT, false, attributeSize * BYTES_PER_FLOAT, 0);
        vertexBuffer.unbind(GL15.GL_ARRAY_BUFFER);
        vertexBuffers.put(type, vertexBuffer);
    }

    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    public static Mesh create(MeshData meshData) {
        int id = GL30.glGenVertexArrays();
        return new Mesh(id, meshData);
    }

    public void bind(int... attributes) {
        bind();
        for (int i : attributes) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    public void unbind(int... attributes) {
        for (int i : attributes) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }

    public void createAttribute(VertexBuffer.Type type, int attribute, int[] data, int attributeSize) {
        VertexBuffer vertexBuffer = VertexBuffer.create(type);
        vertexBuffer.bind(GL15.GL_ARRAY_BUFFER);
        vertexBuffer.storeData(GL15.GL_ARRAY_BUFFER, data);
        GL30.glVertexAttribIPointer(attribute, attributeSize, GL11.GL_INT, attributeSize * BYTES_PER_INT, 0);
        vertexBuffer.unbind(GL15.GL_ARRAY_BUFFER);
        vertexBuffers.put(type, vertexBuffer);
    }

    public void delete() {
        GL30.glDeleteVertexArrays(id);
        for (VertexBuffer.Type type : vertexBuffers.keySet()) {
            vertexBuffers.get(type).delete();
        }
        indexBuffer.delete();
    }

    public Map<VertexBuffer.Type, VertexBuffer> getVertexBuffers() {
        return vertexBuffers;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public MeshData getMeshData() {
        return meshData;
    }

    public static class MeshData {

        private String name;
        private int[] indices;
        private float[] vertices;
        private float[] textures;
        private float[] normals;
        private float[] tangents;
        private float furthestPoint;

        public MeshData(String name, int[] indices, float[] vertices, float[] textures, float[] normals, float furthestPoint) {
            this.name = name;
            this.indices = indices;
            this.vertices = vertices;
            this.textures = textures;
            this.normals = normals;
            this.furthestPoint = furthestPoint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int[] getIndices() {
            return indices;
        }

        public void setIndices(int[] indices) {
            this.indices = indices;
        }

        public float[] getVertices() {
            return vertices;
        }

        public void setVertices(float[] vertices) {
            this.vertices = vertices;
        }

        public float[] getTextures() {
            return textures;
        }

        public void setTextures(float[] textures) {
            this.textures = textures;
        }

        public float[] getNormals() {
            return normals;
        }

        public void setNormals(float[] normals) {
            this.normals = normals;
        }

        public float[] getTangents() {
            return tangents;
        }

        public void setTangents(float[] tangents) {
            this.tangents = tangents;
        }

        public float getFurthestPoint() {
            return furthestPoint;
        }

        public void setFurthestPoint(float furthestPoint) {
            this.furthestPoint = furthestPoint;
        }
    }
}
