package org.usd232.robotics.robot.minimap;

import java.nio.IntBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class Window implements Runnable {
    private long id;

    private void render() {
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glColor3f(1, 0, 0);
        GL11.glVertex2f(0, 0);
        GL11.glColor3f(0, 1, 0);
        GL11.glVertex2f(0, 1);
        GL11.glColor3f(0, 0, 1);
        GL11.glVertex2f(1, 1);
        GL11.glEnd();
    }

    @Override
    public void run() {
        GLFW.glfwMakeContextCurrent(id);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(id);
        GL.createCapabilities();
        GL11.glClearColor(0, 0, 0, 1);
        while (!GLFW.glfwWindowShouldClose(id)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            render();
            GLFW.glfwSwapBuffers(id);
            GLFW.glfwPollEvents();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        Callbacks.glfwFreeCallbacks(id);
        GLFW.glfwDestroyWindow(id);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    private void keyClicked(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
            GLFW.glfwSetWindowShouldClose(window, true);
        }
    }

    public Window() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        id = GLFW.glfwCreateWindow(800, 600, "Robot Minimap", MemoryUtil.NULL, MemoryUtil.NULL);
        if (id == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        GLFW.glfwSetKeyCallback(id, this::keyClicked);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(id, width, height);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(id, (vidmode.width() - width.get(0)) / 2, (vidmode.height() - height.get(0)) / 2);
        }
    }
}
