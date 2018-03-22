package pl.oblivion.gui;

import pl.oblivion.material.Material;
import pl.oblivion.scene.GameObject;

public abstract class GUI extends GameObject {

    private Material material;

    protected GUI() {
        super("GUI", GUI.class);
    }

    protected GUI(String name) {
        super(name, GUI.class);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public abstract void update(float delta);

}
