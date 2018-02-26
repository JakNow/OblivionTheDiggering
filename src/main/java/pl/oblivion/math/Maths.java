package pl.oblivion.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.engine.camera.Camera;
import pl.oblivion.scene.Model;

import java.util.List;

/**
 * @author jakubnowakowski
 * Created at 25.01.2018
 */
public class Maths {

    private static final Matrix4f MODEL_VIEW_MATRIX = new Matrix4f();
    /**
     * Get from Camera class
     */
    private static final Matrix4f VIEW_MATRIX = new Matrix4f();
    //Added to don't create new instance in everymethod. Use this and add more if needed
    private static Vector3f TEMP_VEC1 = new Vector3f();
    private static Vector3f TEMP_VEC2 = new Vector3f();
    private static Vector3f TEMP_VEC3 = new Vector3f();



    public static void vector3fToList(Vector3f vector3f, List<Float> floatList) {
        floatList.add(vector3f.x);
        floatList.add(vector3f.y);
        floatList.add(vector3f.z);
    }

    public static void vector2fToList(Vector3f vector2f, List<Float> floatList) {
        floatList.add(vector2f.x);
        floatList.add(vector2f.y);
    }

    public static int[] listIntToArray(List<Integer> list) {
        return list.stream().mapToInt((Integer v) -> v).toArray();
    }

    public static float[] listFloatToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] array = new float[size];
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static Matrix4f viewCurrent = new Matrix4f();

    public static Matrix4f getModelViewMatrix(Model model) {
       Quaternion rotation = model.getTransform().rotation;
         MODEL_VIEW_MATRIX.identity().translate(model.getTransform().translation).rotateX(-rotation.x)
                .rotateY(-rotation.y).rotateZ(-rotation.z).scale(model
                .getTransform().scale);
        viewCurrent.set(VIEW_MATRIX);
        return viewCurrent.mul(MODEL_VIEW_MATRIX);
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        VIEW_MATRIX.identity().set(camera.getViewMatrix());
        return VIEW_MATRIX;
    }

    public static float clamp(float value, float min, float max){
        if(value<min){
            return min;
        }else if (value>max){
            return max;
        }else{
            return value;
        }
    }

    public static float keepValue(float value, float min, float max){
        value = value % max;
        if(value < min){
            value+=max;
        }
        return value;
    }
}