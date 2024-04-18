package xyz.tryfle.platformer.util.render

import org.lwjgl.opengl.GL46.*
import org.lwjgl.system.MemoryStack
import xyz.tryfle.platformer.util.system.FileReader

class ShaderProgram {

    private var vertexShader: Int = 0
    private var fragmentShader: Int = 0
    var shaderProgram: Int = glCreateProgram()

    init {
        compileShader()
    }

    private fun compileShader() {
        println("Compiling shaders..")
        vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, FileReader.readFileAsString("glsl/VertexShader.glsl"))
        glCompileShader(vertexShader)
        val vertexStatus = MemoryStack.stackPush()
            .use { stack ->
                val vertexStatusB = stack.ints(0)

                glGetShaderiv(vertexShader, GL_COMPILE_STATUS, vertexStatusB)
                vertexStatusB
            }
        if (vertexStatus[0] == GL_FALSE) {
            val log = glGetShaderInfoLog(vertexShader)
            println("Vertex shader failed to compile: $log")
        }

        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, FileReader.readFileAsString("glsl/FragShader.glsl"))
        glCompileShader(fragmentShader)
        val fragmentStatus = MemoryStack.stackPush()
            .use { stack ->
                val fragmentStatusB = stack.ints(0)

                glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, fragmentStatusB)
                fragmentStatusB
            }
        if (fragmentStatus[0] == GL_FALSE) {
            val log = glGetShaderInfoLog(fragmentShader)
            println("Fragment shader failed to compile: $log")
        }

        glAttachShader(shaderProgram, vertexShader)
        glAttachShader(shaderProgram, fragmentShader)
        glLinkProgram(shaderProgram)

        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
    }
}