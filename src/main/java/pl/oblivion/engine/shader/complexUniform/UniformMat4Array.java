package pl.oblivion.engine.shader.complexUniform;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import pl.oblivion.engine.shader.uniforms.Uniform;
import pl.oblivion.engine.shader.uniforms.UniformMat4;

public class UniformMat4Array extends Uniform {

    private UniformMat4[] uniformMat4s;

    public UniformMat4Array(String name, int size) {
        super(name);
        for (int i = 0; i < size; i++) {
            uniformMat4s[i] = new UniformMat4(name + "[" + i + "]");
        }
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    @Override
    public void storeUniformLocation(int programID) {
        for (UniformMat4 uniformMat4 : uniformMat4s) {
            uniformMat4.storeUniformLocation(programID);
        }
    }

    public void loadMat4Array(Matrix4f[] matrix4fs) {
        for (int i = 0; i < matrix4fs.length; i++) {
            uniformMat4s[i].loadMat4(matrix4fs[i]);
        }
    }

}
