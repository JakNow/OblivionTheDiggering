package pl.oblivion.engine.camera;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.core.app.Window;
import pl.oblivion.math.Quaternion;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.GameObject;

import static org.lwjgl.glfw.GLFW.*;

public class Camera extends GameObject {

    private Matrix4f viewMatrix = new Matrix4f();
    private Window window;

    protected Camera(String name, Vector3f position, Quaternion rotation, Window window) {
        super(name, Camera.class);
        this.window = window;
        this.transform = new Transform(position, rotation, new Vector3f(1.0f));
    }

    public Camera(Window window) {
        super("Default Camera", Camera.class);
        this.transform = new Transform(new Vector3f(), new Quaternion(), new Vector3f(1.0f));
        this.window = window;

    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraPos = transform.translation;
        Quaternionf rotation = transform.rotation;

        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public void update(float delta) {
        if (window.isKeyPressed(GLFW_KEY_W)) {
            transform.translation.z -= 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            transform.translation.z += 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            transform.translation.x -= 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            transform.translation.x += 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            transform.translation.y -= 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_X)) {
            transform.translation.y += 0.5 * 10 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            transform.rotation.x -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_E)) {
            transform.rotation.x += 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_R)) {
            transform.rotation.y -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_T)) {
            transform.rotation.y += 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_F)) {
            transform.rotation.z -= 0.5 * 100 * delta;
        }
        if (window.isKeyPressed(GLFW_KEY_G)) {
            transform.rotation.z += 0.5 * 100 * delta;
        }
        /*
        if (getWindow().isKeyPressed(GLFW_KEY_W)) {
			playerMoveComponent.setVelocityGoal(playerMoveComponent.getRunSpeed());
		} else if (getWindow().isKeyPressed(GLFW_KEY_S)) {
			playerMoveComponent.setVelocityGoal(-playerMoveComponent.getRunSpeed());
		}else if(getWindow().isKeyReleased(GLFW_KEY_W) || getWindow().isKeyReleased(GLFW_KEY_S)){
			playerMoveComponent.setVelocityGoal(0);
		}
         */
    }

}
