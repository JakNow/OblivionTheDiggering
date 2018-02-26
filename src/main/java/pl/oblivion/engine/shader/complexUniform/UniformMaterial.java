package pl.oblivion.engine.shader.complexUniform;

import org.apache.log4j.Logger;
import pl.oblivion.engine.shader.uniforms.Uniform;
import pl.oblivion.engine.shader.uniforms.UniformBoolean;
import pl.oblivion.engine.shader.uniforms.UniformFloat;
import pl.oblivion.engine.shader.uniforms.UniformVec4;
import pl.oblivion.material.Material;

public class UniformMaterial extends Uniform {


    private UniformBoolean hasDiffuseTexture;
    private UniformBoolean hasNormalTexture;
    private UniformBoolean hasAmbientTexture;
    private UniformBoolean hasSpecularTexture;
    private UniformBoolean hasAlphaTexture;
    private UniformBoolean hasReflectionTexture;

    private UniformVec4 ambientColor;
    private UniformVec4 diffuseColor;
    private UniformVec4 specularColor;
    private UniformVec4 emissiveColor;
    private UniformVec4 reflectiveColor;

    private UniformFloat reflectivity;
    private UniformFloat shininess;

    public UniformMaterial(String name) {
        super(name);
        hasDiffuseTexture = new UniformBoolean(name + ".hasDiffuseTexture");
        hasNormalTexture = new UniformBoolean(name + ".hasNormalTexture");
        hasAmbientTexture = new UniformBoolean(name + ".hasAmbientTexture");
        hasSpecularTexture = new UniformBoolean(name + ".hasSpecularTexture");
        hasAlphaTexture = new UniformBoolean(name + ".hasAlphaTexture");
        hasReflectionTexture = new UniformBoolean(name+".hasReflectionTexture");

        ambientColor = new UniformVec4(name + ".ambientColor");
        diffuseColor = new UniformVec4(name + ".diffuseColor");
        specularColor = new UniformVec4(name + ".specularColor");
        emissiveColor = new UniformVec4(name + ".emissiveColor");
        reflectiveColor = new UniformVec4(name + ".reflectiveColor");

        reflectivity = new UniformFloat(name + ".reflectivity");
        shininess = new UniformFloat(name + ".shininess");
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    @Override
    public void storeUniformLocation(int programID) {
        hasDiffuseTexture.storeUniformLocation(programID);
        hasNormalTexture.storeUniformLocation(programID);
        hasAmbientTexture.storeUniformLocation(programID);
        hasSpecularTexture.storeUniformLocation(programID);
        hasAlphaTexture.storeUniformLocation(programID);
        hasReflectionTexture.storeUniformLocation(programID);

        ambientColor.storeUniformLocation(programID);
        diffuseColor.storeUniformLocation(programID);
        specularColor.storeUniformLocation(programID);
        emissiveColor.storeUniformLocation(programID);
        reflectiveColor.storeUniformLocation(programID);

        reflectivity.storeUniformLocation(programID);
        shininess.storeUniformLocation(programID);
    }

    public void loadMaterial(Material material) {
        hasDiffuseTexture.loadBoolean(material.isUseDiffuseTexture());
        hasNormalTexture.loadBoolean(material.isUseNormalTexture());
        hasAmbientTexture.loadBoolean(material.isUseAmbientTexture());
        hasSpecularTexture.loadBoolean(material.isUseSpecularTexture());
        hasAlphaTexture.loadBoolean(material.isUseAlphaTexture());
        hasReflectionTexture.loadBoolean(material.isUseReflectionTexture());

        ambientColor.loadVec4(material.getAmbientColour());
        diffuseColor.loadVec4(material.getDiffuseColour());
        specularColor.loadVec4(material.getSpecularColour());
        emissiveColor.loadVec4(material.getEmissiveColour());
        reflectiveColor.loadVec4(material.getReflectiveColour());

        reflectivity.loadFloat(material.getReflectivity());
        shininess.loadFloat(material.getShininess());
    }
}
