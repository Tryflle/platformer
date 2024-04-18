package xyz.tryfle.platformer.util.system

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException

object FileReader {

    fun readFileAsString(filePath: String): String {
        val inputStream = javaClass.classLoader.getResourceAsStream(filePath)
        if (inputStream == null) {
            println("Error: File at $filePath not found or not accessible")
            return ""
        }

        return try {
            BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
        } catch (e: IOException) {
            println("Error reading file at $filePath: ${e.message}")
            ""
        }
    }
}