package pl.oblivion.scene;

import org.joml.Vector3f;

/**
 * @author jakubnowakowski
 * Created at 19.02.2018
 */
public interface Moveable {

    public void rotate(float dx, float dy, float dz, float delta);

    public void rotate(Vector3f dp, float delta);

    public void translate(float dx, float dy, float dz, float delta);

    public void translate(Vector3f dp, float delta);

}
