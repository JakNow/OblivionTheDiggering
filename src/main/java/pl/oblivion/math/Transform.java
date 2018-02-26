package pl.oblivion.math;

import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.scene.GameObject;

import java.util.List;

/**
 * @author jakubnowakowski
 * Created at 22.01.2018
 */
public class Transform {

    public Vector3f translation;
    public Quaternion rotation;
    public Vector3f scale;

    public Transform() {
        this.translation = new Vector3f(0.0f);
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f);
        this.scale = new Vector3f(1.0f);
    }

    public Transform(Vector3f translation, Quaternion rotation, Vector3f scale) {
        this.translation = translation != null ? translation : new Vector3f();
        this.rotation = rotation != null ? rotation : new Quaternion();
        this.scale = scale != null ? scale : new Vector3f();
    }

    @Override
    public String toString() {
        return "Transform{" +
                "translation=" + translation +
                ", rotation=" + rotation +
                '}';
    }



    public void rotateAroundPoint(float dx, float dy, float dz, Transform transform){
        this.translation.sub(transform.translation);

        this.translation.rotateX(-(float)Math.toRadians(dx));
        this.translation.rotateY(-(float)Math.toRadians(dy));
        this.translation.rotateZ(-(float)Math.toRadians(dz));

        this.translation.add(transform.translation);

        this.rotation.add(dx,dy,dz);
    }


    public void updateToParent(GameObject parent) {
        this.translation.add(parent.getWorldTransform().translation);
        rotateAroundParent(parent.getWorldTransform());
        this.scale.mul(parent.getWorldTransform().scale);
    }

    private void rotateAroundParent(Transform transform){

       this.translation.sub(transform.translation);

        this.translation.rotateX(-transform.rotation.x);
        this.translation.rotateY(-transform.rotation.y);
        this.translation.rotateZ(-transform.rotation.z);

        this.translation.add(transform.translation);


        this.rotation.add(transform.rotation);
    }
}
