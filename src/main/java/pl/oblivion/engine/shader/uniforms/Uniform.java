package pl.oblivion.engine.shader.uniforms;

import org.apache.log4j.Logger;
import org.lwjgl.opengl.GL20;

public abstract class Uniform {

    private static final int NOT_FOUND = -1;
    private static Logger logger;
    private String name;
    private int location;

    public Uniform(String name) {
        this.name = name;
        logger = initLogger();
    }

    protected abstract Logger initLogger();

    public void storeUniformLocation(int programID) {
        location = GL20.glGetUniformLocation(programID, name);
        if (location == NOT_FOUND) {
            logger.error("Could not found " + this.getClass() + " for " + name);
        }
    }

    int getLocation() {
        return location;
    }

}
