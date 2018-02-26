package pl.oblivion.engine.render;

import org.apache.log4j.Logger;
import pl.oblivion.engine.shader.types.ShaderType;
import pl.oblivion.gui.GUI;
import pl.oblivion.light.Light;
import pl.oblivion.scene.GameObject;
import pl.oblivion.scene.MatMesh;
import pl.oblivion.scene.Model;
import pl.oblivion.scene.Scene;

import java.util.*;

public class RendererQueue {

    private static final Logger logger = Logger.getLogger(RendererQueue.class.getName());

    private final Map<ShaderType, Map<MatMesh, List<Model>>> shaderMeshMap = new HashMap<>();
    private final Map<ShaderType, Map<GUI, List<GUI>>> shaderGUIMap = new HashMap<>();
    private final List<Light> lightList = new ArrayList<>();

    RendererQueue() {
        logger.info("Successful " + RendererQueue.class.getName() + " initialization.");
    }


    void processScene(Scene scene) {
        List<GameObject> gameObjects = scene.getChildren();
        for (GameObject gameObject : gameObjects) {
            processGameObject(gameObject);
        }
    }

    private void processGameObject(GameObject gameObject) {
        if (gameObject.getClassType() == Model.class) {
            processModel((Model) gameObject);
        } else if (gameObject.getClassType() == Scene.class) {
            processScene((Scene) gameObject);
        } else if (gameObject.getClassType() == GUI.class) {
            processGui((GUI) gameObject);
        } else if (gameObject.getClassType() == Light.class) {
            processLight((Light) gameObject);
        }
    }

    private void processModel(Model model) {
        Map<MatMesh, List<Model>> meshModelMap = shaderMeshMap.computeIfAbsent(model.getMatMesh().getMaterial().getShader().getShaderType(), k -> new HashMap<>());
        List<Model> models = meshModelMap.computeIfAbsent(model.getMatMesh(), k -> new LinkedList<>());
        models.add(model);

        for (GameObject gameObject : model.getChildren()) {
            processGameObject(gameObject);
        }

    }

    private void processGui(GUI GUI) {
        Map<GUI, List<GUI>> guiGuiMap = shaderGUIMap.computeIfAbsent(GUI.getMaterial().getShader().getShaderType(), k -> new HashMap<>());
        List<GUI> GUIS = guiGuiMap.computeIfAbsent(GUI, k -> new LinkedList<>());

        GUIS.add(GUI);
    }

    private void processLight(Light light) {
        lightList.add(light);
    }

    public List<Light> getLightList() {
        return lightList;
    }

    public Map<ShaderType, Map<MatMesh, List<Model>>> getShaderMeshMap() {
        return shaderMeshMap;
    }

    public Map<ShaderType, Map<GUI, List<GUI>>> getShaderGUIMap() {
        return shaderGUIMap;
    }
}
