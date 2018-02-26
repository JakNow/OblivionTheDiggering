package pl.oblivion.engine.shader;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import pl.oblivion.engine.render.Renderer;
import pl.oblivion.engine.shader.types.ShaderType;
import pl.oblivion.engine.shader.uniforms.Uniform;
import pl.oblivion.export.OwnFile;

import java.io.BufferedReader;

public abstract class ShaderProgram {

    private static Logger logger;
    private final ShaderType shaderType;
    private int programID;

    public ShaderProgram(ShaderType shaderType, OwnFile vertex, OwnFile fragment, String... inVariables) {
        logger = initLogger();
        this.shaderType = shaderType;
        initShader(vertex, fragment, inVariables);
    }

    private Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    private void initShader(OwnFile vertex, OwnFile fragment, String... inVariables) {
        logger.info("Creating a new shader program for " + this.getClass().getName() + ".");
        int vertexShaderID = loadShader(vertex, GL20.GL_VERTEX_SHADER);
        int fragmentShaderID = loadShader(fragment, GL20.GL_FRAGMENT_SHADER);
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes(inVariables);
        GL20.glLinkProgram(programID);
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);

    }

    private int loadShader(OwnFile file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = file.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (Exception e) {
            logger.error("Could not read file.", e);
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            logger.error(GL20.glGetShaderInfoLog(shaderID, 500));
            logger.error("Could not compile shader " + file);
            clear();
            System.exit(-1);
        }
        logger.info("Compiled shader " + file + " was successfull.");
        return shaderID;
    }

    private void bindAttributes(String... inVariables) {
        for (int i = 0; i < inVariables.length; i++) {
            GL20.glBindAttribLocation(programID, i, inVariables[i]);
        }
    }

    public void clear() {
        stop();
        logger.info("Deleting Shader Program " + programID);
        GL20.glDeleteProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    protected void storeAllUniformLocations(Uniform... uniforms) {
        for (Uniform uniform : uniforms) {
            uniform.storeUniformLocation(programID);
        }
        GL20.glValidateProgram(programID);
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public ShaderType getShaderType() {
        return shaderType;
    }

    public abstract void loadUniformOnce(Renderer renderer);

    public abstract void startShaderRenderLogic(Renderer renderer, Matrix4f viewMatrix);

    public abstract void prepareMeshUniforms(Object... objects);

    public abstract void prepareModelUniforms(Object... objects);

    public abstract void stopShaderRenderLogic();

    public abstract void connectTextureUnits();
}
