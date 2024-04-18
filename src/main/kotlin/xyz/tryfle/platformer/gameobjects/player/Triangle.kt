package xyz.tryfle.platformer.gameobjects.player

import org.lwjgl.opengl.GL46.*
import org.lwjgl.glfw.GLFW
import org.lwjgl.system.MemoryUtil
import xyz.tryfle.platformer.event.GameLoopEvent
import xyz.tryfle.platformer.event.KeyboardEvent
import xyz.tryfle.platformer.event.Subscribe
import xyz.tryfle.platformer.util.render.ShaderProgram
import java.nio.IntBuffer

class Triangle {

    private var x: Float = 0f
    private var y: Float = 0f
    private var speed: Float = 5f
    private var vbo: Int = 0
    private var vao: Int = 0
    private lateinit var sP: ShaderProgram
    private val vertices: FloatArray = floatArrayOf(
        0.0f, 0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        -0.5f, -0.5f, 0.0f
    )

    @Subscribe
    fun onKey(e: KeyboardEvent) {
        if (e.action == GLFW.GLFW_PRESS) {
            when (e.key) {
                GLFW.GLFW_KEY_W -> x -= speed
                GLFW.GLFW_KEY_A -> x += speed
                GLFW.GLFW_KEY_S -> y += speed
                GLFW.GLFW_KEY_D -> x += speed
            }
        }
    }

    @Subscribe
    fun render(e: GameLoopEvent) {
        glUseProgram(sP.shaderProgram)
        glBindVertexArray(vao)
        glDrawArrays(GL_TRIANGLES, 0, 3)
        glBindVertexArray(0)
    }

    fun initializeShaderProgram() {
        val vaoBuffer: IntBuffer = MemoryUtil.memAllocInt(1)
        glGenVertexArrays(vaoBuffer)
        vao = vaoBuffer.get(0)
        glBindVertexArray(vao)
        MemoryUtil.memFree(vaoBuffer)

        val vboBuffer: IntBuffer = MemoryUtil.memAllocInt(1)
        glGenBuffers(vboBuffer)
        vbo = vboBuffer.get(0)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        val floatBuffer = MemoryUtil.memAllocFloat(vertices.size).put(vertices).flip()
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW)
        MemoryUtil.memFree(floatBuffer)
        MemoryUtil.memFree(vboBuffer)

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L)
        glEnableVertexAttribArray(0)

        glBindVertexArray(0)

        sP = ShaderProgram()
    }
}