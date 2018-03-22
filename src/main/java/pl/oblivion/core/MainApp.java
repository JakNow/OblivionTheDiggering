package pl.oblivion.core;

import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.assets.AssetLoader;
import pl.oblivion.core.app.SimpleApp;
import pl.oblivion.game.assets.models.Floor;
import pl.oblivion.game.assets.models.Wall;
import pl.oblivion.light.types.DirectLight;
import pl.oblivion.light.types.PointLight;
import pl.oblivion.light.types.SpotLight;
import pl.oblivion.light.utils.Attenuation;
import pl.oblivion.material.Material;
import pl.oblivion.material.Texture;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Mesh;
import pl.oblivion.scene.Model;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MainApp extends SimpleApp {

    Model cube1, cube2, cube3, cube4, cube5;
    PointLight pointLight;

    public MainApp() {
        cube1 = new Floor(10, 10, new Transform(new Vector3f(0, -5, 0), new Quaternionf(),
                new
                Vector3f(1, 1, 1)));
        cube2 = new Wall( 11, 5, 1, new Transform(new Vector3f(0, 2.5f, 5f), new Quaternionf(),
                new
                Vector3f
                (1, 1, 1)));

        cube3 = new Wall( 11, 5, 1, new Transform(new Vector3f(0, 2.5f, -5f), new Quaternionf(),
                new Vector3f(1, 1, 1)));

        cube4 = new Wall( 11, 5, 1, new Transform(new Vector3f(5, 2.5f, 0),new Quaternionf(new AxisAngle4f
                ((float) Math.toRadians(90),0,1,0)), new
                Vector3f(1, 1, 1)));

        cube5 = new Wall( 11, 5, 1, new Transform(new Vector3f(-5f, 2.5f, 0), new Quaternionf(new AxisAngle4f
                ((float) Math.toRadians(90),0,1,0)), new
                Vector3f(1, 1, 1)));



        Material testMaterial = cube1.getMatMesh().getMaterial();

        testMaterial.setDiffuseColour(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f));
        testMaterial.setAmbientColour(testMaterial.getDiffuseColour());
        testMaterial.setShininess(96.078431f);

        Texture diffuse = AssetLoader.loadTexture("brick_diffuse", "brick/BricksWhiteWashedLight001_COL_1K.jpg");
        Texture normal = AssetLoader.loadTexture("brick_normal", "brick/BricksWhiteWashedLight001_NRM_1K.jpg");
        Texture specular = AssetLoader.loadTexture("brick_specular", "brick/BricksWhiteWashedLight001_GLOSS_1K.jpg");
        Texture reflection = AssetLoader.loadTexture("brick_reflection", "brick/BricksWhiteWashedLight001_REFL_1K" +
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
        Quaternionf direction = new Quaternionf(new AxisAngle4f((float)Math.toRadians(45),0,-1,0));


        pointLight = new PointLight("pointLight", new Vector3f(0, 2, 7), color, new
                Attenuation(1, 0.01f, 0.02f), 1.0f);
        cube1.addChild(pointLight);
       DirectLight directLight = new DirectLight("directLight", direction, color, 1.0f) {
            @Override
            public void update(float delta) {

            }
        };
        rootScene.addChild(directLight);

        SpotLight spotLight = new SpotLight("spotLight", new Vector3f(0, 2, 0), direction, color, 7.5f, new
                Attenuation
                (0f, 0.0f, 0.02f), 1f);


        cube1.addChild(spotLight);



        DirectLight directLight1 = new DirectLight("test", new Quaternionf(new AxisAngle4f((float)Math.toRadians(90),
                1,1,0)), new Vector4f
                (0.34f, 0.231f, 0.852f,
                        1.0f), 0.5f) {
            @Override
            public void update(float delta) {

            }
        };
        rootScene.addChild(directLight1);


        DirectLight directLight2 = new DirectLight("test", new Quaternionf(new AxisAngle4f((float)Math.toRadians(90),
                1,1,0)), new
                Vector4f
                (0.1734f, 0.1734f,
                        0.1734f,
                        1.0f), 1.0f) {
            @Override
            public void update(float delta) {

            }
        };
        rootScene.addChild(directLight2);




        rootScene.addChild(camera);

    }

    public static void main(String... args) {
        new MainApp().run();
    }

    @Override
    public void logicUpdate(float delta) {

        cube1.rotate(new Quaternionf(new AxisAngle4f((float)Math.toRadians(5),0,1,0)),delta);

        rootScene.updateTree(delta);
        if (window.isKeyPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(window.getWindowID(), true);
        }
    }
}
