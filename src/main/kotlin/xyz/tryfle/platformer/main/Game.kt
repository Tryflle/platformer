package xyz.tryfle.platformer.main

import xyz.tryfle.platformer.event.EventBus
import xyz.tryfle.platformer.gameobjects.player.Triangle

object Game {

    val eventBus: EventBus = EventBus()
    private val triangle: Triangle = Triangle()

    fun postInit() {
        eventBus.subscribe(triangle)
        triangle.initializeShaderProgram()
    }
}