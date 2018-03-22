package pl.oblivion.engine.render;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import pl.oblivion.assets.AssetLoader;
import pl.oblivion.core.app.Window;
import pl.oblivion.engine.camera.Camera;
import pl.oblivion.engine.shader.ShaderCache;
import pl.oblivion.engine.shader.ShaderProgram;
import pl.oblivion.engine.shader.types.ShaderType;
import pl.oblivion.gui.GUI;
import pl.oblivion.light.Light;
import pl.oblivion.light.types.AmbientLight;
import pl.oblivion.material.Material;
import pl.oblivion.math.Maths;
import pl.oblivion.scene.MatMesh;
import pl.oblivion.scene.Model;
import pl.oblivion.scene.Scene;

import java.util.List;
import java.util.Map;

public class Renderer {

    private static final Logger logger = Logger.getLogger(Renderer.class.getName());
    private final RendererQueue rendererQueue;
    private final Camera camera;
    /**
     * Binding 0-position,1-texture,2-normal
     */
    private final int[] bindingAttributes = {0, 1, 2};
    float t = 0.2f;
    private final AmbientLight ambientLight = new AmbientLight(new Vector4f(t, t, t, 1.0f), 1.0f) {
        @Override
        public void update(float delta) {

        }
    };
    private Window window;
    private float currentWidth;
    private float currentHeight;
    private ShaderCache shaderCache = ShaderCache.getInstance();

    public Renderer(Window window, Camera camera) {
        this.window = window;
        this.camera = camera;
        this.rendererQueue = new RendererQueue();

    }


    public void initShadersUniforms() {
        for (ShaderType shaderType : rendererQueue.getShaderMeshMap().keySet()) {
            ShaderProgram shaderProgram = shaderCache.getShaderProgram(shaderType);
            shaderProgram.start();
            shaderProgram.loadUniformOnce(this);
            shaderProgram.stop();
        }
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Window.RED, Window.GREEN, Window.BLUE, Window.ALPHA);
        GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        if (currentWidth != window.getWidth() || currentHeight != window.getHeight()) {
            Window.updateProjectionMatrix(window.getProjectionMatrix(), window.getWidth(), window.getHeight());
            this.currentWidth = window.getWidth();
            this.currentHeight = window.getHeight();
        }
        renderModels();
        renderGUI();

    }

    private void renderModels() {
        for (ShaderType shaderType : rendererQueue.getShaderMeshMap().keySet()) {
            Map<MatMesh, List<Model>> mapOfModels = rendererQueue.getShaderMeshMap().get(shaderType);
            ShaderProgram shaderProgram = shaderCache.getShaderProgram(shaderType);
            Matrix4f viewMatrix = Maths.calculateViewMatrix(camera);
            shaderProgram.startShaderRenderLogic(this, viewMatrix);
            for (MatMesh matMesh : mapOfModels.keySet()) {
                prepareMesh(matMesh, shaderProgram);
                List<Model> modelList = mapOfModels.get(matMesh);
                for (Model model : modelList) {

                    shaderProgram.prepareModelUniforms(Maths.getModelViewMatrix(model));
                    GL11.glDrawElements(GL11.GL_TRIANGLES, matMesh.getMesh().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
                }
                matMesh.getMesh().unbind(bindingAttributes);
            }
            shaderCache.getShaderProgram(shaderType).stopShaderRenderLogic();
        }
    }

    private void renderGUI() {
        for (ShaderType shaderType : rendererQueue.getShaderGUIMap().keySet()) {
            Map<GUI, List<GUI>> mapOfGuis = rendererQueue.getShaderGUIMap().get(shaderType);
            shaderCache.getShaderProgram(shaderType).startShaderRenderLogic(this, null);
            for (GUI GUI : mapOfGuis.keySet()) {
                //TODO render logic for gui
            }
            shaderCache.getShaderProgram(shaderType).stopShaderRenderLogic();
        }
    }

    private void prepareMesh(MatMesh matMesh, ShaderProgram shaderProgram) {
        matMesh.getMesh().bind(bindingAttributes);

        Material material = matMesh.getMaterial();
        if (material.isUseDiffuseTexture()) {
            matMesh.getMaterial().getDiffuseTexture().bind(Material.DIFFUSE_TEXTURE_UNIT);
        }
        if (material.isUseNormalTexture()) {
            matMesh.getMaterial().getNormalTexture().bind(Material.NORMAL_TEXTURE_UNIT);
        }
        if (material.isUseAmbientTexture()) {
            matMesh.getMaterial().getAmbientTexture().bind(Material.AMBIENT_TEXTURE_UNIT);
        }
        if (material.isUseSpecularTexture()) {
            matMesh.getMaterial().getSpecularTexture().bind(Material.SPECULAR_TEXTURE_UNIT);
        }
        if (material.isUseAlphaTexture()) {
            matMesh.getMaterial().getAlphaTexture().bind(Material.ALPHA_TEXTURE_UNIT);
        }
        if (material.isUseReflectionTexture()) {
            matMesh.getMaterial().getReflectionTexture().bind(Material.REFLECTION_TEXTURE_UNIT);
        }

        shaderProgram.prepareMeshUniforms(material);
    }

    public void cleanUp() {
        for (ShaderType shaderType : shaderCache.getShaderProgramList().keySet()) {
            shaderCache.getShaderProgramList().get(shaderType).clear();
            shaderCache.getShaderProgramList().remove(shaderType);
        }
        AssetLoader.cleanUp();
    }

    public void addScene(Scene scene) {
        rendererQueue.processScene(scene);
    }

    public Matrix4f getProjectionMatrix() {
        return window.getProjectionMatrix();
    }

    public int getCurrentNumberOfLights(int maxLights) {
        return rendererQueue.getLightList().size() > maxLights ? maxLights : rendererQueue.getLightList().size();
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public List<Light> getListOfLights() {
        return rendererQueue.getLightList();
    }
}
