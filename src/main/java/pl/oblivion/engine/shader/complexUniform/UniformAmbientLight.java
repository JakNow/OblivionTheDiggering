package pl.oblivion.engine.shader.complexUniform;

import org.apache.log4j.Logger;
import pl.oblivion.engine.shader.uniforms.Uniform;
import pl.oblivion.engine.shader.uniforms.UniformFloat;
import pl.oblivion.engine.shader.uniforms.UniformVec4;
import pl.oblivion.light.types.AmbientLight;

public class UniformAmbientLight extends Uniform {

    private UniformVec4 color;
    private UniformFloat intensity;

    public UniformAmbientLight(String name) {
        super(name);

        color = new UniformVec4(name + ".color");
        intensity = new UniformFloat(name + ".intensity");
    }

    public void loadAmbientLight(AmbientLight ambientLight) {
        color.loadVec4(ambientLight.getColor());
        intensity.loadFloat(ambientLight.getIntensity());
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(UniformAmbientLight.class.getName());
    }

    @Override
    public void storeUniformLocation(int programID) {
        color.storeUniformLocation(programID);
        intensity.storeUniformLocation(programID);
    }
}
