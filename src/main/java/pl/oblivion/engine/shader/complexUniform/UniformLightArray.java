package pl.oblivion.engine.shader.complexUniform;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import pl.oblivion.engine.shader.uniforms.Uniform;
import pl.oblivion.light.Light;

import java.util.List;

public class UniformLightArray extends Uniform {

    private UniformLight[] uniformLights;

    public UniformLightArray(String name, int size) {
        super(name);
        uniformLights = new UniformLight[size];
        for (int i = 0; i < size; i++) {
            uniformLights[i] = new UniformLight(name + "[" + i + "]");
        }
    }

    public void loadLights(List<Light> lightList, Matrix4f viewMatrix) {
        for (int i = 0; i < lightList.size(); i++) {
            uniformLights[i].loadLight(lightList.get(i), viewMatrix);
        }
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(UniformLightArray.class.getName());
    }

    @Override
    public void storeUniformLocation(int programID) {
        for (UniformLight uniformLight : uniformLights) {
            uniformLight.storeUniformLocation(programID);
        }
    }
}
