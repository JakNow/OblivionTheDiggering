package pl.oblivion.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author jakubnowakowski
 * Created at 22.01.2018
 */
public class Transform {

    private final Matrix4f transformationMatrix = new Matrix4f();
    public Vector3f translation;
    public Quaternionf rotation;
    public Vector3f scale;
    private Quaternionf tempRotation;
    private Vector3f tempScale;

    public Transform() {
        this.translation = new Vector3f(0.0f);
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1.0f);
        this.tempRotation = new Quaternionf(this.rotation);
        this.tempScale = new Vector3f(this.scale);

    }

    public Transform(Vector3f translation, Quaternionf rotation, Vector3f scale) {
        this.translation = translation != null ? translation : new Vector3f();
        this.rotation = rotation != null ? rotation : new Quaternionf();
        this.scale = scale != null ? scale : new Vector3f(1, 1, 1);
        this.tempRotation = new Quaternionf(this.rotation);
        this.tempScale = new Vector3f(this.scale);
    }

    public Transform(Transform transform) {
        this.translation = new Vector3f(transform.translation);
        this.rotation = new Quaternionf(transform.rotation);
        this.scale = new Vector3f(transform.scale);
        this.tempRotation = new Quaternionf(this.rotation);
        this.tempScale = new Vector3f(this.scale);
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix.identity().translate(translation).rotate(rotation).scale
                (scale);
    }

    public void rotate(Quaternionf rotation, float delta) {
        tempRotation.mul(rotation);
        this.rotation.slerp(tempRotation, delta);
        tempRotation.set(this.rotation);
    }

    @Override
    public String toString() {
        return "Transform{" +
                "translation=" + translation +
                ", rotation=" + rotation +
                ", scale=" + scale +
                '}';
    }

    public void set(Transform transform) {
        this.setTranslation(transform.translation);
        this.setRotation(transform.rotation);
        this.setScale(transform.scale);
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }


    public void transform(Transform transform) {
        this.rotation.set(tempRotation.set(transform.rotation).mul(this.rotation));
        this.scale.mul(transform.scale);
        this.translation.add(transform.translation).rotate(transform.rotation).mul(this.scale);
    }

    public Transform negate() {
        this.translation.negate();
        this.rotation.x = -this.rotation.x;
        this.rotation.y = -this.rotation.y;
        this.rotation.z = -this.rotation.z;
        this.scale = new Vector3f(1).div(this.scale);
        return this;
    }
}
