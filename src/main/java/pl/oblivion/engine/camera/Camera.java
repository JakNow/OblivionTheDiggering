package pl.oblivion.engine.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.core.app.Window;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.GameObject;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends GameObject {

    private Matrix4f viewMatrix = new Matrix4f();
    private Window window;

    public Camera(String name, Vector3f position, Quaternionf rotation, Window window) {
        super(name, Camera.class);
        this.window = window;
        this.transform = new Transform(new Vector3f(position), new Quaternionf(rotation), new Vector3f(1.0f));

    }


    public Matrix4f getViewMatrix() {
        return viewMatrix.identity().rotate(transform.rotation).translate(-transform.translation.x, -transform
                .translation.y, -transform.translation.z);
    }


    public void update(float delta) {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            transform.translation.z -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            transform.translation.z += 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            transform.translation.x -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            transform.translation.x += 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            transform.translation.y -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_X)) {
            transform.translation.y += 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            transform.rotation.rotateX((float) Math.toRadians(20 * delta));
        }

        if (window.isKeyPressed(GLFW_KEY_E)) {
            transform.rotation.rotateX((float) Math.toRadians(-20 * delta));
        }
    }


}
