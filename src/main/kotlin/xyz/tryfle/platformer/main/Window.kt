package xyz.tryfle.platformer.main

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL46.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import xyz.tryfle.platformer.event.GameLoopEvent
import xyz.tryfle.platformer.input.KeyHandler
import xyz.tryfle.platformer.input.MouseHandler

object Window {

    var handle: Long = 0
    private val keyHandler = KeyHandler()
    private val mouseHandler = MouseHandler()

    fun loop() {
        createCapabilities()

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        while (!glfwWindowShouldClose(handle)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            Game.eventBus.post(GameLoopEvent())

            glfwSwapBuffers(handle)


            glfwPollEvents()
        }
    }

    fun init() {
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        handle = glfwCreateWindow(300, 300, "Tryfle learning to read docs", MemoryUtil.NULL, MemoryUtil.NULL)
        if (handle == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW handle")

        glfwSetKeyCallback(
            handle
        ) { handle: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                handle,
                true
            )
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(handle, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

            glfwSetWindowPos(
                handle,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }
        glfwMakeContextCurrent(handle)

        // Enable v-sync
        glfwSwapInterval(1)

        glfwShowWindow(handle)

        glfwSetKeyCallback(handle, keyHandler::handleKey)
        glfwSetMouseButtonCallback(handle, mouseHandler::handleMouseButton)
        glfwSetCursorPosCallback(handle, mouseHandler::handleCursorPos)
        createCapabilities()

        Game.postInit()
    }
}