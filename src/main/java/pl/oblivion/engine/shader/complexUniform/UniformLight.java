package pl.oblivion.engine.shader.complexUniform;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import pl.oblivion.engine.shader.uniforms.*;
import pl.oblivion.light.Light;
import pl.oblivion.light.utils.Attenuation;

public class UniformLight extends Uniform {

    private final Vector4f tempPos = new Vector4f();
    private final Vector4f tempDir = new Vector4f();
    private UniformBoolean useDirectLight;
    private UniformBoolean usePointLight;
    private UniformBoolean useSpotLight;
    private UniformDirectLight directLight;
    private UniformPointLight pointLight;
    private UniformSpotLight spotLight;

    UniformLight(String name) {
        super(name);
        useDirectLight = new UniformBoolean(name + ".useDirectLight");
        usePointLight = new UniformBoolean(name + ".usePointLight");
        useSpotLight = new UniformBoolean(name + ".useSpotLight");

        directLight = new UniformDirectLight(name + ".directLight");
        pointLight = new UniformPointLight(name + ".pointLight");
        spotLight = new UniformSpotLight(name + ".spotLight");
    }

    @Override
    protected Logger initLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    public void loadLight(Light light, Matrix4f viewMatrix) {
        switch (light.getLightType()) {
            case DirectLight:
                useDirectLight.loadBoolean(true);
                directLight.loadLight(light, viewMatrix);
                break;
            case PointLight:
                usePointLight.loadBoolean(true);
                pointLight.loadLight(light, viewMatrix);
                break;
            case SpotLight:
                useSpotLight.loadBoolean(true);
                spotLight.loadLight(light, viewMatrix);
                break;
        }
    }    @Override
    public void storeUniformLocation(int programID) {
        useDirectLight.storeUniformLocation(programID);
        usePointLight.storeUniformLocation(programID);
        useSpotLight.storeUniformLocation(programID);
        directLight.storeUniformLocation(programID);
        pointLight.storeUniformLocation(programID);
        spotLight.storeUniformLocation(programID);
    }

    public class UniformSpotLight extends Uniform {

        private UniformVec3 position;
        private UniformVec3 direction;
        private UniformVec4 color;
        private UniformFloat angle;
        private UniformAttenuation attenuation;
        private UniformFloat intensity;

        UniformSpotLight(String name) {
            super(name);
            position = new UniformVec3(name + ".position");
            direction = new UniformVec3(name + ".direction");
            color = new UniformVec4(name + ".color");
            angle = new UniformFloat(name + ".angle");
            attenuation = new UniformAttenuation(name + ".att");
            intensity = new UniformFloat(name + ".intensity");
        }

        private void loadLight(Light light, Matrix4f viewMatrix) {
            tempPos.set(light.getTransform().translation, 1.0f);
            tempPos.mul(viewMatrix);
            position.loadVec3(tempPos.x, tempPos.y, tempPos.z);

            tempDir.set(light.getTransform().rotation.x,light.getTransform().rotation.y,light.getTransform().rotation
                    .z, 0.0f);
            tempDir.mul(viewMatrix);

            direction.loadVec3(tempDir.x, tempDir.y, tempDir.z);
            color.loadVec4(light.getColor());
            angle.loadFloat(light.getAngle());
            attenuation.loadAttenuation(light.getAttenuation());
            intensity.loadFloat(light.getIntensity());
        }        @Override
        protected Logger initLogger() {
            return Logger.getLogger(this.getClass().getName());
        }

        @Override
        public void storeUniformLocation(int programID) {
            position.storeUniformLocation(programID);
            direction.storeUniformLocation(programID);
            color.storeUniformLocation(programID);
            angle.storeUniformLocation(programID);
            attenuation.storeUniformLocation(programID);
            intensity.storeUniformLocation(programID);
        }


    }

    public class UniformDirectLight extends Uniform {

        private UniformVec3 direction;
        private UniformVec4 color;
        private UniformFloat intensity;

        UniformDirectLight(String name) {
            super(name);
            direction = new UniformVec3(name + ".direction");
            color = new UniformVec4(name + ".color");
            intensity = new UniformFloat(name + ".intensity");
        }

        private void loadLight(Light light, Matrix4f viewMatrix) {
            tempDir.set(light.getTransform().rotation.x,light.getTransform().rotation.y,light.getTransform().rotation
                    .z, 0.0f);            tempDir.mul(viewMatrix);

            direction.loadVec3(tempDir.x, tempDir.y, tempDir.z);
            color.loadVec4(light.getColor());
            intensity.loadFloat(light.getIntensity());
        }        @Override
        protected Logger initLogger() {
            return Logger.getLogger(this.getClass().getName());
        }

        @Override
        public void storeUniformLocation(int programID) {
            direction.storeUniformLocation(programID);
            color.storeUniformLocation(programID);
            intensity.storeUniformLocation(programID);
        }


    }

    public class UniformPointLight extends Uniform {

        private UniformVec3 position;
        private UniformVec4 color;
        private UniformAttenuation attenuation;
        private UniformFloat intensity;

        UniformPointLight(String name) {
            super(name);
            position = new UniformVec3(name + ".position");
            color = new UniformVec4(name + ".color");
            attenuation = new UniformAttenuation(name + ".att");
            intensity = new UniformFloat(name + ".intensity");
        }

        private void loadLight(Light light, Matrix4f viewMatrix) {
            tempPos.set(light.getTransform().translation, 1.0f);
            tempPos.mul(viewMatrix);

            position.loadVec3(tempPos.x, tempPos.y, tempPos.z);
            color.loadVec4(light.getColor());
            attenuation.loadAttenuation(light.getAttenuation());
            intensity.loadFloat(light.getIntensity());
        }        @Override
        protected Logger initLogger() {
            return Logger.getLogger(this.getClass().getName());
        }

        @Override
        public void storeUniformLocation(int programID) {
            position.storeUniformLocation(programID);
            color.storeUniformLocation(programID);
            attenuation.storeUniformLocation(programID);
            intensity.storeUniformLocation(programID);
        }


    }

    public class UniformAttenuation extends Uniform {

        private UniformFloat constant;
        private UniformFloat linear;
        private UniformFloat exponent;

        UniformAttenuation(String name) {
            super(name);
            constant = new UniformFloat(name + ".constant");
            linear = new UniformFloat(name + ".linear");
            exponent = new UniformFloat(name + ".exponent");
        }

        private void loadAttenuation(Attenuation attenuation) {
            constant.loadFloat(attenuation.getConstant());
            linear.loadFloat(attenuation.getLinear());
            exponent.loadFloat(attenuation.getExponent());
        }

        @Override
        public void storeUniformLocation(int programID) {
            constant.storeUniformLocation(programID);
            linear.storeUniformLocation(programID);
            exponent.storeUniformLocation(programID);
        }

        @Override
        protected Logger initLogger() {
            return Logger.getLogger(this.getClass().getName());
        }
    }


}
