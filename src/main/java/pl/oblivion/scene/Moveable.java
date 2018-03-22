package pl.oblivion.scene;

import org.joml.Quaternionf;

/**
 * @author jakubnowakowski
 * Created at 19.02.2018
 */
public interface Moveable {

    public void rotate(Quaternionf rotation, float delta);

    public void translate(float dx, float dy, float dz, float delta);


}
