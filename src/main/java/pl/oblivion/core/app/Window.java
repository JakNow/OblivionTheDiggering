package pl.oblivion.core.app;

import org.apache.log4j.Logger;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class Window {

    private static final Logger logger = Logger.getLogger(Window.class.getName());
    public static float RED = Float.parseFloat(SimpleApp.prop.getProperty("window.background.red"));
    public static float GREEN = Float.parseFloat(SimpleApp.prop.getProperty("window.background.green"));
    public static float BLUE = Float.parseFloat(SimpleApp.prop.getProperty("window.background.blue"));
    public static float ALPHA = Float.parseFloat(SimpleApp.prop.getProperty("window.background.alpha"));
    private static float FOV = Float.parseFloat(SimpleApp.prop.getProperty("window.projection.fov"));
    private static float NEAR = Float.parseFloat(SimpleApp.prop.getProperty("window.projection.near"));
    private static float FAR = Float.parseFloat(SimpleApp.prop.getProperty("window.projection.far"));
    private final Matrix4f projectionMatrix;
    private long windowID;
    private String title = SimpleApp.prop.getProperty("window.display.title");
    private String mainTitle = title;
    private int width = Integer.parseInt(SimpleApp.prop.getProperty("window.display.width"));
    private int height = Integer.parseInt(SimpleApp.prop.getProperty("window.display.height"));
    private boolean resized = false;
    private boolean vSync = true;

    public Window() {
        projectionMatrix = new Matrix4f();
        init();
        logger.info("Successful " + this.getClass().getName() + " initialization.");
    }

    private void init() {
        if (!glfwInit()) {
            logger.error("Unable to initialize GLFW", new IllegalStateException());
        }
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        windowID = glfwCreateWindow(width, height, mainTitle, NULL, NULL);

        if (windowID == NULL) {
            logger.error("Failed to craete GLFW window", new RuntimeException());
        }

        glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.resized = true;
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(windowID, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    windowID,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(windowID);

        if (vSync) {
            glfwSwapInterval(1);
        }

        glfwShowWindow(windowID);
        GL.createCapabilities();

        glClearColor(RED, GREEN, BLUE, ALPHA);
    }

    public static Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
        logger.info("Updating projection matrix.");
        float aspectRatio = (float) width / (float) height;
        return matrix.setPerspective(FOV, aspectRatio, NEAR, FAR);
    }

    public void destroy() {
        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);

        glfwTerminate();
    }

    public void updateAfter() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public long getWindowID() {
        return windowID;
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowID, keyCode) == GLFW_PRESS;
    }

    public boolean isvSync() {
        return vSync;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public void setTitle(String append) {
        glfwSetWindowTitle(windowID, title.concat(append));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
