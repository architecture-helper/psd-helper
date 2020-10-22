package dev.federicocapece.drawzone

import javafx.scene.canvas.GraphicsContext

interface Drawable {
    var x : Double
    var y : Double

    var width: Double
    var height: Double

    fun draw(g:GraphicsContext)

    val bottom: Double
        get() = y+height

    val right: Double
        get() = x+width

}