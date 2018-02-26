package pl.oblivion.scene;

import pl.oblivion.material.Material;

public class MatMesh {

    private Mesh mesh;
    private Material material;

    MatMesh(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
