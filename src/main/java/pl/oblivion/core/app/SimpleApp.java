package pl.oblivion.core.app;

import org.apache.log4j.Logger;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.oblivion.core.utils.Config;
import pl.oblivion.engine.camera.Camera;
import pl.oblivion.engine.render.Renderer;
import pl.oblivion.math.Transform;
import pl.oblivion.scene.Scene;

import java.util.Properties;

public abstract class SimpleApp {

    private static final Logger logger = Logger.getLogger(SimpleApp.class.getName());
    public static Properties prop = Config.loadProperties("src/main/resources/app.properties");
    private static float interval;
    protected final Window window;
    protected final Camera camera;
    protected final Scene rootScene;
    private final Timer timer;
    private final Renderer renderer;
    private final int ups = Integer.parseInt(prop.getProperty("window.display.ups"));
    private final int fps = Integer.parseInt(prop.getProperty("window.display.fps"));

    protected SimpleApp() {
        window = new Window();
        timer = new Timer();
        camera = new Camera("3d camera", new Vector3f(0, 0, 0), new Quaternionf(new AxisAngle4f((float) Math.toRadians
                (0), 0, 1, 0)),
                window);
        renderer = new Renderer(window, camera);
        rootScene = new Scene("rootNode", new Transform());


    }

    public static float getInterval() {
        return interval;
    }

    public void run() {
        init();
        float elapsedTime;
        float accumulator = 0f;
        interval = 1f / ups;
        float seconds = 0f;
        int upsTick = 0, fpsTick = 0;

        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;
            seconds += elapsedTime;

            while (accumulator >= interval) {
                logicUpdate(interval);

                accumulator -= interval;
                upsTick++;
            }
            renderer.render();
            fpsTick++;
            if (!window.isvSync()) {
                sync();
            }

            if (seconds >= 1.0f) {
                window.setTitle(" [UPS: " + upsTick + " | FPS: " + fpsTick + "]");
                upsTick = fpsTick = 0;
                seconds = 0f;
            }
            window.updateAfter();
        }

        cleanUp();
        window.destroy();
    }

    private void init() {
        renderer.addScene(rootScene);
        renderer.initShadersUniforms();
    }

    public abstract void logicUpdate(float delta);

    private void sync() {
        float loopSlot = 1f / fps;
        double endTime = timer.getLastLoopTime() + loopSlot;

        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                logger.error("Problem with vSyncing APP.", ie);
            }
        }
    }

    private void cleanUp() {
        renderer.cleanUp();
    }

    private class Timer {
        private final Logger logger = Logger.getLogger(Timer.class);
        private double lastLoopTime;

        Timer() {
            this.init();
            logger.info("Initializing of Timer was successful.");
        }

        private void init() {
            lastLoopTime = getTime();
        }

        double getTime() {
            return System.nanoTime() / 1000_000_000.0;
        }

        float getElapsedTime() {
            double time = getTime();
            float elapsedTime = (float) (time - lastLoopTime);
            lastLoopTime = time;
            return elapsedTime;
        }

        double getLastLoopTime() {
            return lastLoopTime;
        }
    }
}
