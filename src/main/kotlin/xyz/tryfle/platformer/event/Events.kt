package xyz.tryfle.platformer.event

class KeyboardEvent(val key: Int, val action: Int) : EventType()

class ClickEvent(val button: Int, val action: Int) : EventType()

class MoveMouseEvent(val xpos: Double, val ypos: Double) : EventType()

class GameLoopEvent() : EventType()