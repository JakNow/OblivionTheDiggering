package pl.oblivion.core;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.assets.AssetLoader;
import pl.oblivion.core.app.SimpleApp;
import pl.oblivion.light.types.DirectLight;
import pl.oblivion.light.types.PointLight;
import pl.oblivion.light.types.SpotLight;
import pl.oblivion.light.utils.Attenuation;
import pl.oblivion.material.Material;
import pl.oblivion.material.Texture;
import pl.oblivion.math.Quaternion;
import pl.oblivion.math.Transform;
import pl.oblivion.shapes.Cube;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MainApp extends SimpleApp {

    Cube cube1, cube2, cube3, cube4, cube5;

    public MainApp() {
        cube1 = new Cube("Cube1",new Transform(new Vector3f(0, 0, 0), new Quaternion(0, 45, 0), new
                Vector3f
                (10,
                1, 10)));
        cube2 = new Cube("Cube2",new Transform(new Vector3f(0, 2.5f, 5), new Quaternion(0, 0, 0), new
                Vector3f(11,
                5, 1)));
        cube3 = new Cube("Cube3",new Transform(new Vector3f(0, 2.5f, -5), new Quaternion(0, 0, 0), new
                Vector3f(11,
                5, 1)));
        cube4 = new Cube("Cube4",new Transform(new Vector3f(5, 2.5f, 0), new Quaternion(0, 0, 0), new
                Vector3f(1,
                5, 11)));
        cube5 = new Cube("Cube5",new Transform(new Vector3f(-5, 2.5f, 0), new Quaternion(0, 0, 0), new
                Vector3f(1,
                5, 11)));

        Material testMaterial = cube1.getMatMesh().getMaterial();

        testMaterial.setDiffuseColour(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
        testMaterial.setAmbientColour(testMaterial.getDiffuseColour());
        testMaterial.setShininess(96.078431f);

        Texture diffuse = AssetLoader.loadTexture("brick_diffuse","brick/BricksWhiteWashedLight001_COL_1K.jpg");
        Texture normal = AssetLoader.loadTexture("brick_normal","brick/BricksWhiteWashedLight001_NRM_1K.jpg");
        Texture specular = AssetLoader.loadTexture("brick_specular","brick/BricksWhiteWashedLight001_GLOSS_1K.jpg");
        Texture reflection = AssetLoader.loadTexture("brick_reflection","brick/BricksWhiteWashedLight001_REFL_1K" +
                ".jpg");

        testMaterial.setDiffuseTexture(diffuse);
        testMaterial.setNormalTexture(normal);
        testMaterial.setSpecularTexture(specular);
        testMaterial.setReflectionTexture(reflection);

        cube1.getMatMesh().setMaterial(testMaterial);
        cube2.getMatMesh().setMaterial(testMaterial);
        cube3.getMatMesh().setMaterial(testMaterial);
        cube4.getMatMesh().setMaterial(testMaterial);
        cube5.getMatMesh().setMaterial(testMaterial);

        rootScene.addChild(cube1);
        cube1.addChild(cube2);
        cube1.addChild(cube3);
         cube1.addChild(cube4);
        cube1.addChild(cube5);

        Vector4f color = new Vector4f(1, 1, 1, 1);
        Quaternion direction = new Quaternion(-1, 0, 0);

        PointLight pointLight = new PointLight("pointLight", new Vector3f(0, 5, 1), color, new
                Attenuation(1, 0.01f, 0.002f), 1.0f);
        cube1.addChild(pointLight);



        DirectLight directLight = new DirectLight("directLight", direction, color, 1.0f);

        SpotLight spotLight = new SpotLight("spotLight", new Vector3f(20f, 2, 0), direction, color, 7.5f, new
                Attenuation
                (0f, 0.0f, 0.02f), 1f);



        rootScene.addChild(directLight);
        cube1.addChild(spotLight);


       DirectLight directLight1 = new DirectLight("test", new Quaternion(0, 1, 0), new Vector4f(0.34f, 0.231f, 0.852f,
                1.0f), 0.5f);
        rootScene.addChild(directLight1);


        DirectLight directLight2 = new DirectLight("test", new Quaternion(-1, -1, 0), new Vector4f(0.1734f, 0.1734f,
                0.1734f,
                1.0f), 1.0f);
        rootScene.addChild(directLight2);
    }

    public static void main(String... args) {
        new MainApp().run();
    }

    @Override
    public void logicUpdate(float delta) {
        camera.update(delta);
        cube1.rotate(0,0,0,delta);

        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindowID(), true);
        }
    }
}
