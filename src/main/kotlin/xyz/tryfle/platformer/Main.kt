package xyz.tryfle.platformer

import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import xyz.tryfle.platformer.main.Window

class Main {

    fun run() {
        println("Initializing LWJGL ${Version.getVersion()}")
        println("PLATFORMER!!! HOLY!!! Running on JVM version ${Runtime.version()}")
        GLFWErrorCallback.createPrint(System.err).set()

        check(glfwInit()) { "Unable to initialize GLFW" }

        Window.init()
        Window.loop()

        Callbacks.glfwFreeCallbacks(Window.handle)
        glfwDestroyWindow(Window.handle)

        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Main().run()
        }
    }
}