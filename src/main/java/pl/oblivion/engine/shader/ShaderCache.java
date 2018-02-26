package pl.oblivion.engine.shader;

import org.apache.log4j.Logger;
import pl.oblivion.engine.shader.types.DiffuseShader;
import pl.oblivion.engine.shader.types.ShaderType;

import java.util.HashMap;
import java.util.Map;

public class ShaderCache {

    private static final Logger logger = Logger.getLogger(ShaderCache.class.getName());
    private static ShaderCache INSTANCE;
    private Map<ShaderType, ShaderProgram> shaderProgramList;

    private ShaderCache() {
        shaderProgramList = new HashMap<>();
    }

    public static synchronized ShaderCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShaderCache();
            logger.info("Creating a new instnace of " + ShaderCache.class.getName() + ".");
        }
        return INSTANCE;
    }

    public ShaderProgram getShaderProgram(ShaderType shaderType) {
        ShaderProgram shaderProgram = shaderProgramList.get(shaderType);
        if (shaderProgram == null) {
            logger.info("Creating a new Shader Program of " + shaderType + " type");
            shaderProgram = createNewShader(shaderType);
            shaderProgramList.put(shaderType, shaderProgram);
            return shaderProgram;
        } else {
            return shaderProgram;
        }
    }

    private ShaderProgram createNewShader(ShaderType shaderType) {
        switch (shaderType) {
            case DiffuseShaderType:
                return new DiffuseShader();
        }
        logger.error("Didn't find a Shader of type " + shaderType + ". Add new case or create a shader.");
        System.exit(-1);
        return null;
    }

    public Map<ShaderType, ShaderProgram> getShaderProgramList() {
        return shaderProgramList;
    }
}
