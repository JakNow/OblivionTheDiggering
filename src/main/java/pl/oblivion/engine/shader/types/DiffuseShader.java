package pl.oblivion.engine.shader.types;

import org.joml.Matrix4f;
import pl.oblivion.engine.render.Renderer;
import pl.oblivion.engine.shader.ShaderProgram;
import pl.oblivion.engine.shader.complexUniform.UniformAmbientLight;
import pl.oblivion.engine.shader.complexUniform.UniformLightArray;
import pl.oblivion.engine.shader.complexUniform.UniformMaterial;
import pl.oblivion.engine.shader.uniforms.UniformInt;
import pl.oblivion.engine.shader.uniforms.UniformMat4;
import pl.oblivion.engine.shader.uniforms.UniformSampler;
import pl.oblivion.export.OwnFile;
import pl.oblivion.material.Material;

public class DiffuseShader extends ShaderProgram {
    private static final OwnFile vertex = new OwnFile("shaders/diffuse/vertex.vert");
    private static final OwnFile fragment = new OwnFile("shaders/diffuse/fragment.frag");
    private final int maxLights = 10;
    private UniformMat4 projectionMatrix = new UniformMat4("projectionMatrix");
    private UniformMat4 modelViewMatrix = new UniformMat4("modelViewMatrix");
    private UniformAmbientLight ambientLight = new UniformAmbientLight("ambientLight");

    private UniformMaterial materialUniform = new UniformMaterial("material");
    private UniformLightArray uniformLightArray = new UniformLightArray("light", 10);
    private UniformInt numberOfLights = new UniformInt("numberOfLights");
    private UniformSampler diffuseTextureSampler = new UniformSampler("diffuseTexture");
    private UniformSampler normalTextureSampler = new UniformSampler("normalTexture");
    private UniformSampler ambientTextureSampler = new UniformSampler("ambientTexture");
    private UniformSampler specularTextureSampler = new UniformSampler("specularTexture");
    private UniformSampler alphaTextureSampler = new UniformSampler("alphaTexture");


    public DiffuseShader() {
        super(ShaderType.DiffuseShaderType, vertex, fragment, "in_position", "in_texture", "in_normal");
        super.storeAllUniformLocations(projectionMatrix, modelViewMatrix, ambientLight, materialUniform, uniformLightArray,
                numberOfLights,
                diffuseTextureSampler,
                normalTextureSampler, ambientTextureSampler, specularTextureSampler, alphaTextureSampler);
        connectTextureUnits();
    }


    @Override
    public void loadUniformOnce(Renderer renderer) {
        numberOfLights.loadInt(renderer.getCurrentNumberOfLights(maxLights));
    }

    @Override
    public void startShaderRenderLogic(Renderer renderer, Matrix4f viewMatrix) {
        this.start();
        projectionMatrix.loadMat4(renderer.getProjectionMatrix());
        ambientLight.loadAmbientLight(renderer.getAmbientLight());
        uniformLightArray.loadLights(renderer.getListOfLights(), viewMatrix);
    }

    @Override
    public void prepareMeshUniforms(Object... objects) {
        materialUniform.loadMaterial((Material) objects[0]);
    }

    @Override
    public void prepareModelUniforms(Object... objects) {
        modelViewMatrix.loadMat4((Matrix4f) objects[0]);
    }

    @Override
    public void stopShaderRenderLogic() {
        this.stop();
    }

    @Override
    public void connectTextureUnits() {
        super.start();
        diffuseTextureSampler.loadTexUnit(Material.DIFFUSE_TEXTURE_UNIT);
        normalTextureSampler.loadTexUnit(Material.NORMAL_TEXTURE_UNIT);
        ambientTextureSampler.loadTexUnit(Material.AMBIENT_TEXTURE_UNIT);
        specularTextureSampler.loadTexUnit(Material.SPECULAR_TEXTURE_UNIT);
        alphaTextureSampler.loadTexUnit(Material.ALPHA_TEXTURE_UNIT);
        super.stop();
    }


}
