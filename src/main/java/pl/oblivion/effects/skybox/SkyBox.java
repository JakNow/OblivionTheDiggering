package pl.oblivion.effects.skybox;

import pl.oblivion.scene.GameObject;

public class SkyBox extends GameObject {
    //TODO check if add new renderer (renderSkybox) or can be in renderModel();
    protected SkyBox(String name) {
        super(name, SkyBox.class);
    }
}
