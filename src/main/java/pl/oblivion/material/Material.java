package pl.oblivion.material;

import org.apache.log4j.Logger;
import org.joml.Vector4f;
import pl.oblivion.engine.shader.ShaderCache;
import pl.oblivion.engine.shader.ShaderProgram;
import pl.oblivion.engine.shader.types.ShaderType;
import pl.oblivion.export.Saveable;

public class Material implements Cloneable, Saveable {
    public static final int DIFFUSE_TEXTURE_UNIT = 0;
    public static final int NORMAL_TEXTURE_UNIT = 1;
    public static final int AMBIENT_TEXTURE_UNIT = 2;
    public static final int SPECULAR_TEXTURE_UNIT = 3;
    public static final int ALPHA_TEXTURE_UNIT = 4;
    public static final int REFLECTION_TEXTURE_UNIT = 5;
    
    public static final Vector4f DEFAULT_COLOR = new Vector4f(255, 255, 255, 1);
    private static final Logger logger = Logger.getLogger(Material.class.getName());
    private String name;
    private MaterialData materialData;
    private ShaderProgram shader;
    private boolean useDiffuseTexture = false;
    private boolean useNormalTexture = false;
    private boolean useAmbientTexture = false;
    private boolean useSpecularTexture = false;
    private boolean useAlphaTexture = false;
    private boolean useReflectionTexture = false;


    public Material(String name) {
        this.name = name;
        this.materialData = new MaterialData();
        this.materialData.addMaterialParam(MaterialDataType.Name, this.name);
        this.shader = ShaderCache.getInstance().getShaderProgram(ShaderType.DiffuseShaderType);
        logger.info("Creating a new material " + name);
    }

    public ShaderProgram getShader() {
        return shader;
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }

    public void setShader(ShaderType shaderType) {
        this.shader = ShaderCache.getInstance().getShaderProgram(shaderType);
    }

    public String getName() {
        return name;
    }

    public Texture getDiffuseTexture() {
        Object object = this.getMaterialParam(MaterialDataType.DiffuseTexture);
        return (object != null) ? (Texture) object : null;
    }

    private Object getMaterialParam(MaterialDataType type) {
        try {
            return this.materialData.getMaterialParam(type).value;
        } catch (NullPointerException e) {
            logger.warn("Couldn't find material data: " + type + ".");
            return null;
        }
    }

    public void setDiffuseTexture(Texture texture) {
        this.useDiffuseTexture = true;
        this.setMaterialParam(MaterialDataType.DiffuseTexture, texture);
    }

    public void setMaterialParam(MaterialDataType type, Object value) {
        this.materialData.addMaterialParam(type, value);
    }

    public Texture getNormalTexture() {
        return (Texture) this.getMaterialParam(MaterialDataType.NormalTexture);
    }

    public void setNormalTexture(Texture texture) {
        this.useNormalTexture = true;
        this.setMaterialParam(MaterialDataType.NormalTexture, texture);
    }

    public Texture getAmbientTexture() {
        return (Texture) this.getMaterialParam(MaterialDataType.AmbientTexture);
    }

    public void setAmbientTexture(Texture texture) {
        this.useAmbientTexture = true;
        this.setMaterialParam(MaterialDataType.AmbientTexture, texture);
    }

    public Texture getSpecularTexture() {
        return (Texture) this.getMaterialParam(MaterialDataType.SpecularTexture);
    }

    public void setSpecularTexture(Texture texture) {
        this.useSpecularTexture = true;
        this.setMaterialParam(MaterialDataType.SpecularTexture, texture);
    }

    public Texture getAlphaTexture() {
        return (Texture) this.getMaterialParam(MaterialDataType.AlphaTexture);
    }

    public void setAlphaTexture(Texture texture) {
        this.useAlphaTexture = true;
        this.setMaterialParam(MaterialDataType.AlphaTexture, texture);
    }

    public Vector4f getAmbientColour() {
        return (Vector4f) this.getMaterialParam(MaterialDataType.AmbientColor);
    }

    public void setAmbientColour(Vector4f colour) {
        this.setMaterialParam(MaterialDataType.AmbientColor, colour);
    }

    public Vector4f getDiffuseColour() {
        return (Vector4f) this.getMaterialParam(MaterialDataType.DiffuseColor);
    }

    public void setDiffuseColour(Vector4f colour) {
        this.setMaterialParam(MaterialDataType.DiffuseColor, colour);
    }

    public Vector4f getSpecularColour() {
        return (Vector4f) this.getMaterialParam(MaterialDataType.SpecularColor);
    }

    public void setSpecularColour(Vector4f colour) {
        this.setMaterialParam(MaterialDataType.SpecularColor, colour);
    }

    public Vector4f getEmissiveColour() {
        return (Vector4f) this.getMaterialParam(MaterialDataType.EmissiveColor);
    }

    public void setEmissiveColour(Vector4f colour) {
        this.setMaterialParam(MaterialDataType.EmissiveColor, colour);
    }

    public Vector4f getReflectiveColour() {
        return (Vector4f) this.getMaterialParam(MaterialDataType.ReflectiveColor);
    }

    public void setReflectiveColour(Vector4f colour) {
        this.setMaterialParam(MaterialDataType.ReflectiveColor, colour);
    }

    public float getReflectivity() {
        return (float) this.getMaterialParam(MaterialDataType.Reflectivity);
    }

    public void setReflectivity(float reflectivity) {
        this.setMaterialParam(MaterialDataType.Reflectivity, reflectivity);
    }


    public float getShininess() {
        return (float) this.getMaterialParam(MaterialDataType.Shininess);
    }

    public void setShininess(float shininess) {
        this.setMaterialParam(MaterialDataType.Shininess, shininess);
    }

    public boolean isUseDiffuseTexture() {
        return useDiffuseTexture;
    }

    public boolean isUseNormalTexture() {
        return useNormalTexture;
    }

    public boolean isUseAmbientTexture() {
        return useAmbientTexture;
    }

    public boolean isUseSpecularTexture() {
        return useSpecularTexture;
    }

    public boolean isUseAlphaTexture() {
        return useAlphaTexture;
    }

    public void setReflectionTexture(Texture texture) {
        this.useReflectionTexture = true;
        this.setMaterialParam(MaterialDataType.ReflectionTexture, texture);
    }
    
    public Texture getReflectionTexture(){
        return (Texture) this.getMaterialParam(MaterialDataType.ReflectionTexture);
    }


    public boolean isUseReflectionTexture() {
        return useReflectionTexture;
    }
}
