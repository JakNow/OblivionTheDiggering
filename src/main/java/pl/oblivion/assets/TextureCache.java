package pl.oblivion.assets;

import org.apache.log4j.Logger;
import pl.oblivion.material.Texture;

import java.util.HashMap;
import java.util.Map;

class TextureCache {

    private static final Logger logger = Logger.getLogger(TextureCache.class.getName());

    private static TextureCache INSTANCE;

    private Map<String, Texture> texturesMap;

    private TextureCache() {
        texturesMap = new HashMap<>();
    }

    public static synchronized TextureCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TextureCache();
            logger.info("Creating a new instance of " + TextureCache.class.getName() + ".");
        }
        return INSTANCE;
    }

    public Texture getTexture(String name, String path) {
        Texture texture = texturesMap.get(name);
        if (texture == null) {
            texture = new Texture(name,path);
            texturesMap.put(name, texture);
        }
        return texture;
    }

    public void cleanUp() {
        for (String key : texturesMap.keySet()) {
            texturesMap.get(key).delete();
        }
    }
}
