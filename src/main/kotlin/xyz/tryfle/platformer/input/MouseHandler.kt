package xyz.tryfle.platformer.input

import xyz.tryfle.platformer.event.ClickEvent
import xyz.tryfle.platformer.event.MoveMouseEvent
import xyz.tryfle.platformer.main.Game

class MouseHandler {

    fun handleMouseButton(window: Long, button: Int, action: Int, mods: Int) {
        Game.eventBus.post(ClickEvent(button, action))
    }

    fun handleCursorPos(window: Long, xpos: Double, ypos: Double) {
        Game.eventBus.post(MoveMouseEvent(xpos, ypos))
    }
}