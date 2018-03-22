package pl.oblivion.assets;

import org.apache.log4j.Logger;
import pl.oblivion.material.Material;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

import java.util.HashMap;
import java.util.Map;

class ModelCache {

    private static final Logger logger = Logger.getLogger(ModelCache.class.getName());

    private static ModelCache INSTANCE;
    private Map<String, Model> modelsMap;

    private ModelCache() {
        modelsMap = new HashMap<>();
    }

    public static synchronized ModelCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelCache();
            logger.info("Creating a new instance of " + ModelCache.class.getName() + ".");
        }
        return INSTANCE;
    }

    public Model getModel(String name) {
        Model model = modelsMap.get(name);
        if (model == null) {
            logger.info("Importing new model.");
            model = AssetLoader.importModel(name);
            modelsMap.put(name, model);
            return model;
        } else {
            logger.info("Loading model from " + ModelCache.class.getName());
            return model;
        }
    }

    Model createModel(Mesh mesh, Material material) {
        String name = mesh.getMeshData().getName();
        Model model = modelsMap.get(name);
        if (model == null) {
            model = new Model(name, mesh, material);
            modelsMap.put(name, model);
        }

        return model;
    }

    public void cleanUp() {
        for (String key : modelsMap.keySet()) {
            //TODO models cleanUp
        }
    }




}
