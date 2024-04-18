package xyz.tryfle.platformer.input

import xyz.tryfle.platformer.event.KeyboardEvent
import xyz.tryfle.platformer.main.Game

class KeyHandler {

    fun handleKey(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        Game.eventBus.post(KeyboardEvent(key, action))
    }
}